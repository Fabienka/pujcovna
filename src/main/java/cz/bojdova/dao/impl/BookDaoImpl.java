package cz.bojdova.dao.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cz.bojdova.dao.BookDao;
import cz.bojdova.model.Book;

public class BookDaoImpl implements BookDao {

    private static final String FILE_PATH = "books.json";
    private final Gson gson = new Gson();

    @Override
    public List<Book> getAllBooks() {
        try {
            String json = Files.readString(Path.of(FILE_PATH));
            Book[] booksArray = gson.fromJson(json, Book[].class);
            return new ArrayList<>(Arrays.asList(booksArray));
        } catch (IOException e) {
            System.err.println("Error: Cannot read or find the file '" + FILE_PATH + "'.");
        } catch (JsonSyntaxException e) {
            System.err.println("Error: Invalid JSON syntax in file '" + FILE_PATH + "'. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while reading books: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void saveAllBooks(List<Book> books) {
        try {
            String json = gson.toJson(books);
            Files.write(Path.of(FILE_PATH), json.getBytes());
        } catch (IOException e) {
            System.err.println("Error: Failed to save data to '" + FILE_PATH + "'. " + e.getMessage());
        }
    }
}
