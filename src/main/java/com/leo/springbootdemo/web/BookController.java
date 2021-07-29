package com.leo.springbootdemo.web;


import com.leo.springbootdemo.domain.Book;
import com.leo.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String list(){
        return "books";
    }


    //返回模板的名字
    @GetMapping("/books/{id}")
    public String detail(@PathVariable long id, Model model){
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book";
    }

}
