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
import java.util.List;
import model.Restaurant;
import service.RestaurantService;
import service.HistoryService;

public class RestaurantPanel extends JPanel {
    private final MainFrame mainFrame;
    private final RestaurantService restaurantService;
    private final HistoryService historyService;
    private final JComboBox<String> cityCombo;
    private final JComboBox<String> typeCombo;
    private final JComboBox<String> priceCombo;
    private final JPanel resultsPanel;

    public RestaurantPanel(MainFrame mainFrame, RestaurantService restaurantService, HistoryService historyService) {
        this.mainFrame = mainFrame;
        this.restaurantService = restaurantService;
        this.historyService = historyService;
        
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Recomendaciones de Restaurantes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        JLabel cityLabel = new JLabel("Ciudad:");
        String[] cities = {"Todas", "Bogotá", "Medellín", "Cartagena", "Cali", "Santa Marta", "Chía"};
        cityCombo = new JComboBox<>(cities);
        
        JLabel typeLabel = new JLabel("Tipo de comida:");
        String[] types = {"Todos", "Tradicional", "Vegetariana", "Internacional", "Gourmet", "Mariscos"};
        typeCombo = new JComboBox<>(types);
        
        JLabel priceLabel = new JLabel("Rango de precios:");
        String[] prices = {"Todos", "Bajo", "Medio", "Alto"};
        priceCombo = new JComboBox<>(prices);
        
        filterPanel.add(cityLabel);
        filterPanel.add(cityCombo);
        filterPanel.add(typeLabel);
        filterPanel.add(typeCombo);
        filterPanel.add(priceLabel);
        filterPanel.add(priceCombo);
        
        // Botón de búsqueda
        JButton searchButton = new JButton("Buscar Restaurantes");
        searchButton.addActionListener(e -> searchRestaurants());
        
        // Panel de resultados
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.CENTER);
        mainPanel.add(searchButton, BorderLayout.SOUTH);
        
        // Panel con scroll para los resultados
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        
        // Organizar los paneles
        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void searchRestaurants() {
        String city = cityCombo.getSelectedItem().toString();
        String type = typeCombo.getSelectedItem().toString();
        String price = priceCombo.getSelectedItem().toString();
        
        // Limpiar resultados anteriores
        resultsPanel.removeAll();
        
        // Obtener restaurantes filtrados
        List<Restaurant> filteredRestaurants = restaurantService.getRestaurantsByFilters(city, type, price);
        
        if (filteredRestaurants.isEmpty()) {
            resultsPanel.add(new JLabel("No se encontraron restaurantes con los filtros seleccionados."));
        } else {
            for (Restaurant restaurant : filteredRestaurants) {
                addRestaurantCard(restaurant);
            }
        }
        
        // Registrar en el historial
        String searchQuery = "Ciudad: " + city + ", Tipo: " + type + ", Precio: " + price;
        historyService.addSearch("Restaurantes", searchQuery);
        
        // Actualizar la interfaz
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private void addRestaurantCard(Restaurant restaurant) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel nameLabel = new JLabel(restaurant.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(new JLabel("Tipo: " + restaurant.getType()));
        infoPanel.add(new JLabel("Precio: " + restaurant.getPriceRange()));
        infoPanel.add(new JLabel("Ubicación: " + restaurant.getDestination().getCity()));
        
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ratingPanel.add(new JLabel("Calificación: "));
        
        // Añadir estrellas de calificación
        int fullStars = (int) restaurant.getRating();
        for (int i = 0; i < fullStars; i++) {
            ratingPanel.add(new JLabel("★"));
        }
        for (int i = fullStars; i < 5; i++) {
            ratingPanel.add(new JLabel("☆"));
        }
        
        card.add(nameLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(ratingPanel, BorderLayout.SOUTH);
        
        resultsPanel.add(card);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}