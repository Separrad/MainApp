/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import model.*;
import service.*;
import java.util.List;
import java.awt.geom.Path2D;
import static model.TransportType.BIKE;
import static model.TransportType.BUS;
import static model.TransportType.FLIGHT;
import static model.TransportType.TAXI;
import static model.TransportType.WALK;

public class TransportPanel extends JPanel {
    private final MainFrame mainFrame;
    private final TransportService transportService;
    private final HistoryService historyService;
    private final JComboBox<Destination> destinationCombo;
    private final JPanel resultsPanel;
    private JButton useCurrentLocationBtn;
    private JComboBox<String> originCityCombo;
    
    // Paleta de colores
    private final Color DARK_RED = new Color(140, 20, 20);
    private final Color MEDIUM_RED = new Color(180, 30, 30);
    private final Color LIGHT_RED = new Color(220, 50, 50);
    private final Color WHITE = Color.WHITE;
    private final Color LIGHT_GRAY = new Color(240, 240, 240);
    private final Color DARK_TEXT = new Color(60, 60, 60);

    public TransportPanel(MainFrame mainFrame, TransportService transportService, HistoryService historyService) {
        this.mainFrame = mainFrame;
        this.transportService = transportService;
        this.historyService = historyService;
        
        setLayout(new GridBagLayout());
        setBackground(DARK_RED);
        
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setOpaque(false);
        mainContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JLabel appTitle = new JLabel("TRANSPORTE SEGURO", SwingConstants.CENTER);
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
        
        JLabel mottoLabel = new JLabel("Llega seguro a tu destino", SwingConstants.CENTER);
        mottoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        mottoLabel.setForeground(new Color(255, 255, 255, 200));
        mottoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(appTitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(mottoLabel);
        
        // Panel del formulario
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(WHITE);
        formCard.setBorder(new CompoundBorder(
            new LineBorder(DARK_RED, 2),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        // Combo para ciudad de origen
        originCityCombo = new JComboBox<>();
        originCityCombo.addItem("Bogotá");
        originCityCombo.addItem("Medellín");
        originCityCombo.addItem("Cali");
        originCityCombo.addItem("Cartagena");
        originCityCombo.addItem("Santa Marta");
        styleField(originCityCombo);
        
        // Botón de ubicación actual
        useCurrentLocationBtn = createSecondaryButton("Usar mi ubicación actual");
        useCurrentLocationBtn.addActionListener(e -> handleCurrentLocation());
        
        // Combo para destino
        destinationCombo = new JComboBox<>(transportService.getDestinations().toArray(new Destination[0]));
        styleField(destinationCombo);
        
        // Organizar componentes
        gbc.gridy = 0;
        inputPanel.add(createLabeledField("Ciudad de origen:", originCityCombo), gbc);
        
        gbc.gridy = 1;
        inputPanel.add(useCurrentLocationBtn, gbc);
        
        gbc.gridy = 2;
        inputPanel.add(createLabeledField("Destino:", destinationCombo), gbc);
        
        // Botón de búsqueda
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton searchButton = createMainButton("BUSCAR TRANSPORTE");
        searchButton.addActionListener(e -> searchTransport());
        inputPanel.add(searchButton, gbc);
        
        formCard.add(inputPanel, BorderLayout.CENTER);
        
        // Panel de resultados
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(WHITE);
        resultsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(WHITE);
        
        JPanel resultsCard = new JPanel(new BorderLayout());
        resultsCard.setBackground(WHITE);
        resultsCard.setBorder(new CompoundBorder(
            new LineBorder(DARK_RED, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        resultsCard.add(scrollPane, BorderLayout.CENTER);
        
        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(formCard, BorderLayout.CENTER);
        mainContainer.add(resultsCard, BorderLayout.SOUTH);
        
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = mainGbc.gridy = 0;
        mainGbc.weightx = mainGbc.weighty = 1;
        mainGbc.fill = GridBagConstraints.BOTH;
        add(mainContainer, mainGbc);
    }

    private void handleCurrentLocation() {
        JOptionPane.showMessageDialog(this, 
            "Obteniendo ubicación... (Simulación: usando Bogotá como ubicación actual)", 
            "Ubicación", 
            JOptionPane.INFORMATION_MESSAGE);
        
        originCityCombo.setSelectedItem("Bogotá");
        useCurrentLocationBtn.setText("Ubicación actual: Bogotá");
        useCurrentLocationBtn.setEnabled(false);
    }

    private void searchTransport() {
        String originCity = (String) originCityCombo.getSelectedItem();
        Destination destination = (Destination) destinationCombo.getSelectedItem();
        
        Location userLocation = findLocationByCity(originCity);
        
        resultsPanel.removeAll();
        
        List<TransportOption> transports = transportService.getTransportOptions(userLocation, destination);
        
        if (transports.isEmpty()) {
            JLabel noResults = new JLabel("No se encontraron opciones de transporte.", SwingConstants.CENTER);
            noResults.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noResults.setForeground(DARK_TEXT);
            resultsPanel.add(noResults);
        } else {
            transports.forEach(this::addTransportCard);
            historyService.addSearch("Transporte", "De " + originCity + " a " + destination.getCity());
        }
        
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private Location findLocationByCity(String city) {
        switch(city) {
            case "Bogotá": return new Location(4.711, -74.072, "Bogotá");
            case "Medellín": return new Location(6.244, -75.581, "Medellín");
            case "Cali": return new Location(3.420, -76.520, "Cali");
            case "Cartagena": return new Location(10.393, -75.483, "Cartagena");
            case "Santa Marta": return new Location(11.240, -74.199, "Santa Marta");
            case "San Andrés": return new Location(12.583, -81.706, "San Andrés");
            default: return new Location(4.711, -74.072, "Bogotá");
        }
    }
    
    private void addTransportCard(TransportOption transport) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 1, 1, 1, LIGHT_GRAY),
            new EmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        // Icono textual
        JLabel iconLabel = new JLabel(getTransportIconText(transport.getType()));
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        iconLabel.setForeground(MEDIUM_RED);
        
        // Información principal
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBackground(WHITE);
        
        JLabel priceLabel = new JLabel("Precio: $" + String.format("%,.0f", transport.getCost()) + " COP");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceLabel.setForeground(DARK_TEXT);
        
        JLabel timeLabel = new JLabel("Tiempo: " + transport.getEstimatedTime());
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        timeLabel.setForeground(DARK_TEXT);
        
        infoPanel.add(priceLabel);
        infoPanel.add(timeLabel);
        
        // Detalles
        JTextArea details = new JTextArea(transport.getInstructions());
        details.setEditable(false);
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        details.setBackground(WHITE);
        details.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        details.setForeground(DARK_TEXT);
        
        card.add(iconLabel, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(details, BorderLayout.SOUTH);
        
        resultsPanel.add(card);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
    private String getTransportIconText(TransportType type) {
        switch(type) {
            case FLIGHT: return "✈";
            case BUS: return "🚌";
            case TAXI: return "🚖";
            case BIKE: return "🚲";
            case WALK: return "🚶";
            default: return "➔";
        }
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
    
    private void styleField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_GRAY),
            new EmptyBorder(8, 5, 8, 5)
        ));
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Degradado rojo (oscuro arriba → muy oscuro abajo)
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(100, 10, 10),      // Top: DARK_RED
            0, getHeight(), new Color(40, 0, 0) // Bottom: Casi negro
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Montañas (blanco semitransparente)
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
}