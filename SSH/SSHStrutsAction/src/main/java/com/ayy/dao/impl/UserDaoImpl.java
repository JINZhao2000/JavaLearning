package com.ayy.dao.impl;

import com.ayy.bean.User;
import com.ayy.dao.UserDao;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/02/2021
 * @ Version 1.0
 */
// SessionFactory -> HibernateTemplate
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    @Override
    public void save(User user) {
        this.getHibernateTemplate().save(user);
    }
}
