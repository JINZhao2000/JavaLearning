package com.ayy.dao;

import com.ayy.bean.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/03/2021
 * @ Version 1.0
 */

public interface BookMapper {
    int addBook(Books books);

    int deleteBookById(@Param("bookId") int id);

    int updateBook(Books books);

    Books queryBookById(@Param("bookId") int id);

    List<Books> queryAllBooks();

    List<Books> queryBookByName(@Param("bookName") String name);
}
