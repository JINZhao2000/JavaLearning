package com.ayy.pojo;

import com.ayy.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @ ClassName PersonIdCardTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/01/2021 23H
 * @ Version 1.0
 */
public class PersonIdCardTest {
    @Test
    public void testFKOneToOne(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            IdCard c1 = new IdCard();
            c1.setCode("100101");

            IdCard c2 = new IdCard();
            c2.setCode("100102");

            Person p1 = new Person();
            p1.setPname("Person1");
            p1.setAge(11);
            p1.setCard(c1);

            Person p2 = new Person();
            p2.setPname("Person2");
            p2.setAge(12);
            p2.setCard(c2);

            session.save(p1);
            session.save(p2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }
}
