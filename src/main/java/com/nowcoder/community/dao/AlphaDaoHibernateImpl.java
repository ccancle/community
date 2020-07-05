package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @author ：ccancle菜菜
 * @date ：Created in 2019/11/5 22:16
 * @description：TODO
 * @version: TODO
 */
@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
