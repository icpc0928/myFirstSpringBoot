package com.leo.springbootdemo.service;

import com.leo.springbootdemo.domain.Book;
import com.leo.springbootdemo.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 分頁查詢單列表
     * @return
     */
    public Page<Book> findAllByPage(Pageable pageable){
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        //PageRequest已改為靜態方法 故不用new
//        Pageable pageable = PageRequest.of(1, 5, sort);
        return bookRepository.findAll(pageable);
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
     * 獲取一條書單信息  如果資料庫中沒有此id 則新增一個空的Book物件回傳 避免回傳null讓瀏覽器500
     * @param id
     * @return
     */
    public Book findById(long id){
        Optional<Book> optional = bookRepository.findById(id);
        return optional.orElse(new Book());
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

    /**
     * 自定義更新
     * @param status
     * @param id
     * @return
     */

    public int updateByJPQL(int status, long id){
        return bookRepository.updateByJPQL(status, id);
    }

    /**
     * 自定義刪除
     * @param id
     * @return
     */
    public int deleteByJPQL(long id){
        return bookRepository.deleteByJPQL(id);
    }


    //一個方法涉及到兩次操作的狀況 => 刪除後更新 刪除成功但更新失敗的話....因為有Transactional 所以會rollback回復回去
    /**
     * 測試事務操作方法
     * @param id
     * @param status
     * @param uid
     * @return
     */
    @Transactional
    public int deleteAndUpdate(long id, int status, long uid){
        int dcount = bookRepository.deleteByJPQL(id);

        int ucount = bookRepository.updateByJPQL(status, uid);

        return dcount + ucount;

    }



}
