/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;
import service.ComplexManualHash;
public class User {
    private String username;
    private String email;
    private String password;
    private String recoveryCode;
    
    public User() {} // Constructor vacío necesario para CSV

    public User(String username, String email, String password) {
        this.username = username != null ? username : email.split("@")[0];
        this.email = email != null ? email.toLowerCase() : null;
        this.password = password;
        this.recoveryCode = ComplexManualHash.generateHash(email + password);
    }
    
    // Getters y setters (mantener todos los existentes)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRecoveryCode() { return recoveryCode; }
    public void setRecoveryCode(String recoveryCode) { this.recoveryCode = recoveryCode; }
}