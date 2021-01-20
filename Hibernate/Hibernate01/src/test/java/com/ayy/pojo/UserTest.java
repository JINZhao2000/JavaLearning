package com.ayy.pojo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

/**
 * @ ClassName UserTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/01/2021 22H
 * @ Version 1.0
 */
public class UserTest {
    @Test
    public void testHibernateUpdate(){
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction tx = null;
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            User user = new User();
            user.setUname("Hibernate1");
            user.setBalance(1000);
            session.save(user);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Test
    public void testHibernateQuery(){
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            session = sessionFactory.openSession();
            User user = session.get(User.class,1);
            System.out.println(user);
        }catch (Exception e){
        }finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }
    }
}
