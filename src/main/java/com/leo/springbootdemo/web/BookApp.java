package com.leo.springbootdemo.web;

import com.leo.springbootdemo.domain.Book;
import com.leo.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//外部Controller
@RestController
@RequestMapping("/api/v1")
public class BookApp {

    @Autowired
    private BookService bookService;

    //獲取讀書清單列表
    @GetMapping("/books")
    public List<Book> getAll(){
        return bookService.findAll();
    }

//    /**
//     * 新增一筆書 到資料庫
//     * @param name
//     * @param author
//     * @param description
//     * @param status
//     * @param isbn
//     * @return
//     */
//    @PostMapping("/books")
//    public Book post(@RequestParam String name,
//                     @RequestParam String author,
//                     @RequestParam String description,
//                     @RequestParam int status,
//                     @RequestParam String isbn){
//        Book book = new Book();
//        book.setName(name);
//        book.setAuthor(author);
//        book.setDescription(description);
//        book.setStatus(status);
//        book.setIsbn(isbn);
//        return bookService.save(book);
//    }

    //傳送一個物件實體參數也可以
    @PostMapping("/books")
    public Book post(Book book){
        return bookService.save(book);

    }

    /**
     * 以id獲取一條書單信息
     * @param id
     * @return
     */
    @GetMapping("/books/{id}")
    public Optional<Book> getOne(@PathVariable long id){
        return bookService.findById(id);
    }

    /**
     * 更新一個書單
     * @param id
     * @param name
     * @param author
     * @param description
     * @param status
     * @param isbn
     * @return
     */
    @PutMapping("/books")
    public Book update(@RequestParam long id,
                       @RequestParam String name,
                       @RequestParam String author,
                       @RequestParam String description,
                       @RequestParam int status,
                       @RequestParam String isbn){
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        book.setStatus(status);
        book.setIsbn(isbn);
        return bookService.save(book);

    }

    /**
     * 刪除書單
     * @param id
     */
    @DeleteMapping("/books/{id}")
    public void deleteById(@PathVariable long id){
        bookService.deleteById(id);

    }

    @PostMapping("/books/by")
    public List<Book> findBy(@RequestParam int len
                             ){
//        return bookService.findByAuthor(author);
//        return bookService.findByAuthorAndStatus(author, status);
//        return bookService.findByDescriptionEndsWith(description);
//        return bookService.findByDescriptionContains(description);
        return bookService.findByJPQL(len);
    }




}
