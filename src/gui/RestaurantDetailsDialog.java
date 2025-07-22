package gui;

import javax.swing.*;
import java.awt.*;
import model.Restaurant;

public class RestaurantDetailsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public RestaurantDetailsDialog(JFrame parent, Restaurant restaurant) {
        super(parent, restaurant.getName(), true);
        setLayout(new BorderLayout());
        setSize(650, 750);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = createGradientPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(contentPanel);
        
        addRestaurantImage(contentPanel, restaurant);
        addReservationButton(contentPanel, restaurant);
        addDescription(contentPanel, restaurant);
        addFeaturedDishes(contentPanel, restaurant);
        addContactInfo(contentPanel, restaurant);
        
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

    private void addRestaurantImage(JPanel contentPanel, Restaurant restaurant) {
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon originalIcon = restaurant.getImagePath() != null ? 
                new ImageIcon(getClass().getResource(restaurant.getImagePath())) :
                new ImageIcon(getClass().getResource("/images/default_restaurant.jpg"));
            
            Image scaledImage = originalIcon.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        } catch (Exception e) {
            imageLabel.setText("Imagen no disponible");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        contentPanel.add(imageLabel);
    }

    private void addReservationButton(JPanel contentPanel, Restaurant restaurant) {
        JButton reserveButton = new JButton("Reservar");
        reserveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        reserveButton.setBackground(new Color(50, 150, 50));
        reserveButton.setForeground(Color.WHITE);
        reserveButton.setFocusPainted(false);
        reserveButton.setBorderPainted(false);
        reserveButton.setOpaque(true);
        reserveButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        reserveButton.addActionListener(e -> new RestaurantReservationDialog(this, restaurant));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(reserveButton);
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addDescription(JPanel contentPanel, Restaurant restaurant) {
        JTextArea descriptionArea = new JTextArea(restaurant.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(255, 255, 255, 180));
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Descripción"),
            BorderFactory.createEmptyBorder(5, 5, 15, 5)
        ));
        contentPanel.add(descriptionArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void addFeaturedDishes(JPanel contentPanel, Restaurant restaurant) {
        JPanel dishesPanel = new JPanel();
        dishesPanel.setLayout(new BoxLayout(dishesPanel, BoxLayout.Y_AXIS));
        dishesPanel.setBorder(BorderFactory.createTitledBorder("Platillos Destacados"));
        dishesPanel.setBackground(new Color(255, 255, 255, 180));
        
        if (restaurant.getFeaturedDishes() != null && !restaurant.getFeaturedDishes().isEmpty()) {
            restaurant.getFeaturedDishes().forEach(dish -> {
                JLabel dishLabel = new JLabel("• " + dish);
                dishLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                dishLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                dishesPanel.add(dishLabel);
            });
        } else {
            JLabel noDishes = new JLabel("No hay información de platillos destacados");
            noDishes.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            dishesPanel.add(noDishes);
        }
        contentPanel.add(dishesPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void addContactInfo(JPanel contentPanel, Restaurant restaurant) {
        JPanel contactPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        contactPanel.setBorder(BorderFactory.createTitledBorder("Información de Contacto"));
        contactPanel.setBackground(new Color(255, 255, 255, 180));
        
        addContactInfoRow(contactPanel, "📞 Teléfono:", restaurant.getPhone());
        addContactInfoRow(contactPanel, "🏠 Dirección:", restaurant.getAddress());
        addContactInfoRow(contactPanel, "🌐 Sitio web:", restaurant.getWebsite());
        
        contentPanel.add(contactPanel);
    }

    private void addContactInfoRow(JPanel panel, String label, String value) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setOpaque(false);
        
        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JLabel valueLbl = new JLabel(value != null ? value : "No disponible");
        valueLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        rowPanel.add(labelLbl, BorderLayout.WEST);
        rowPanel.add(valueLbl, BorderLayout.CENTER);
        panel.add(rowPanel);
    }
}