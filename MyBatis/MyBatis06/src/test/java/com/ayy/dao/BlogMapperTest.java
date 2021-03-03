package com.ayy.dao;

import com.ayy.bean.Blog;
import com.ayy.util.IDUtils;
import com.ayy.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/03/2021
 * @ Version 1.0
 */
public class BlogMapperTest {
    private SqlSession sqlSession;
    private BlogMapper mapper;

    @Before
    public void setUp(){
        sqlSession = MyBatisUtils.getSqlSession();
        mapper = sqlSession.getMapper(BlogMapper.class);
    }

    @After
    public void tearDown(){
        mapper = null;
        sqlSession.close();
    }

    @Test
    public void testAddBlog(){
        Blog blog = new Blog();
        blog.setBid(IDUtils.getId());
        blog.setTitle("Title");
        blog.setAuthor("AYY");
        blog.setCreateTime(new Date());
        blog.setViews(999);

        mapper.addBlog(blog);

        blog.setBid(IDUtils.getId());
        blog.setTitle("Title2");
        mapper.addBlog(blog);

        blog.setBid(IDUtils.getId());
        blog.setTitle("Title3");
        mapper.addBlog(blog);

        blog.setBid(IDUtils.getId());
        blog.setTitle("Title4");
        mapper.addBlog(blog);
    }

    @Test
    public void testQueryBlogIF(){
        Map<String,Object> map = new HashMap<>();
        List<Blog> blogs;
        System.out.println("----------------");
        blogs = mapper.queryBlogIF(map);
        blogs.forEach(System.out::println);
        System.out.println("----------------");
        map.put("title","Title3");
        blogs = mapper.queryBlogIF(map);
        blogs.forEach(System.out::println);
        System.out.println("----------------");
        map.put("author","AYY2");
        blogs = mapper.queryBlogIF(map);
        blogs.forEach(System.out::println);
        System.out.println("----------------");
    }

    @Test
    public void testQueryBlogCHOOSE(){
        Map<String,Object> map = new HashMap<>();
        List<Blog> blogs;
        map.put("views","999");
        System.out.println("----------------");
        blogs = mapper.queryBlogCHOOSE(map);
        blogs.forEach(System.out::println);
        System.out.println("----------------");
        map.put("title","Title3");
        blogs = mapper.queryBlogCHOOSE(map);
        blogs.forEach(System.out::println);
        System.out.println("----------------");
        map.put("author","AYY2");
        blogs = mapper.queryBlogCHOOSE(map);
        blogs.forEach(System.out::println);
        System.out.println("----------------");
    }

    @Test
    public void testUpdateBlog(){
        Map<String,Object> map = new HashMap<>();
        map.put("bid","'5654d8b19fd54b1db6296ade40aa4341'");
        map.put("title","Title3");
        mapper.updateBlog(map);
        System.out.println("----------------");
    }

    @Test
    public void testQueryBlogForEach(){
        Map<String,Object> map = new HashMap<>();
        List<String> ids = new ArrayList<>();
        ids.add("5654d8b19fd54b1db6296ade40aa4341");
        map.put("ids",ids);
        List<Blog> blogs = mapper.queryBlogForEach(map);
        blogs.forEach(System.out::println);
    }
}
