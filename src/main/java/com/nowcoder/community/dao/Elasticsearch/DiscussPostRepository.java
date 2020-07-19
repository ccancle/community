package com.nowcoder.community.dao.Elasticsearch;

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: community
 * @description: Elasticsearch数据层接口
 * @author: Macchac
 * @create: 2020-07-19 17:47
 **/
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {
}
