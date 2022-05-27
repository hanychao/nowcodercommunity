package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @author hyc
 * @create 2022-05-16 20:52
 */
@Repository("alphaDaoHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
