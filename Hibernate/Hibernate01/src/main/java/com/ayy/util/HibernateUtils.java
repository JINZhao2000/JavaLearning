package com.ayy.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @ ClassName HibernateUtils
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/01/2021 20H
 * @ Version 1.0
 */
public class HibernateUtils {
    private static ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();
    private static StandardServiceRegistry registry;
    private static SessionFactory factory;

    static {
        registry = new StandardServiceRegistryBuilder().configure().build();
        factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private static void rebuildSessionFactory(){
        factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    public static Session getSession(){
        Session session = sessionThreadLocal.get();
        if(null == session || !session.isOpen()){
            if(null==factory){
                HibernateUtils.rebuildSessionFactory();
            }
            session = (null!=factory?factory.openSession():null);
            if(null==session){
                throw new IllegalArgumentException("Can't create session");
            }
            sessionThreadLocal.set(session);
        }
        return session;
    }

    public static void closeSession(){
        Session session = sessionThreadLocal.get();
        sessionThreadLocal.set(null);
        if(null!=session && session.isOpen()){
            session.close();
        }
    }
}
