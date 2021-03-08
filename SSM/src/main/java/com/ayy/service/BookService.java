package com.ayy.service;

import com.ayy.bean.Books;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/03/2021
 * @ Version 1.0
 */

public interface BookService {
    int addBook(Books books);

    int deleteBookById(int id);

    int updateBook(Books books);

    Books queryBookById(int id);

    List<Books> queryAllBooks();

    List<Books> queryBookByName(String name);
}
