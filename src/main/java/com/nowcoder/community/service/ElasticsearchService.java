package com.nowcoder.community.service;

import com.nowcoder.community.dao.Elasticsearch.DiscussPostRepository;
import com.nowcoder.community.entity.DiscussPost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: community
 * @description: 搜索服务
 * @author: Macchac
 * @create: 2020-07-19 18:50
 **/
@Service
public class ElasticsearchService {

    @Autowired
    private DiscussPostRepository discussPostRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 发帖子
     * @param post
     */
    public void saveDiscussPost(DiscussPost post){
        discussPostRepository.save(post);
    }

    /**
     * 删帖子
     * @param id
     */
    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }

    /**
     * 查询
     * @param keywords 关键字
     * @param current 当前页码
     * @param limit 每页显示数量
     * @return
     */
    public Page<DiscussPost> searchDiscussPost(String keywords, int current,int limit){
        //构造查询条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keywords,"title","content"))
                //排序条件 优先按照type排序 然后按照加精分数排序，如果都相同则按照发布时间排序
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                //分页条件 当前页数和每页数量
                .withPageable(PageRequest.of(current,limit))
                //结果高亮条件 堆匹配值前后添加em标签
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();


        return  elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                //获取命中数据
                SearchHits hits = searchResponse.getHits();
                //判空
                if (hits.getTotalHits()<=0){
                    return null;
                }
                //创建集合
                List<DiscussPost> list = new ArrayList<>();
                for (SearchHit hit : hits){
                    //创建实体
                    DiscussPost post = new DiscussPost();

                    //把命中数据中的数据取值添加到实体中
                    String id = hit.getSourceAsMap().get("id").toString();
                    post.setId(Integer.valueOf(id));

                    String userId = hit.getSourceAsMap().get("userId").toString();
                    post.setUserId(Integer.valueOf(userId));

                    String title = hit.getSourceAsMap().get("title").toString();
                    post.setTitle(title);

                    String content = hit.getSourceAsMap().get("content").toString();
                    post.setContent(content);

                    String status = hit.getSourceAsMap().get("status").toString();
                    post.setStatus(Integer.valueOf(status));

                    String createTime = hit.getSourceAsMap().get("createTime").toString();
                    post.setCreateTime(new Date(Long.valueOf(createTime)));

                    String commentCount = hit.getSourceAsMap().get("commentCount").toString();
                    post.setCommentCount(Integer.valueOf(commentCount));

                    //处理高亮显示的结果  覆盖content title 显示内容
                    HighlightField titleField = hit.getHighlightFields().get("title");
                    if (titleField != null){
                        //匹配多段显示第一段 getFragments[0]
                        post.setTitle(titleField.getFragments()[0].toString());
                    }
                    HighlightField contentField = hit.getHighlightFields().get("content");
                    if (contentField != null){
                        //匹配多段显示第一段 getFragments[0]
                        post.setContent(contentField.getFragments()[0].toString());
                    }
                    list.add(post);

                }
                return new AggregatedPageImpl(list,pageable,
                        hits.getTotalHits(),searchResponse.getAggregations(),searchResponse.getScrollId(),hits.getMaxScore());
            }
        });
    }
}
