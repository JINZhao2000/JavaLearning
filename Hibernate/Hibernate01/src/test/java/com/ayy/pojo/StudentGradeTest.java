package com.ayy.pojo;

import com.ayy.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @ ClassName StudentGradeTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/01/2021 21H
 * @ Version 1.0
 */
public class StudentGradeTest {
    @Test
    public void testSingleManyToOne(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            Grade g1 = new Grade();
            g1.setGname("Base");

            Grade g2 = new Grade();
            g2.setGname("Advanced");

            session.save(g1);
            session.save(g2);

            Student stu1 = new Student();
            stu1.setSname("Student1");
            stu1.setAge(10);
            stu1.setGrade(g1);

            Student stu2 = new Student();
            stu2.setSname("Student2");
            stu2.setAge(12);
            stu2.setGrade(g1);

            Student stu3 = new Student();
            stu3.setSname("Student3");
            stu3.setAge(14);
            stu3.setGrade(g2);

            session.save(stu1);
            session.save(stu2);
            session.save(stu3);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Test
    public void testSingleGetManyToOne(){
        Session session = null;
        Student stu = null;
        try {
            session = HibernateUtils.getSession();

            stu = session.get(Student.class,1);
            System.out.println(stu);

        } catch (Exception e) {
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Test
    public void testSingleOneToMany(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            Grade2 g1 = new Grade2();
            g1.setGname("Base");

            Grade2 g2 = new Grade2();
            g2.setGname("Advanced");

            Student2 stu1 = new Student2();
            stu1.setSname("Student1");
            stu1.setAge(10);

            Student2 stu2 = new Student2();
            stu2.setSname("Student2");
            stu2.setAge(12);

            Student2 stu3 = new Student2();
            stu3.setSname("Student3");
            stu3.setAge(14);

            g1.addStudent(stu1);
            g1.addStudent(stu2);
            g2.addStudent(stu3);

            session.save(g1);
            session.save(g2);
            session.save(stu1);
            session.save(stu2);
            session.save(stu3);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Test
    public void testSingleGetOneToMany(){
        Session session = null;
        Grade2 g = null;
        try {
            session = HibernateUtils.getSession();

            g = session.get(Grade2.class,1);
            g.getStudents().forEach(System.out::println);

        } catch (Exception e) {
        } finally {
            HibernateUtils.closeSession();
        }
    }

    @Test
    public void testDoubleOneToMany(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.getSession();
            tx = session.beginTransaction();

            Grade3 g1 = new Grade3();
            g1.setGname("Base");

            Grade3 g2 = new Grade3();
            g2.setGname("Advanced");

            Student3 stu1 = new Student3();
            stu1.setSname("Student1");
            stu1.setAge(10);

            Student3 stu2 = new Student3();
            stu2.setSname("Student2");
            stu2.setAge(12);

            Student3 stu3 = new Student3();
            stu3.setSname("Student3");
            stu3.setAge(14);

            g1.addStudent(stu1);
            g1.addStudent(stu2);
            g2.addStudent(stu3);

            session.save(g1);
            session.save(g2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }
}
