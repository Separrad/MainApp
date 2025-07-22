/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.List;

public class HistoryService {
    private final List<String> searchHistory;
    
    public HistoryService() {
        searchHistory = new ArrayList<>();
    }
    
    public void addSearch(String searchType, String query) {
        String entry = searchType + ": " + query;
        searchHistory.add(0, entry); // Agregar al inicio
        
        // Limitar el historial a 50 entradas
        if (searchHistory.size() > 50) {
            searchHistory.remove(searchHistory.size() - 1);
        }
    }
    
    public List<String> getSearchHistory() {
        return new ArrayList<>(searchHistory);
    }
    
    public void clearHistory() {
        searchHistory.clear();
    }
}