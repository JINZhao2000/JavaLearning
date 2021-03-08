package com.ayy.controller;

import com.ayy.bean.Books;
import com.ayy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/03/2021
 * @ Version 1.0
 */
@Controller
@RequestMapping("/book")
public class BookController {
    private BookService bookService;

    @Autowired
    @Qualifier("bookServiceImpl")
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/allbook")
    public String list(Model model){
        List<Books> books = bookService.queryAllBooks();
        model.addAttribute("list",books);
        return "allbook";
    }

    @RequestMapping("/toAddBook")
    public String toAddPage(){
        return "addbook";
    }

    @RequestMapping("/addbook")
    public String addBook(Books books){
        bookService.addBook(books);
        return "redirect:/book/allbook";
    }

    @RequestMapping("/toUpdateBook/{bookId}")
    public String toUpdatePage(@PathVariable int bookId, Model model){
        Books books = bookService.queryBookById(bookId);
        model.addAttribute("book",books);
        return "updatebook";
    }

    @RequestMapping("/updatebook")
    public String updateBook(Books books){
        bookService.updateBook(books);
        return "redirect:/book/allbook";
    }

    @RequestMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable int bookId){
        bookService.deleteBookById(bookId);
        return "redirect:/book/allbook";
    }

    @RequestMapping("/queryBook")
    public String queryBook(String queryBookName, Model model){
        List<Books> books = bookService.queryBookByName(queryBookName);
        model.addAttribute("list",books);
        return "allbook";
    }
}
