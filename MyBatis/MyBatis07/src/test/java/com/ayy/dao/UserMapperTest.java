package com.ayy.dao;

import com.ayy.bean.User;
import com.ayy.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 03/03/2021
 * @ Version 1.0
 */
public class UserMapperTest {
    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void setUp() throws Exception {
        sqlSession = MyBatisUtils.getSqlSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void tearDown() throws Exception {
        mapper = null;
        sqlSession.close();
    }

    @Test
    public void testQueryUserById() {
        User user = mapper.queryUserById(1);
        System.out.println(user);
        System.out.println("-------------------");
        User user2 = mapper.queryUserById(1);
        System.out.println(user2);
        System.out.println(user.equals(user2));
    }

    @Test
    public void testQueryUserById2() {
        User user = mapper.queryUserById(1);
        System.out.println(user);
    }

    @Test
    public void testQueryUserById3() {
        User user = mapper.queryUserById(1);
        System.out.println(user);
    }
}
