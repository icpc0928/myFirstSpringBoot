package com.leo.springbootdemo.web;

import com.leo.springbootdemo.domain.Book;
import com.leo.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//外部Controller
@RestController
@RequestMapping("/api/v1")
public class BookApp {

    @Autowired
    private BookService bookService;

//    //獲取讀書清單列表
//    @GetMapping("/books")
//    public List<Book> getAll(){
//        return bookService.findAll();
//
//    }

    //獲取讀書清單列表 (分頁)
    @GetMapping("/books")
    public Page<Book> getAll(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        /**
         * {
         *     "content": [      //內容
         *         {
         *             "id": 21,
         *             "name": "安安",
         *             "author": "Leo",
         *             "isbn": "000",
         *             "description": "jj202020",
         *             "status": 0
         *         },
         *         {
         *             "id": 20,
         *             "name": "安安",
         *             "author": "Leo",
         *             "isbn": "000",
         *             "description": "jj202020",
         *             "status": 1
         *         },
         *         {
         *             "id": 19,
         *             "name": "五號",
         *             "author": "作者",
         *             "isbn": "4546564656",
         *             "description": "d d",
         *             "status": 2
         *         },
         *         {
         *             "id": 17,
         *             "name": "安安",
         *             "author": "Leo",
         *             "isbn": "000",
         *             "description": "jj;j'j'jlk';",
         *             "status": 0
         *         },
         *         {
         *             "id": 16,
         *             "name": "安安",
         *             "author": "Leo",
         *             "isbn": "000",
         *             "description": "jj;j'j'jlk';",
         *             "status": 0
         *         }
         *     ],
         *     "pageable": {
         *         "sort": {
         *             "sorted": true,
         *             "unsorted": false,
         *             "empty": false
         *         },
         *         "offset": 5,
         *         "pageNumber": 1,
         *         "pageSize": 5,
         *         "unpaged": false,
         *         "paged": true
         *     },
         *     "totalElements": 25,  總共有幾個項目
         *     "totalPages": 5,      總共有幾頁
         *     "last": false,        是否是最後一頁
         *     "number": 1,          目前是第幾頁 0開始
         *     "size": 5,            總共幾頁
         *     "sort": {
         *         "sorted": true,
         *         "unsorted": false,
         *         "empty": false
         *     },
         *     "numberOfElements": 5, 當前頁有幾個項目 (如果是最後一頁可能不滿預設個數)
         *     "first": false,
         *     "empty": false
         * }
         */

//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return bookService.findAllByPage(pageable);

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
    public Book getOne(@PathVariable long id){
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
    public int findBy(@RequestParam long id,
                      @RequestParam int status,
                      @RequestParam long uid){
//        return bookService.findByAuthor(author);
//        return bookService.findByAuthorAndStatus(author, status);
//        return bookService.findByDescriptionEndsWith(description);
//        return bookService.findByDescriptionContains(description);
//        return bookService.findByJPQL(len);
//        return bookService.updateByJPQL(status, id);
//        return bookService.deleteByJPQL(id);
        return bookService.deleteAndUpdate(id, status, uid);
    }





}
