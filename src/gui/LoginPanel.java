/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.AuthService;
import model.User;

public class LoginPanel extends JPanel {
    private final MainFrame mainFrame;
    private final AuthService authService;
    private final JTextField emailField;
    private final JPasswordField passwordField;

    public LoginPanel(MainFrame mainFrame, AuthService authService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel titleLabel = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);
        
        // Campos de formulario
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Correo electrónico:"), gbc);
        
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Iniciar Sesión");
        JButton registerButton = new JButton("Registrarse");
        
        loginButton.addActionListener(e -> attemptLogin());
        registerButton.addActionListener(e -> showRegistrationDialog());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void attemptLogin() {
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());
    
    if (authService.authenticate(email, password)) {
        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        mainFrame.onSuccessfulLogin(); // Método nuevo que habilita la navegación
    } else {
        JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void showRegistrationDialog() {
        String email = JOptionPane.showInputDialog(this, "Ingrese su correo electrónico:", "Registro", JOptionPane.QUESTION_MESSAGE);
        
        if (email != null && !email.isEmpty()) {
            String password = JOptionPane.showInputDialog(this, "Ingrese una contraseña:", "Registro", JOptionPane.QUESTION_MESSAGE);
            
            if (password != null && !password.isEmpty()) {
                String username = email.split("@")[0];
                if (authService.register(new User(username, email, password))) {
                    JOptionPane.showMessageDialog(this, "Registro exitoso! Ahora puede iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "El correo ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}