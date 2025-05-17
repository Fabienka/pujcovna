package cz.bojdova.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cz.bojdova.dao.BookDao;
import cz.bojdova.model.Book;

public class BookDaoImpl implements BookDao {
    private Gson gson = new Gson();
    Scanner scanner = null;
    // Implementace metod pro práci s knihami
    @Override
    public List<Book> getAllBooks() {
        try {
            scanner = new Scanner(new File("books.json"));
            // Načtení obsahu souboru do Stringu
            StringBuilder jsonBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonBuilder.append(scanner.nextLine());
            }
            String json = jsonBuilder.toString();
            // Převod JSON na pole objektů Book
            Book[] booksArray = gson.fromJson(json, Book[].class);
            // Převod pole na seznam
            List<Book> books = List.of(booksArray);
            return books;
        } catch (FileNotFoundException e) {
            System.out.println("Soubor nebyl nalezen.");
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.out.println("Nastala chyba při čtení souboru. JSON není validní." + e.getMessage());
        } catch (Exception e) {
            System.out.println("Nastala neočekávaná chyba při čtení souboru.");
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        // Pokud dojde k chybě, vrátíme prázdný seznam
        return null; 
    }

    @Override
    public void saveAllBooks(List<Book> books) {
        // Implementace pro uložení seznamu knih do souboru
        try {
            String json = gson.toJson(books);
            // Uložení JSON do souboru
            File file = new File("books.json");
            java.nio.file.Files.write(file.toPath(), json.getBytes());
        } catch (Exception e) {
            System.out.println("Nastala chyba při ukládání dat do souboru.");
            e.printStackTrace();
        }
    }
}
