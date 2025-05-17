package cz.bojdova.dao;

import java.util.List;

import cz.bojdova.model.Book;
    
public interface BookDao {
    List<Book> getAllBooks();
    void saveAllBooks(List<Book> books);
}
