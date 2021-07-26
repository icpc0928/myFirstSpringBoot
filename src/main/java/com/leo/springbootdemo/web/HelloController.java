package com.leo.springbootdemo.web;

import com.leo.springbootdemo.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//整理 @Controller 與 @RestController 的差異: @Controller 最後會回到視圖解吸器(就是WebPage頁面),在Java中傳統的頁面都是jsp組成,但是SpringBoot並沒有預設支持jsp頁面,需另外處裡
//@RestController則是 @ResponseBody + @Controller 的結合, 可返回JSON的格式給客端(就是RESTful Service常用的傳送資料格式) 而要回傳response一個頁面的話 SpringBoot官方建議Thymeleaf模板引擎

//@Controller         //裡面所做的mapping 若返回String 將會返回對應的path  (pom 需要導入 spring-boot-starter-thymeleaf 模板)   (for模板式的開發)
@RestController   //裡面所做的mapping 若返回String 則保持返回String                                                      (單純回傳)
@RequestMapping("/api/v1")
public class HelloController {

//    @Value("${book.name}")
//    private String name;
//    @Value("${book.author}")
//    private String author;
//    @Value("${book.isbn}")
//    private String isbn;
//    @Value("${book.description}")
//    private String description;

//    @Autowired
//    private Book book;


    //RESTful
    //@RestController 在class上註解 ,使該class作為外部控制器 讓外部的請求可連上這
    /*@RequestMapping 請求映射 用在方法or Class上 , 作為請求映射的作用 並根據String做映射
        *直接打路徑 (不定義方法則都能支持各種方法(get,post...)
        * 另一種方法:
        * value = "/leo01"(路徑) , method = RequestMethod.GET (請求方法)
        *RequestMapping也可以放在Class上 路徑上則為 localhost:8080/"class的路徑"/"class內的路徑"
    */


//    @RequestMapping("/leo01")
    @RequestMapping(value = "/leo01", method = RequestMethod.GET)
    public String hello(){
        return "Hello Spring Boot";
    }

    // RequestMapping 的簡寫方式 PostMapping DeleteMapping...
    @PostMapping("/leo01_post")
    public String helloPost(){
        return "Post";
    }
    @GetMapping("/leo01_get")
    public String helloGet(){
        return "Get";
    }

    @GetMapping("/leo02")
    public String getPath(){
        return "leo02";
    }

    @GetMapping("/leo03")
//    @ResponseBody   //如果類別是返回模板的方式,這裡額外用這個,讓他返回目前自訂的格式(如String/JSON)
    public Object getString(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Hello");
        map.put("age", 18);

        return map;
    }

    //PathVariable
    //路徑傳參數  //使用@PathVariable 作為參數註解  ,但由客端傳送過來的永遠都是String 但簡單的類型這裡可以自動轉
    //且如果getMapping後定義的參數 跟方法內的參數不同,可額外定義  (故 要就定義傳送參數的名稱,不然就保持一致
    //且可對參數做正則表達式的規則 : {參數名:正則表達式}
//    @GetMapping("/books/{id}/{username:[a-z_]+}")
    @GetMapping("/books/{id}")
    public Object getOne(@PathVariable long id){
        System.out.println("id: " + id);
//        System.out.println("user: " + user);
//        Map<String, Object> book = new HashMap<>();
//        book.put("name", name);
//        book.put("isbn", isbn);
//        book.put("author", author);
////        book.put("userName" , user);
//        book.put("description", description);


//        return book;
        return null;
    }

    @PostMapping("/books")
    public Object post(@RequestParam("name") String name,
                       @RequestParam("author") String author,
                       @RequestParam("isbn") String isbn){
        Map<String, Object> book = new HashMap<>();
        book.put("name", name);
        book.put("author", author);
        book.put("isbn", isbn);
        return book;
    }

    //127.0.0.1:8080/api/v1/books?page=1&size=10
    //127.0.0.1:8080/api/v1/books?page=1        (defaultValue=> 若沒傳size參數 默認值)
    @GetMapping("/books")
    public Object getAll(@RequestParam("page") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size){

        List<Map> contents = new ArrayList<>();

        for(int i = (page - 1) * size ;i < page * size; i++){
            Map<String, Object> book = new HashMap<>();
            book.put("name", "i:" + i);
            book.put("author", "author"+ i);
            book.put("isbn", "isbn" + i);
            contents.add(book);
        }

        Map<String, Object> pagemap = new HashMap<>();
        pagemap.put("page", page);
        pagemap.put("size", size);
        pagemap.put("content", contents);

        return pagemap;
    }



}
