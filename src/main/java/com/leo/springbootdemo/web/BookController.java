package com.leo.springbootdemo.web;


import com.leo.springbootdemo.domain.Book;
import com.leo.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 獲取書單列表
     * @param model
     * @return
     */
    @GetMapping("/books")
    public String list(Model model){
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    /**
     * 獲取書單詳情
     * @param id
     * @param model
     * @return
     */
    //返回模板的名字
    @GetMapping("/books/{id}")
    public String detail(@PathVariable long id, Model model){
        Book book = bookService.findById(id);
        //回傳物件名稱, 物件
        model.addAttribute("book", book);
        //因此類別為Controller 故回傳值會導向book.html
        return "book";
    }

    /**
     * 跳轉input提交葉面
     * @return
     */
    @GetMapping("/books/input")
    public String inputPage(){
        return "input";
    }

    /**
     * 提交一個書單信息。   redirect 返回books頁面
     * redirect: 重定向路徑
     * @param book
     * @return
     */
    @PostMapping("/books")
    public String post(Book book){
        bookService.save(book);
        return "redirect:/books";
    }

}
