package com.ayy.dao;

import com.ayy.bean.Prof;
import com.ayy.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 01/03/2021
 * @ Version 1.0
 */
public class ProfMapperTest {
    private SqlSession sqlSession;
    private ProfMapper mapper;

    @Before
    public void setUp(){
         sqlSession = MyBatisUtils.getSqlSession();
         mapper = sqlSession.getMapper(ProfMapper.class);
    }

    @After
    public void tearDown(){
        mapper = null;
        sqlSession.close();
    }

    @Test
    public void testGetProfs(){
        List<Prof> profs = mapper.getProfs();
        profs.forEach(System.out::println);
    }

    @Test
    public void testGetProf(){
        Prof prof = mapper.getProf(1);
        System.out.println(prof);
    }

    @Test
    public void testGetProf2(){
        Prof prof = mapper.getProf2(1);
        System.out.println(prof);
    }
}
