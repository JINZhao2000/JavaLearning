package com.ayy.util;

import com.ayy.pojo.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

/**
 * @ ClassName HibernateUtilsTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/01/2021 21H
 * @ Version 1.0
 */
public class HibernateUtilsTest {
    @Test
    public void testGetSession() {
        Assert.assertNotNull(HibernateUtils.getSession());
    }

    @Test
    public void testCloseSession() {
        HibernateUtils.getSession();
        HibernateUtils.closeSession();
    }


    // not formal
    @Test
    public void testLifeCycle01() {
        Session session = null;
        Transaction tx = null;
        User user = null;
        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            user = new User(); // transient
            user.setUname("Hibernate2");
            user.setBalance(2000);

            session.save(user); // persistent

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }

        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            user.setUname("Hibernate3"); // detached

            session.update(user); // persistent

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Test
    public void testLifeCycle02() {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtils.getSession();

            //user = session.get(User.class,1); // persistent instantly
            user = session.load(User.class, 2); // persistent proxy

            System.out.println(user);

            session.clear(); // detached
            //session.evict(user);

        } catch (Exception e) {

        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Test
    public void testUpdate(){
        Session session = null;
        Transaction tx = null;
        User user = null;
        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            /* illegal
            user = new User();
            user.setUid(1);
            user.setUname("Hibernate4");
            session.update(user);
            */
            // balance => null

            user = session.get(User.class,1);
            if(null!=user){
                user.setUname("Hibernate5");
                session.update(user);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Test
    public void testDelete(){
        Session session = null;
        Transaction tx = null;
        User user = null;
        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            /* illegal
            user = new User();
            user.setUid(2);
            session.delete(user);
            */

            user = session.get(User.class,2);
            if(null!=user){
                session.delete(user);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }
}


