/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import service.HistoryService;

public class HistoryPanel extends JPanel {
    private final MainFrame mainFrame;
    private final HistoryService historyService;

    public HistoryPanel(MainFrame mainFrame, HistoryService historyService) {
        this.mainFrame = mainFrame;
        this.historyService = historyService;
        
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Historial de Búsquedas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
        // Panel de historial
        JPanel historyEntriesPanel = new JPanel();
        historyEntriesPanel.setLayout(new BoxLayout(historyEntriesPanel, BoxLayout.Y_AXIS));
        historyEntriesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Obtener y mostrar historial
        List<String> history = historyService.getSearchHistory();
        if (history.isEmpty()) {
            historyEntriesPanel.add(new JLabel("No hay búsquedas recientes."));
        } else {
            for (String entry : history) {
                addHistoryEntry(historyEntriesPanel, entry);
            }
        }
        
        // Panel con scroll para el historial
        JScrollPane scrollPane = new JScrollPane(historyEntriesPanel);
        
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Volver al Menú Principal");
        JButton clearButton = new JButton("Limpiar Historial");
        
        backButton.addActionListener(e -> mainFrame.showPanel("RESTAURANT"));
        clearButton.addActionListener(e -> {
            historyService.clearHistory();
            mainFrame.showPanel("HISTORY"); // Recargar el panel
        });
        
        buttonPanel.add(backButton);
        buttonPanel.add(clearButton);
        
        // Organizar los paneles
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addHistoryEntry(JPanel panel, String entry) {
        JPanel entryPanel = new JPanel(new BorderLayout());
        entryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Separar el tipo de búsqueda de los detalles
        String[] parts = entry.split(": ", 2);
        String searchType = parts[0];
        String details = parts.length > 1 ? parts[1] : "";
        
        JLabel typeLabel = new JLabel(searchType);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel descLabel = new JLabel(details);
        
        // Fecha simulada
        JLabel dateLabel = new JLabel("Hoy", SwingConstants.RIGHT);
        dateLabel.setForeground(Color.GRAY);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(descLabel, BorderLayout.CENTER);
        infoPanel.add(dateLabel, BorderLayout.EAST);
        
        entryPanel.add(typeLabel, BorderLayout.NORTH);
        entryPanel.add(infoPanel, BorderLayout.CENTER);
        
        panel.add(entryPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}