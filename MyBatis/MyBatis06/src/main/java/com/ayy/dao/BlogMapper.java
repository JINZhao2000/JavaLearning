package com.ayy.dao;

import com.ayy.bean.Blog;

import java.util.List;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/03/2021
 * @ Version 1.0
 */

public interface BlogMapper {
    int addBlog(Blog blog);

    List<Blog> queryBlogIF(Map map);

    List<Blog> queryBlogCHOOSE(Map map);

    int updateBlog(Map map);
}
