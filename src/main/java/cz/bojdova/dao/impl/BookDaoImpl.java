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
            scanner = new Scanner(new File("bookache/books.json"));
            // Předpokládáme, že soubor obsahuje JSON pole knih
            StringBuilder jsonBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonBuilder.append(scanner.nextLine());
            }
            String json = jsonBuilder.toString();
            // Předpokládáme, že máme třídu Book, která odpovídá struktuře JSON
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
        
        // Zde byste měli implementovat logiku pro načtení všech knih z databáze nebo jiného úložiště
        return null; // Návrat prázdného seznamu jako příklad
    }

    @Override
    public void saveAllBooks(List<Book> books) {
        // Zde byste měli implementovat logiku pro uložení seznamu knih do databáze nebo jiného úložiště
    }
}
