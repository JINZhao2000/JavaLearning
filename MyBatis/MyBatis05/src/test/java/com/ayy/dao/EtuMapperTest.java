package com.ayy.dao;

import com.ayy.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;

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

}
