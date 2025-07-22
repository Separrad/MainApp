/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import service.AuthService;
import model.User;

public class LoginPanel extends JPanel {
    private final MainFrame mainFrame;
    private final AuthService authService;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    
    // Paleta de colores
    private final Color DARK_RED = new Color(140, 20, 20);
    private final Color MEDIUM_RED = new Color(180, 30, 30);
    private final Color LIGHT_RED = new Color(220, 50, 50);
    private final Color WHITE = Color.WHITE;
    private final Color LIGHT_GRAY = new Color(240, 240, 240);
    private final Color DARK_TEXT = new Color(60, 60, 60);

    public LoginPanel(MainFrame mainFrame, AuthService authService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        this.emailField = new JTextField();
        this.passwordField = new JPasswordField();
        
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new GridBagLayout());
        setBackground(DARK_RED); // Fondo rojo claro para todo el panel
        
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setOpaque(false);
        mainContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel del título con el lema
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JLabel appTitle = new JLabel("COLOMBIA WANDER", SwingConstants.CENTER);
        try {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, 
                getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            appTitle.setFont(montserrat.deriveFont(34f));
        } catch (Exception e) {
            appTitle.setFont(new Font("Verdana", Font.BOLD, 34));
        }
        appTitle.setForeground(WHITE);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel mottoLabel = new JLabel("Descubre cada paso, vive cada aventura", SwingConstants.CENTER);
        mottoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        mottoLabel.setForeground(new Color(255, 255, 255, 200));
        mottoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(appTitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(mottoLabel);
        
        JPanel loginForm = createLoginForm();
        
        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(loginForm, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.weightx = gbc.weighty = 1;
        add(mainContainer, gbc);
    }

    private JPanel createLoginForm() {
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(WHITE);
        formCard.setBorder(new CompoundBorder(
            new LineBorder(DARK_RED, 2), // Borde blanco más grueso
            new EmptyBorder(25, 25, 25, 25)
        ));
        formCard.setPreferredSize(new Dimension(350, 420));
        
        JLabel title = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(DARK_TEXT);
        title.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        // Campos email y contraseña
        gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Correo electrónico:"), gbc);
        
        gbc.gridy = 1;
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_GRAY),
            new EmptyBorder(8, 5, 8, 5)
        ));
        fieldsPanel.add(emailField, gbc);
        
        gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridy = 3;
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_GRAY),
            new EmptyBorder(8, 5, 8, 5)
        ));
        fieldsPanel.add(passwordField, gbc);
        
        // Botones
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        JButton forgotBtn = createLinkButton("¿Olvidaste tu contraseña?");
        forgotBtn.addActionListener(e -> showPasswordRecoveryDialog());
        fieldsPanel.add(forgotBtn, gbc);
        
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton changePassBtn = createLinkButton("Cambiar contraseña");
        changePassBtn.addActionListener(e -> showChangePasswordDialog());
        fieldsPanel.add(changePassBtn, gbc);
        
        gbc.gridy = 6;
        JButton loginBtn = createMainButton("ACCEDER");
        loginBtn.addActionListener(e -> attemptLogin());
        fieldsPanel.add(loginBtn, gbc);
        
        gbc.gridy = 7;
        JSeparator separator = new JSeparator();
        separator.setForeground(LIGHT_GRAY);
        fieldsPanel.add(separator, gbc);
        
        gbc.gridy = 8;
        JButton registerBtn = createSecondaryButton("Crear una cuenta nueva");
        registerBtn.addActionListener(e -> showRegistrationDialog());
        fieldsPanel.add(registerBtn, gbc);
        
        formCard.add(title, BorderLayout.NORTH);
        formCard.add(fieldsPanel, BorderLayout.CENTER);
        
        return formCard;
    }
    
    private JButton createMainButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(MEDIUM_RED);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 40));
        button.setBorder(new EmptyBorder(10, 30, 10, 30));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(LIGHT_RED);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(MEDIUM_RED);
            }
        });
        return button;
    }
    
    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(DARK_TEXT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(MEDIUM_RED);
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(DARK_TEXT);
            }
        });
        return button;
    }
    
    private JButton createLinkButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        button.setForeground(DARK_TEXT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(MEDIUM_RED);
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(DARK_TEXT);
            }
        });
        return button;
    }
    
    private void attemptLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (email.isEmpty() || password.isEmpty()) {
            showError("Por favor complete todos los campos");
            return;
        }
        
        if (authService.authenticate(email, password)) {
            showSuccess("Bienvenido");
            mainFrame.onSuccessfulLogin();
        } else {
            showError("Credenciales incorrectas");
            passwordField.setText("");
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 2, 0, MEDIUM_RED),
            new EmptyBorder(8, 5, 8, 5)
        ));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 2, 0, MEDIUM_RED),
            new EmptyBorder(8, 5, 8, 5)
        ));
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_GRAY),
            new EmptyBorder(8, 5, 8, 5)
        ));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_GRAY),
            new EmptyBorder(8, 5, 8, 5)
        ));
    }
    
    private void showRegistrationDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Registro de Nuevo Usuario");
        dialog.setModal(true);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);
        
        JLabel title = new JLabel("REGISTRO", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(MEDIUM_RED);
        panel.add(title, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(WHITE);
        
        JTextField regEmail = new JTextField();
        JPasswordField regPass = new JPasswordField();
        JPasswordField regConfirmPass = new JPasswordField();
        
        styleField(regEmail);
        styleField(regPass);
        styleField(regConfirmPass);
        
        formPanel.add(createLabeledField("Correo electrónico", regEmail));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(createLabeledField("Contraseña", regPass));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(createLabeledField("Confirmar contraseña", regConfirmPass));
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        JButton registerBtn = createMainButton("REGISTRARSE");
        registerBtn.addActionListener(e -> {
            String email = regEmail.getText().trim();
            String password = new String(regPass.getPassword()).trim();
            String confirmPassword = new String(regConfirmPass.getPassword()).trim();
            
            if(email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Todos los campos son obligatorios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Las contraseñas no coinciden", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(dialog, 
                    "Por favor ingrese un correo electrónico válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            User newUser = new User(email.split("@")[0], email, password);
            if(authService.register(newUser)) {
                JOptionPane.showMessageDialog(dialog, 
                    "GUARDE ESTE CÓDIGO EN UN LUGAR SEGURO.\n" +
                    "Quien tenga acceso a él podrá recuperar su contraseña.",
                    "Advertencia de Seguridad", 
                    JOptionPane.WARNING_MESSAGE);
                
                JPanel codePanel = new JPanel(new BorderLayout());
                codePanel.add(new JLabel("<html><center>Registro exitoso!<br><br>" +
                    "<b>Código de recuperación:</b></center></html>"), BorderLayout.NORTH);
                
                JTextField codeField = new JTextField(newUser.getRecoveryCode());
                codeField.setEditable(false);
                codeField.setHorizontalAlignment(JTextField.CENTER);
                codeField.setFont(new Font("Monospaced", Font.BOLD, 16));
                codePanel.add(codeField, BorderLayout.CENTER);
                
                JOptionPane.showMessageDialog(dialog, codePanel, 
                    "Código de Recuperación", JOptionPane.INFORMATION_MESSAGE);
                
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, 
                    "El correo ya está registrado", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(WHITE);
        buttonPanel.add(registerBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showPasswordRecoveryDialog() {
        JDialog dialog = new JDialog(mainFrame, "Recuperar Contraseña", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);
        
        JTextField emailField = new JTextField();
        JTextField codeField = new JTextField();
        
        styleField(emailField);
        styleField(codeField);
        
        panel.add(createLabeledField("Correo electrónico:", emailField));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(createLabeledField("Código de recuperación:", codeField));
        
        JButton recoverBtn = createMainButton("Recuperar Contraseña");
        JButton cancelBtn = createSecondaryButton("Cancelar");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(recoverBtn);
        buttonPanel.add(cancelBtn);
        
        recoverBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String code = codeField.getText().trim();
            
            if (email.isEmpty() || code.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Por favor complete todos los campos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String password = authService.recoverPassword(email, code);
            if (password != null) {
                JPanel resultPanel = new JPanel(new BorderLayout());
                resultPanel.add(new JLabel("<html><center>Contraseña recuperada:<br><br>" +
                    "<b>" + password + "</b><br><br>" +
                    "Ahora puede iniciar sesión</center></html>"), 
                    BorderLayout.CENTER);
                
                JOptionPane.showMessageDialog(dialog, resultPanel, 
                    "Contraseña Recuperada", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, 
                    "Correo o código de recuperación incorrectos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }
    
    private void showChangePasswordDialog() {
        JDialog dialog = new JDialog(mainFrame, "Cambiar Contraseña", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(WHITE);
        
        JTextField emailField = new JTextField();
        JPasswordField currentPassField = new JPasswordField();
        JPasswordField newPassField = new JPasswordField();
        JPasswordField confirmPassField = new JPasswordField();
        
        styleField(emailField);
        styleField(currentPassField);
        styleField(newPassField);
        styleField(confirmPassField);
        
        panel.add(createLabeledField("Correo electrónico:", emailField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabeledField("Contraseña actual:", currentPassField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabeledField("Nueva contraseña:", newPassField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabeledField("Confirmar nueva contraseña:", confirmPassField));
        
        JButton changeBtn = createMainButton("Cambiar Contraseña");
        JButton cancelBtn = createSecondaryButton("Cancelar");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(changeBtn);
        buttonPanel.add(cancelBtn);
        
        changeBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String currentPass = new String(currentPassField.getPassword());
            String newPass = new String(newPassField.getPassword());
            String confirmPass = new String(confirmPassField.getPassword());
            
            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Las nuevas contraseñas no coinciden", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (authService.changePassword(email, currentPass, newPass)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Contraseña cambiada exitosamente!\n" +
                    "Su código de recuperación también ha sido actualizado.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, 
                    "Correo o contraseña actual incorrectos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }
    
    private void styleField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_GRAY),
            new EmptyBorder(8, 5, 8, 5)
        ));
    }
        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. Degradado rojo (oscuro arriba → muy oscuro abajo)
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(100, 10, 10),      // Top: DARK_RED
            0, getHeight(), new Color(40, 0, 0) // Bottom: Casi negro
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 2. Montañas (blanco semitransparente)
        g2d.setColor(new Color(255, 255, 255, 50)); // 20% opacidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Montaña izquierda (más alta)
        Path2D montLeft = new Path2D.Double();
        montLeft.moveTo(0, getHeight());
        montLeft.curveTo(
            getWidth() * 0.2, getHeight(),          // Control 1 (base)
            getWidth() * 0.25, getHeight() * 0.4,   // Control 2 (subida)
            getWidth() * 0.4, getHeight()           // Final (bajada)
        );
        g2d.fill(montLeft);

        // Montaña derecha (más pequeña)
        Path2D montRight = new Path2D.Double();
        montRight.moveTo(getWidth() * 0.6, getHeight());
        montRight.curveTo(
            getWidth() * 0.7, getHeight() * 0.6,
            getWidth() * 0.8, getHeight() * 0.5,
            getWidth(), getHeight()
        );
        g2d.fill(montRight);

        // Montaña central (la más grande)
        Path2D montCenter = new Path2D.Double();
        montCenter.moveTo(getWidth() * 0.3, getHeight());
        montCenter.curveTo(
            getWidth() * 0.5, getHeight() * 0.3,   // Pico alto
            getWidth() * 0.7, getHeight() * 0.4,
            getWidth() * 0.8, getHeight()
        );
        g2d.fill(montCenter);
    }
    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(DARK_TEXT);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
}