package com.leo.springbootdemo.web;


import com.leo.springbootdemo.domain.Book;
import com.leo.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 獲取書單列表 @PageableDefault自動取得Pageable的實體對象,再去改內部的預設值 可判斷如page=-1時改回page=0顯示回第一頁 但如果page>總頁數的話,無法判斷
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/books")
    public String list(@PageableDefault(value = Integer.MAX_VALUE, size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){

//        List<Book> books = bookService.findAll();
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Page<Book> page1 = bookService.findAllByPage(pageable);
        model.addAttribute("page", page1);
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
    public String inputPage(Model model){
        model.addAttribute("book", new Book());
        return "input";
    }

    /**
     * 跳轉到更新頁面input
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/books/{id}/input")
    public String inputEditPage(@PathVariable long id, Model model){
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "input";
    }



    /**
     * 提交一個書單信息。   redirect 返回books頁面
     * redirect: 重定向路徑
     * @param book
     * @return
     */
    @PostMapping("/books")
    public String post(Book book, final RedirectAttributes attributes){

        Book book1 = bookService.save(book);
        if(book1 != null){
            //添加閃存 就可以跨過redirect 從post~~到get
            attributes.addFlashAttribute("message", "<" + book1.getName() + "> 信息提交成功");
        }
        return "redirect:/books";
    }

    //Model 只能在一個請求中使用

    /**
     * POST ---> redirect ---> (GET)/books
     */


    @GetMapping("/books/{id}/delete")
    public String delete(@PathVariable long id, final RedirectAttributes attributes){
        bookService.deleteById(id);
        attributes.addFlashAttribute("message", "<" + "> 刪除成功");
        return "redirect:/books";
    }



}
