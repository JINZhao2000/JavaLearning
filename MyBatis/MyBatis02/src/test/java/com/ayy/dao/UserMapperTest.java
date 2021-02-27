package com.ayy.dao;

import com.ayy.bean.User;
import com.ayy.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 26/02/2021
 * @ Version 1.0
 */
public class UserMapperTest {
    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void setUp() {
        sqlSession = MyBatisUtils.getSqlSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void tearDown() {
        sqlSession.close();
        mapper = null;
    }

    @Test
    public void testGetAllUser() {
        List<User> allUser = mapper.getAllUser();
        allUser.forEach(System.out::println);
    }

    @Test
    public void testGetAllUser2() {
        List<User> users = sqlSession.selectList("com.ayy.dao.UserMapper.getAllUser");
        users.forEach(System.out::println);
    }

    @Test
    public void testGetUserById() {
        User user = mapper.getUserById(1);
        System.out.println(user);
    }

    @Test
    public void testAddUser() {
        User user = new User("USER11", "123456");
        try {
            mapper.addUser(user);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }
    }

    @Test
    public void testUpdateUser(){
        User user = new User("USER11111","123456");
        user.setUid(1);
        try {
            mapper.updateUser(user);
            sqlSession.commit();
        } catch (Exception e){
            sqlSession.rollback();
        }
    }

    @Test
    public void testDeleteUser(){
        try {
            mapper.deleteUser(5);
            sqlSession.commit();
        } catch (Exception e){
            sqlSession.rollback();
        }
    }
}
