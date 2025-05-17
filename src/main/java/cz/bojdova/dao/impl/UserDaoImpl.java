package cz.bojdova.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cz.bojdova.dao.UserDao;
import cz.bojdova.model.User;

public class UserDaoImpl implements UserDao {
    private Gson gson = new Gson();
    Scanner scanner = null;

    @Override
    public List<User> getAllUsers() {
        
        try {
            scanner = new Scanner(new File("users.json"));
            // Načtení obsahu souboru do Stringu
            StringBuilder jsonBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonBuilder.append(scanner.nextLine());
            }
            String json = jsonBuilder.toString();
            
            // Převod JSON na pole objektů User
            User[] usersArray = gson.fromJson(json, User[].class);
            System.out.println("Načtení uživatelů z JSON jako pole:");
            System.out.println(Arrays.toString(usersArray));
            List<User> users = new ArrayList<>(Arrays.asList(usersArray));

            // Převod pole na seznam
            System.out.println("Načtení uživatelů:");
            System.out.println(users);
            return users;
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
    public void saveAllUsers(List<User> users) {
        try {
            String json = gson.toJson(users);
            // Uložení JSON do souboru
            File file = new File("users.json");
            java.nio.file.Files.write(file.toPath(), json.getBytes());
        } catch (Exception e) {
            System.out.println("Nastala chyba při ukládání dat do souboru.");
            e.printStackTrace();
        }
    }
}