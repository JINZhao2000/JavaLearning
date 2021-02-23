package com.ayy.dao.impl;

import com.ayy.bean.User;
import com.ayy.dao.UserDao;
import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/02/2021
 * @ Version 1.0
 */
public class UserDaoImpl implements UserDao {
    private HibernateTemplate template;

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    @Override
    public void save(User user) {
        template.save(user);
    }
}
