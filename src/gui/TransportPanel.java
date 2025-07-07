/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Destination;
import model.Transport;
import service.TransportService;
import service.HistoryService;

public class TransportPanel extends JPanel {
    private final MainFrame mainFrame;
    private final TransportService transportService;
    private final HistoryService historyService;
    private final JComboBox<Destination> destinationCombo;
    private final JPanel resultsPanel;

    public TransportPanel(MainFrame mainFrame, TransportService transportService, HistoryService historyService) {
        this.mainFrame = mainFrame;
        this.transportService = transportService;
        this.historyService = historyService;
        
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Opciones de Transporte Seguro", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
        // Panel de selección de destino
        JPanel destinationPanel = new JPanel(new FlowLayout());
        JLabel destinationLabel = new JLabel("Seleccione su destino:");
        
        // Obtener destinos del servicio
        List<Destination> destinations = transportService.getDestinations();
        destinationCombo = new JComboBox<>(destinations.toArray(new Destination[0]));
        
        destinationPanel.add(destinationLabel);
        destinationPanel.add(destinationCombo);
        
        // Botón de búsqueda
        JButton searchButton = new JButton("Buscar Transporte");
        searchButton.addActionListener(e -> searchTransport());
        
        // Panel de resultados
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(destinationPanel, BorderLayout.CENTER);
        mainPanel.add(searchButton, BorderLayout.SOUTH);
        
        // Panel con scroll para los resultados
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        
        // Organizar los paneles
        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void searchTransport() {
        Destination selectedDestination = (Destination) destinationCombo.getSelectedItem();
        
        // Limpiar resultados anteriores
        resultsPanel.removeAll();
        
        // Obtener opciones de transporte
        List<Transport> transports = transportService.getTransportsByDestination(selectedDestination);
        
        if (transports.isEmpty()) {
            resultsPanel.add(new JLabel("No se encontraron opciones de transporte para este destino."));
        } else {
            for (Transport transport : transports) {
                addTransportCard(transport);
            }
        }
        
        // Registrar en el historial
        historyService.addSearch("Transporte", "Destino: " + selectedDestination.getName());
        
        // Actualizar la interfaz
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private void addTransportCard(Transport transport) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel typeLabel = new JLabel(transport.getType());
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(new JLabel("Destino: " + transport.getDestination().getName()));
        infoPanel.add(new JLabel("Tiempo estimado: " + transport.getEstimatedTime()));
        infoPanel.add(new JLabel("Costo estimado: $" + String.format("%,.0f", transport.getEstimatedCost())));
        
        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionPanel.add(new JLabel("Instrucciones: " + transport.getInstructions()));
        
        card.add(typeLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(instructionPanel, BorderLayout.SOUTH);
        
        resultsPanel.add(card);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}