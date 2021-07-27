package com.leo.springbootdemo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

//interface 要繼承JpaRep... 泛型: 實體類對象 跟 ID
public interface BookRepository extends JpaRepository<Book, Long> {

    //JPA官方文檔有規範名稱怎麼命名:
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

    //自定義一個方法供使用 但命稱有規範
    List<Book> findByAuthor(String author);

    //當一次傳兩個參數過來
    List<Book> findByAuthorAndStatus(String author, int status);

    //以甚麼結尾的查詢
    List<Book> findByDescriptionEndsWith(String des);

    //包含的語句
    List<Book> findByDescriptionContains(String des);

    //自定義查詢  用nativeQuery = true 來直接用sql語法來做直接查詢 但還是要對帶入的參數做 ?1 , ?2 ....
//    @Query("select b from Book b where length(b.name) > ?1")
    @Query(value = "SELECT * FROM book  WHERE CHAR_LENGTH(name) > ?1" , nativeQuery = true )
    List<Book> findByJPQL(int length);

    //依照id更改status (update語法 不被支援會報 Not supported for DML,故需要增加@Modifying)
    @Transactional  //納入事務管理 如果使用update/delete需要使用
    @Modifying
    @Query("update Book b set b.status = ?1 where b.id = ?2")
    int updateByJPQL(int status, long id);

    @Transactional  //納入事務管理 如果使用update/delete需要使用
    @Modifying
    @Query("delete from Book b where b.id = ?1")
    int deleteByJPQL(long id);

}
