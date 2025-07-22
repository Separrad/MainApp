/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private User[] users;
    private int userCount;
    private final String DATA_FILE;
    private static final int INITIAL_CAPACITY = 10;
    
    public AuthService() {
        this.DATA_FILE = getDataFilePath();
        this.users = new User[INITIAL_CAPACITY];
        this.userCount = 0;
        loadUsers();
        
        if(userCount == 0) {
            register(new User("admin", "admin@colombiawander.com", "admin123"));
        }
    }
    
    private String getDataFilePath() {
        return "colombia_wander_users.csv";
    }
    
    // Método para encontrar un usuario por email
    private User findUserByEmail(String email) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getEmail().equalsIgnoreCase(email)) {
                return users[i];
            }
        }
        return null;
    }
    
    // Método para verificar si un email ya existe
    private boolean containsEmail(String email) {
        return findUserByEmail(email) != null;
    }
    
    // Método para agregar un nuevo usuario
    private void addUser(User user) {
        if (userCount >= users.length) {
            // Redimensionar el array si es necesario
            User[] newUsers = new User[users.length * 2];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            users = newUsers;
        }
        users[userCount++] = user;
    }
    
    public boolean authenticate(String email, String password) {
        if(email == null || password == null) return false;
        User user = findUserByEmail(email.toLowerCase());
        return user != null && user.getPassword().equals(password);
    }
    
    public boolean register(User newUser) {
        if(newUser == null || newUser.getEmail() == null || newUser.getPassword() == null) {
            return false;
        }
        
        String email = newUser.getEmail().toLowerCase();
        if(!email.contains("@") || !email.contains(".")) {
            return false;
        }
        
        if(containsEmail(email)) {
            return false;
        }
        
        addUser(newUser);
        return saveUsers();
    }
    
    public String recoverPassword(String email, String recoveryCode) {
        if(email == null || recoveryCode == null) return null;
        User user = findUserByEmail(email.toLowerCase());
        if(user != null && user.getRecoveryCode().equals(recoveryCode)) {
            return user.getPassword();
        }
        return null;
    }
    
    public boolean changePassword(String email, String currentPassword, String newPassword) {
        if(email == null || currentPassword == null || newPassword == null) {
            return false;
        }
        
        email = email.toLowerCase();
        User user = findUserByEmail(email);
        
        if(user == null || !user.getPassword().equals(currentPassword)) {
            return false;
        }
        
        user.setPassword(newPassword);
        user.setRecoveryCode(ComplexManualHash.generateHash(email + newPassword));
        return saveUsers();
    }
    
    // Métodos para manejo del CSV
    private void loadUsers() {
        File file = new File(DATA_FILE);
        if(file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
                String line;
                boolean isHeader = true;
                
                while ((line = reader.readLine()) != null) {
                    if(isHeader) {
                        isHeader = false;
                        continue;
                    }
                    
                    String[] data = line.split(",");
                    if(data.length >= 4) {
                        User user = new User(data[3], data[0], data[1]);
                        user.setRecoveryCode(data[2]);
                        addUser(user);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al cargar usuarios: " + e.getMessage());
            }
        }
    }
    
    private boolean saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            writer.write("email,password,recoveryCode,username");
            writer.newLine();
            
            for(int i = 0; i < userCount; i++) {
                User user = users[i];
                writer.write(String.format("%s,%s,%s,%s",
                    user.getEmail(),
                    user.getPassword(),
                    user.getRecoveryCode(),
                    user.getUsername()));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
            return false;
        }
    }
    
    // Método para obtener todos los usuarios (útil para pruebas)
    public User[] getAllUsers() {
        User[] result = new User[userCount];
        System.arraycopy(users, 0, result, 0, userCount);
        return result;
    }
}