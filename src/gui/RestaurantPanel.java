package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder; // Importación añadida
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.List;
import model.Restaurant;
import service.RestaurantService;
import service.HistoryService;

public class RestaurantPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final MainFrame mainFrame;
    private final RestaurantService restaurantService;
    private final HistoryService historyService;
    
    // Inicialización directa de los combobox y panel de resultados
    private final JComboBox<String> cityCombo = new JComboBox<>();
    private final JComboBox<String> typeCombo = new JComboBox<>();
    private final JComboBox<String> priceCombo = new JComboBox<>();
    private final JPanel resultsPanel = new JPanel();

    // Paleta de colores
    private static final Color DARK_RED = new Color(140, 20, 20);
    private static final Color MEDIUM_RED = new Color(180, 30, 30);
    private static final Color LIGHT_RED = new Color(220, 50, 50);
    private static final Color WHITE = Color.WHITE;
    private static final Color DARK_TEXT = new Color(60, 60, 60);

    public RestaurantPanel(MainFrame mainFrame, RestaurantService restaurantService, HistoryService historyService) {
        this.mainFrame = mainFrame;
        this.restaurantService = restaurantService;
        this.historyService = historyService;
        
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        // Panel de título
        JPanel titlePanel = createTitlePanel();
        
        // Panel de filtros
        JPanel filterPanel = createFilterPanel();
        
        // Botón de búsqueda
        JPanel buttonPanel = createSearchButtonPanel();
        
        // Configurar panel de resultados
        setupResultsPanel();
        
        // Panel principal
        JPanel mainPanel = createMainPanel(titlePanel, filterPanel, buttonPanel);
        
        // Panel con scroll para los resultados
        JScrollPane scrollPane = createScrollPane();
        
        // Organizar los paneles
        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupResultsPanel() {
        resultsPanel.setOpaque(false);
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Recomendaciones de Restaurantes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(3, 1, 10, 5));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Configurar combobox para ciudad
        JPanel cityPanel = createFilterPanel("Ciudad:", 
            new String[]{"Todas", "Bogotá", "Medellín", "Cartagena", "Cali", "Santa Marta", "Chía"});
        cityCombo.setModel(((JComboBox<String>) cityPanel.getComponent(1)).getModel());
        
        // Configurar combobox para tipo de comida
        JPanel typePanel = createFilterPanel("Tipo de comida:", 
            new String[]{"Todos", "Tradicional", "Vegetariana", "Internacional", "Gourmet", "Mariscos"});
        typeCombo.setModel(((JComboBox<String>) typePanel.getComponent(1)).getModel());
        
        // Configurar combobox para rango de precios
        JPanel pricePanel = createFilterPanel("Rango de precios:", 
            new String[]{"Todos", "Bajo", "Medio", "Alto"});
        priceCombo.setModel(((JComboBox<String>) pricePanel.getComponent(1)).getModel());
        
        filterPanel.add(cityPanel);
        filterPanel.add(typePanel);
        filterPanel.add(pricePanel);
        
        return filterPanel;
    }

    private JPanel createSearchButtonPanel() {
        JButton searchButton = createMainButton("Buscar Restaurantes");
        searchButton.addActionListener(e -> searchRestaurants());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(searchButton);
        return buttonPanel;
    }

    private JPanel createMainPanel(JPanel titlePanel, JPanel filterPanel, JPanel buttonPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    private JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }
    
    private JPanel createFilterPanel(String labelText, String[] options) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(WHITE);
        
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(WHITE);
        combo.setMaximumRowCount(6);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(combo, BorderLayout.CENTER);
        
        return panel;
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
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(LIGHT_RED);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(MEDIUM_RED);
            }
        });
        return button;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Degradado rojo
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(100, 10, 10),
            0, getHeight(), new Color(40, 0, 0)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Montañas
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawMountain(g2d, 0, getHeight(), 
            getWidth() * 0.2, getHeight(), 
            getWidth() * 0.25, getHeight() * 0.4, 
            getWidth() * 0.4, getHeight());

        drawMountain(g2d, getWidth() * 0.6, getHeight(), 
            getWidth() * 0.7, getHeight() * 0.6, 
            getWidth() * 0.8, getHeight() * 0.5, 
            getWidth(), getHeight());

        drawMountain(g2d, getWidth() * 0.3, getHeight(), 
            getWidth() * 0.5, getHeight() * 0.3, 
            getWidth() * 0.7, getHeight() * 0.4, 
            getWidth() * 0.8, getHeight());
    }

    private void drawMountain(Graphics2D g2d, double x1, double y1, 
                            double ctrlX1, double ctrlY1, 
                            double ctrlX2, double ctrlY2, 
                            double x2, double y2) {
        Path2D mountain = new Path2D.Double();
        mountain.moveTo(x1, y1);
        mountain.curveTo(ctrlX1, ctrlY1, ctrlX2, ctrlY2, x2, y2);
        mountain.lineTo(x1, y1);
        g2d.fill(mountain);
    }
    
    private void searchRestaurants() {
        String city = cityCombo.getSelectedItem().toString();
        String type = typeCombo.getSelectedItem().toString();
        String price = priceCombo.getSelectedItem().toString();
        
        resultsPanel.removeAll();
        
        List<Restaurant> filteredRestaurants = restaurantService.getRestaurantsByFilters(city, type, price);
        
        if (filteredRestaurants.isEmpty()) {
            showNoResultsMessage();
        } else {
            filteredRestaurants.forEach(this::addRestaurantCard);
        }
        
        historyService.addSearch("Restaurantes", 
            String.format("Ciudad: %s, Tipo: %s, Precio: %s", city, type, price));
        
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void showNoResultsMessage() {
        JLabel noResults = new JLabel("No se encontraron restaurantes con los filtros seleccionados.");
        noResults.setForeground(WHITE);
        noResults.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(noResults);
    }
    
    private void addRestaurantCard(Restaurant restaurant) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 255, 255, 220));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(DARK_RED, 2),
            new EmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RestaurantDetailsDialog(mainFrame, restaurant);
            }
        });
        
        JLabel nameLabel = new JLabel(restaurant.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(DARK_TEXT);
        
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(createInfoLabel("Tipo: " + restaurant.getType()));
        infoPanel.add(createInfoLabel("Precio: " + restaurant.getPriceRange()));
        infoPanel.add(createInfoLabel("Ubicación: " + restaurant.getDestination().getCity()));
        
        JPanel ratingPanel = createRatingPanel(restaurant.getRating());
        
        card.add(nameLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(ratingPanel, BorderLayout.SOUTH);
        
        resultsPanel.add(card);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private JPanel createRatingPanel(double rating) {
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ratingPanel.setOpaque(false);
        ratingPanel.add(createInfoLabel("Calificación: "));
        
        int fullStars = (int) rating;
        for (int i = 0; i < 5; i++) {
            JLabel star = new JLabel(i < fullStars ? "★" : "☆");
            star.setForeground(i < fullStars ? Color.YELLOW : DARK_TEXT);
            ratingPanel.add(star);
        }
        
        return ratingPanel;
    }
    
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(DARK_TEXT);
        return label;
    }
}