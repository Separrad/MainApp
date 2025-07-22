package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import model.Restaurant;

public class RestaurantReservationDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public RestaurantReservationDialog(JDialog parent, Restaurant restaurant) {
        super(parent, "Reservar en " + restaurant.getName(), true);
        setLayout(new BorderLayout());
        setSize(600, 600);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = createGradientPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // Panel para centrar el contenido
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(contentPanel);
        
        addReservationTitle(contentPanel, restaurant);
        addReservationSteps(contentPanel);
        addReservationForm(contentPanel);
        addReservationButtons(contentPanel, restaurant);
        
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(centerPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(100, 10, 10),
                    0, getHeight(), new Color(40, 0, 0)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private void addReservationTitle(JPanel contentPanel, Restaurant restaurant) {
        JLabel titleLabel = new JLabel("Reservar en " + restaurant.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
    }

    private void addReservationSteps(JPanel contentPanel) {
        JPanel stepsPanel = new JPanel();
        stepsPanel.setLayout(new BoxLayout(stepsPanel, BoxLayout.Y_AXIS));
        stepsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200, 150)), 
            "Proceso de Reserva",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            Color.WHITE
        ));
        stepsPanel.setBackground(new Color(255, 255, 255, 180));
        stepsPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        
        String[] steps = {
            "1. Verifica disponibilidad para la fecha deseada",
            "2. Selecciona el número de personas",
            "3. Elige tu horario preferido",
            "4. Proporciona tus datos de contacto",
            "5. Confirma tu reserva"
        };
        
        for (String step : steps) {
            JLabel stepLabel = new JLabel(step);
            stepLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            stepLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            stepsPanel.add(stepLabel);
        }
        
        contentPanel.add(stepsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
    }

    private void addReservationForm(JPanel contentPanel) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200, 150)), 
            "Datos de la Reserva",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            Color.WHITE
        ));
        formPanel.setBackground(new Color(255, 255, 255, 180));
        formPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Fila 1 - Fecha
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createFormLabel("Fecha:"), gbc);
        
        gbc.gridx = 1;
        JTextField dateField = new JTextField(20);
        dateField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(dateField, gbc);
        
        // Fila 2 - Personas
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createFormLabel("Personas:"), gbc);
        
        gbc.gridx = 1;
        JSpinner peopleSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 20, 1));
        peopleSpinner.setPreferredSize(new Dimension(200, 30));
        formPanel.add(peopleSpinner, gbc);
        
        // Fila 3 - Hora
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(createFormLabel("Hora:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> timeCombo = new JComboBox<>(new String[]{"12:00", "13:00", "14:00", "19:00", "20:00", "21:00"});
        timeCombo.setPreferredSize(new Dimension(200, 30));
        formPanel.add(timeCombo, gbc);
        
        // Fila 4 - Nombre
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(createFormLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(nameField, gbc);
        
        // Fila 5 - Teléfono
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(createFormLabel("Teléfono:"), gbc);
        
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(20);
        phoneField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(phoneField, gbc);
        
        // Fila 6 - Email
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(createFormLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(emailField, gbc);
        
        contentPanel.add(formPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
    }

    private void addReservationButtons(JPanel contentPanel, Restaurant restaurant) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton cancelButton = createCancelButton();
        JButton confirmButton = createConfirmButton(restaurant);
        
        // Establecer mismo tamaño para ambos botones
        Dimension buttonSize = new Dimension(180, 40);
        confirmButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(cancelButton);
        
        contentPanel.add(buttonPanel);
    }

    private JButton createConfirmButton(Restaurant restaurant) {
        JButton button = new JButton("Confirmar Reserva");
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(50, 150, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 100, 30), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Reserva confirmada en " + restaurant.getName(), 
                "Reserva Confirmada", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
        
        return button;
    }

    private JButton createCancelButton() {
        JButton button = new JButton("Cancelar");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(180, 30, 30));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(120, 20, 20), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.addActionListener(e -> dispose());
        return button;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }
}