package com.leo.springbootdemo.service;

import com.leo.springbootdemo.domain.Book;
import com.leo.springbootdemo.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //直接調用JpaRepository的方法
    //查詢所有書單列表
    public List<Book> findAll(){
        return bookRepository.findAll();
    }


    /**
     * 提交新增一個書單信息
     * @param book
     * @return
     */
    public Book save(Book book){
        return bookRepository.save(book);
    }

    /**
     * 獲取一條書單信息
     * @param id
     * @return
     */
    public Optional<Book> findById(long id){
        return bookRepository.findById(id);
    }

    /**
     * 刪除一條書單信息
     * @param id
     */
    public void deleteById(long id){
        bookRepository.deleteById(id);
    }


    /***
     * 根據author返回全部書單列表
     * @param author
     * @return
     */
    public List<Book> findByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }

    /**
     * 根據author & status 查詢書單列表
     * @param author
     * @param status
     * @return
     */
    public List<Book> findByAuthorAndStatus(String author, int status){
        return bookRepository.findByAuthorAndStatus(author, status);
    }

    /**
     * 以甚麼為結尾的like查詢 => %' '
     * @param des
     * @return
     */
    public List<Book> findByDescriptionEndsWith(String des){
        return bookRepository.findByDescriptionEndsWith(des);
    }

    /***
     * 包含某些關鍵字的查詢 => %' '%
     * @param des
     * @return
     */
    public List<Book> findByDescriptionContains(String des){
        return bookRepository.findByDescriptionContains(des);
    }

    //自定義查詢
    public List<Book> findByJPQL(int len){
        return bookRepository.findByJPQL(len);
    }


}
