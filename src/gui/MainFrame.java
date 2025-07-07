package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;
import model.Destination;
import service.AuthService;
import service.RestaurantService;
import service.TransportService;
import service.HistoryService;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private boolean isAuthenticated = false;
    private JMenu navigationMenu;
    
    // Servicios
    private final AuthService authService;
    private final RestaurantService restaurantService;
    private final TransportService transportService;
    private final HistoryService historyService;
    
    // Paneles
    private LoginPanel loginPanel;
    private RestaurantPanel restaurantPanel;
    private TransportPanel transportPanel;
    private HistoryPanel historyPanel;

    public MainFrame() {
        // Configuración básica de la ventana
        setTitle("Colombia Wander - Descubre cada paso, vive cada aventura");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar servicios con datos de ejemplo
        List<Destination> destinations = createSampleDestinations();
        this.authService = new AuthService();
        this.restaurantService = new RestaurantService(destinations);
        this.transportService = new TransportService(destinations);
        this.historyService = new HistoryService();
        
        // Configurar el sistema de paneles
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Crear paneles
        loginPanel = new LoginPanel(this, authService);
        restaurantPanel = new RestaurantPanel(this, restaurantService, historyService);
        transportPanel = new TransportPanel(this, transportService, historyService);
        historyPanel = new HistoryPanel(this, historyService);
        
        // Agregar paneles
        cardPanel.add(loginPanel, "LOGIN");
        cardPanel.add(restaurantPanel, "RESTAURANT");
        cardPanel.add(transportPanel, "TRANSPORT");
        cardPanel.add(historyPanel, "HISTORY");
        
        // Mostrar panel inicial
        cardLayout.show(cardPanel, "LOGIN");
        
        // Configurar barra de menú
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        navigationMenu = new JMenu("Navegación");
        JMenuItem loginItem = new JMenuItem("Inicio de sesión");
        JMenuItem restaurantItem = new JMenuItem("Restaurantes");
        JMenuItem transportItem = new JMenuItem("Transporte");
        JMenuItem historyItem = new JMenuItem("Historial");
        
        loginItem.addActionListener(e -> showPanel("LOGIN"));
        restaurantItem.addActionListener(e -> {
            if (isAuthenticated) showPanel("RESTAURANT");
            else JOptionPane.showMessageDialog(this, "Debe iniciar sesión primero", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        });
        transportItem.addActionListener(e -> {
            if (isAuthenticated) showPanel("TRANSPORT");
            else JOptionPane.showMessageDialog(this, "Debe iniciar sesión primero", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        });
        historyItem.addActionListener(e -> {
            if (isAuthenticated) showPanel("HISTORY");
            else JOptionPane.showMessageDialog(this, "Debe iniciar sesión primero", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        });
        
        navigationMenu.add(loginItem);
        navigationMenu.add(restaurantItem);
        navigationMenu.add(transportItem);
        navigationMenu.add(historyItem);
        
        // Deshabilitar todos los items excepto login inicialmente
        setNavigationItemsEnabled(false);
        
        menuBar.add(fileMenu);
        menuBar.add(navigationMenu);
        
        setJMenuBar(menuBar);
        add(cardPanel);
    }
    
    public void showPanel(String panelName) {
        if (!isAuthenticated && !panelName.equals("LOGIN")) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión primero", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        cardLayout.show(cardPanel, panelName);
        
        if (panelName.equals("HISTORY")) {
            historyPanel = new HistoryPanel(this, historyService);
            cardPanel.remove(3);
            cardPanel.add(historyPanel, "HISTORY");
            cardLayout.show(cardPanel, "HISTORY");
        }
    }
    
    private void setNavigationItemsEnabled(boolean enabled) {
        for (int i = 1; i < navigationMenu.getItemCount(); i++) {
            navigationMenu.getItem(i).setEnabled(enabled);
        }
        isAuthenticated = enabled;
    }
    
    public void onSuccessfulLogin() {
        setNavigationItemsEnabled(true);
        showPanel("RESTAURANT");
    }
    
    private List<Destination> createSampleDestinations() {
    return Arrays.asList(
        new Destination("Catedral de Sal", "Zipaquirá", "Iglesia subterránea en mina de sal", 4.5),
        new Destination("Andrés Carne de Res", "Chía", "Famoso restaurante colombiano", 4.6),
        new Destination("Museo del Oro", "Bogotá", "Colección de orfebrería prehispánica", 4.7),
        new Destination("Parque Tayrona", "Santa Marta", "Parque natural con playas", 4.3),
        new Destination("Islas del Rosario", "Cartagena", "Archipiélago con arrecifes de coral", 4.2),
        new Destination("Comuna 13", "Medellín", "Barrio con grafitis y escaleras eléctricas", 4.0),
        new Destination("Zoológico de Cali", "Cali", "Zoológico con diversidad de especies", 4.1)
        
    );
}
}