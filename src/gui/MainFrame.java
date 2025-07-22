package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    // Paleta de colores mejorada
    private final Color DARK_RED = new Color(100, 10, 10);
    private final Color MEDIUM_RED = new Color(140, 20, 20);
    private final Color LIGHT_RED = new Color(180, 30, 30);
    private final Color WHITE = Color.WHITE;
    private final Color LIGHT_GRAY = new Color(240, 240, 240);
    private final Color DARK_TEXT = new Color(60, 60, 60);
    private final Color MENU_TEXT = Color.BLACK;

    public MainFrame() {
        configureLookAndFeel();
        
        // Configuración básica de la ventana
        setTitle("Colombia Wander - Descubre cada paso, vive cada aventura");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(WHITE);
        
        // Inicializar servicios con datos de ejemplo
        List<Destination> destinations = createSampleDestinations();
        this.authService = new AuthService();
        this.restaurantService = new RestaurantService(destinations);
        this.transportService = new TransportService(destinations);
        this.historyService = new HistoryService();
        
        // Configurar el sistema de paneles
        configureCardLayout();
        
        // Configurar barra de menú con estilos
        setJMenuBar(createStyledMenuBar());
        
        add(cardPanel);
        showPanel("LOGIN");
    }
    
    private void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Configuración de estilos globales
            UIManager.put("Panel.background", WHITE);
            UIManager.put("OptionPane.background", WHITE);
            UIManager.put("OptionPane.messageForeground", DARK_TEXT);
            UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 12));
            UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 12));
            
            // Estilos para menú
            UIManager.put("MenuBar.background", WHITE);
            UIManager.put("MenuBar.border", BorderFactory.createMatteBorder(0, 0, 1, 0, LIGHT_GRAY));
            UIManager.put("Menu.background", WHITE);
            UIManager.put("Menu.foreground", MENU_TEXT);
            UIManager.put("Menu.selectionBackground", LIGHT_GRAY);
            UIManager.put("MenuItem.background", WHITE);
            UIManager.put("MenuItem.foreground", MENU_TEXT);
            UIManager.put("MenuItem.selectionBackground", LIGHT_GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void configureCardLayout() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(WHITE);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
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
    }
    
    private JMenuBar createStyledMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(WHITE);
        menuBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Menú Archivo
        JMenu fileMenu = createStyledMenu("Archivo");
        JMenuItem exitItem = createStyledMenuItem("Salir");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        // Menú Navegación
        navigationMenu = createStyledMenu("Navegación");
        JMenuItem loginItem = createStyledMenuItem("Inicio de sesión");
        JMenuItem restaurantItem = createStyledMenuItem("Restaurantes");
        JMenuItem transportItem = createStyledMenuItem("Transporte");
        JMenuItem historyItem = createStyledMenuItem("Historial");
        
        // Configurar acciones
        loginItem.addActionListener(e -> showPanel("LOGIN"));
        restaurantItem.addActionListener(e -> checkAuthAndShowPanel("RESTAURANT"));
        transportItem.addActionListener(e -> checkAuthAndShowPanel("TRANSPORT"));
        historyItem.addActionListener(e -> checkAuthAndShowPanel("HISTORY"));
        
        navigationMenu.add(loginItem);
        navigationMenu.add(restaurantItem);
        navigationMenu.add(transportItem);
        navigationMenu.add(historyItem);
        
        setNavigationItemsEnabled(false);
        
        menuBar.add(fileMenu);
        menuBar.add(navigationMenu);
        
        return menuBar;
    }
    
    private JMenu createStyledMenu(String title) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 13));
        menu.setForeground(MENU_TEXT);
        menu.setBackground(WHITE);
        menu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Efecto hover
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menu.setForeground(DARK_RED);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menu.setForeground(MENU_TEXT);
            }
        });
        
        return menu;
    }
    
    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        item.setForeground(MENU_TEXT);
        item.setBackground(WHITE);
        item.setBorderPainted(false);
        
        // Efecto hover
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setForeground(DARK_RED);
                item.setBackground(LIGHT_GRAY);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                item.setForeground(MENU_TEXT);
                item.setBackground(WHITE);
            }
        });
        
        return item;
    }
    
    private void checkAuthAndShowPanel(String panelName) {
        if (!isAuthenticated) {
            showAccessDeniedMessage();
        } else {
            showPanel(panelName);
        }
    }
    
    private void showAccessDeniedMessage() {
        JOptionPane.showMessageDialog(this, 
            "Debe iniciar sesión primero", 
            "Acceso denegado", 
            JOptionPane.WARNING_MESSAGE);
    }
    
    public void showPanel(String panelName) {
        if (!isAuthenticated && !panelName.equals("LOGIN")) {
            showAccessDeniedMessage();
            return;
        }
        
        cardLayout.show(cardPanel, panelName);
        
        // Actualizar el panel de historial si es necesario
        if (panelName.equals("HISTORY")) {
            cardPanel.remove(historyPanel);
            historyPanel = new HistoryPanel(this, historyService);
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
            new Destination("Catedral de Sal", "Zipaquirá", "Iglesia subterránea en mina de sal", 4.5, 5.028, -74.006),
            new Destination("Andrés Carne de Res", "Chía", "Famoso restaurante colombiano", 4.6, 4.861, -74.069),
            new Destination("Museo del Oro", "Bogotá", "Colección de orfebrería prehispánica", 4.7, 4.711, -74.072),
            new Destination("Parque Tayrona", "Santa Marta", "Parque natural con playas", 4.3, 11.311, -74.077),
            new Destination("Islas del Rosario", "Cartagena", "Archipiélago con arrecifes de coral", 4.2, 10.176, -75.751),
            new Destination("Comuna 13", "Medellín", "Barrio con grafitis y escaleras eléctricas", 4.0, 6.248, -75.574),
            new Destination("Zoológico de Cali", "Cali", "Zoológico con diversidad de especies", 4.1, 3.420, -76.520)
        );
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}