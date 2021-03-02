package com.ayy.dao;

import com.ayy.bean.Etu;
import com.ayy.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/03/2021
 * @ Version 1.0
 */
public class EtuMapperTest {
    private SqlSession sqlSession;
    private EtuMapper mapper;

    @Before
    public void setUp(){
        sqlSession = MyBatisUtils.getSqlSession();
        mapper = sqlSession.getMapper(EtuMapper.class);
    }

    @After
    public void tearDown(){
        mapper = null;
        sqlSession.close();
    }

    @Test
    public void testGetEtu(){
        List<Etu> etus = mapper.getEtus();
        etus.forEach(System.out::println);
    }

    @Test
    public void testGetEtu2(){
        List<Etu> etus = mapper.getEtus2();
        etus.forEach(System.out::println);
    }
}
