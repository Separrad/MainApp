/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.User;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, User> users;
    
    public AuthService() {
        users = new HashMap<>();
        // Usuario de prueba
        users.put("admin@example.com", new User("admin", "admin@example.com", "admin123"));
    }
    
    public boolean authenticate(String email, String password) {
        User user = users.get(email);
        return user != null && user.getPassword().equals(password);
    }
    
    public boolean register(User newUser) {
        if (users.containsKey(newUser.getEmail())) {
            return false; // Usuario ya existe
        }
        users.put(newUser.getEmail(), newUser);
        return true;
    }
}