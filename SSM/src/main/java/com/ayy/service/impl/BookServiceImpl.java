package com.ayy.service.impl;

import com.ayy.bean.Books;
import com.ayy.dao.BookMapper;
import com.ayy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/03/2021
 * @ Version 1.0
 */
@Service
public class BookServiceImpl implements BookService {
    private BookMapper mapper;

    @Autowired
    public void setMapper(BookMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int addBook(Books books) {
        return mapper.addBook(books);
    }

    @Override
    public int deleteBookById(int id) {
        return mapper.deleteBookById(id);
    }

    @Override
    public int updateBook(Books books) {
        return mapper.updateBook(books);
    }

    @Override
    public Books queryBookById(int id) {
        return mapper.queryBookById(id);
    }

    @Override
    public List<Books> queryAllBooks() {
        return mapper.queryAllBooks();
    }

    @Override
    public List<Books> queryBookByName(String name) {
        return mapper.queryBookByName("%"+name+"%");
    }
}
