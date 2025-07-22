package service;

import model.Restaurant;
import model.Destination;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantService {
    private AVLTree avlTree;
    private final List<Destination> destinations;
    
    public RestaurantService(List<Destination> destinations) {
        this.destinations = destinations;
        this.avlTree = new AVLTree();
        initializeSampleData();
    }
    
    // Clase interna para el Árbol AVL
    private static class AVLTree {
        private Node root;
        
        private class Node {
            Restaurant restaurant;
            Node left;
            Node right;
            int height;
            
            Node(Restaurant restaurant) {
                this.restaurant = restaurant;
                this.height = 1;
            }
        }
        
        // Método para insertar un restaurante
        public void insert(Restaurant restaurant) {
            root = insert(root, restaurant);
        }
        
        private Node insert(Node node, Restaurant restaurant) {
            if (node == null) {
                return new Node(restaurant);
            }
            
            // Comparar por nombre (podría modificarse para otros criterios)
            int cmp = restaurant.getName().compareTo(node.restaurant.getName());
            
            if (cmp < 0) {
                node.left = insert(node.left, restaurant);
            } else if (cmp > 0) {
                node.right = insert(node.right, restaurant);
            } else {
                return node; // No permitimos duplicados
            }
            
            // Actualizar altura
            node.height = 1 + Math.max(height(node.left), height(node.right));
            
            // Balancear el árbol
            return balance(node);
        }
        
        // Métodos para balancear el árbol
        private Node balance(Node node) {
            int balanceFactor = getBalance(node);
            
            // Caso izquierda-izquierda
            if (balanceFactor > 1 && getBalance(node.left) >= 0) {
                return rightRotate(node);
            }
            
            // Caso izquierda-derecha
            if (balanceFactor > 1 && getBalance(node.left) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            
            // Caso derecha-derecha
            if (balanceFactor < -1 && getBalance(node.right) <= 0) {
                return leftRotate(node);
            }
            
            // Caso derecha-izquierda
            if (balanceFactor < -1 && getBalance(node.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            
            return node;
        }
        
        private Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;
            
            x.right = y;
            y.left = T2;
            
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            
            return x;
        }
        
        private Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;
            
            y.left = x;
            x.right = T2;
            
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            
            return y;
        }
        
        private int height(Node node) {
            return node == null ? 0 : node.height;
        }
        
        private int getBalance(Node node) {
            return node == null ? 0 : height(node.left) - height(node.right);
        }
        
        // Método para buscar restaurantes por filtros
        public List<Restaurant> searchByFilters(String city, String type, String priceRange) {
            List<Restaurant> result = new ArrayList<>();
            inOrderTraversal(root, result, city, type, priceRange);
            return result;
        }
        
        private void inOrderTraversal(Node node, List<Restaurant> result, 
                                    String city, String type, String priceRange) {
            if (node != null) {
                inOrderTraversal(node.left, result, city, type, priceRange);
                
                Restaurant r = node.restaurant;
                boolean cityMatch = city.equals("Todas") || r.getDestination().getCity().equalsIgnoreCase(city);
                boolean typeMatch = type.equals("Todos") || r.getType().equalsIgnoreCase(type);
                boolean priceMatch = priceRange.equals("Todos") || r.getPriceRange().equalsIgnoreCase(priceRange);
                
                if (cityMatch && typeMatch && priceMatch) {
                    result.add(r);
                }
                
                inOrderTraversal(node.right, result, city, type, priceRange);
            }
        }
    }
    
    private void initializeSampleData() {
        // Restaurantes para Chía
        avlTree.insert(createRestaurant(
            "Andrés Carne de Res", "Tradicional", "Medio", "Chía", 4.6,
            "/images/andres.jpg",
            "Un ícono de la gastronomía colombiana, famoso por su ambiente festivo y platos tradicionales.",
            Arrays.asList("Bandeja Paisa", "Sancocho Trifásico", "Postre de Natas"),
            "+57 123 456 7890",
            "Calle 3 # 11A-56, Chía",
            "www.andrescarnederes.com"
        ));
        
        avlTree.insert(createRestaurant(
            "Costa Azul", "Mariscos", "Medio", "Chía", 4.2,
            "/images/costa_azul.jpg",
            "Especializado en mariscos frescos con recetas tradicionales de la costa colombiana.",
            Arrays.asList("Ceviche Mixto", "Arroz con Mariscos", "Langosta al Ajillo"),
            "+57 987 654 3210",
            "Carrera 5 # 20-30, Chía",
            "www.costaazul.com"
        ));
        
        // Restaurantes para Bogotá
        avlTree.insert(createRestaurant(
            "El Pesquero", "Mariscos", "Alto", "Bogotá", 4.6,
            "/images/pesquero.jpg",
            "Uno de los mejores restaurantes de mariscos en Bogotá con ingredientes importados.",
            Arrays.asList("Cazuela de Mariscos", "Pargo Rojo Frito", "Camarones al Ajillo"),
            "+57 1 2345678",
            "Calle 85 # 12-45, Bogotá",
            "www.elpesquero.com"
        ));
        
        avlTree.insert(createRestaurant(
            "Leo Cocina y Cava", "Gourmet", "Alto", "Bogotá", 4.8,
            "/images/leo.jpg",
            "Experiencia gastronómica de alta cocina colombiana con énfasis en ingredientes locales.",
            Arrays.asList("Menú Degustación", "Tartar de Res", "Helado de Guanábana"),
            "+57 1 3456789",
            "Carrera 13 # 85-14, Bogotá",
            "www.leococinaycava.com"
        ));
        
        // Restaurantes para Medellín
        avlTree.insert(createRestaurant(
            "Hacienda", "Tradicional", "Medio", "Medellín", 4.3,
            "/images/hacienda.jpg",
            "Ambiente campestre con lo mejor de la cocina antioqueña tradicional.",
            Arrays.asList("Bandeja Paisa", "Fríjoles con Garra", "Mazamorra"),
            "+57 4 5678901",
            "Calle 10 # 40-20, Medellín",
            "www.haciendarestaurante.com"
        ));
        
        avlTree.insert(createRestaurant(
            "El Cielo", "Gourmet", "Alto", "Medellín", 4.8,
            "/images/cielo.jpg",
            "Restaurante vanguardista que ofrece una experiencia sensorial única.",
            Arrays.asList("Menú de 11 Tiempos", "Foie Gras con Mango", "Esfera de Chocolate"),
            "+57 4 6789012",
            "Carrera 40 # 10A-22, Medellín",
            "www.elcielorestaurante.com"
        ));
        
        // Restaurantes para Cartagena
        avlTree.insert(createRestaurant(
            "La Cevichería", "Mariscos", "Medio", "Cartagena", 4.7,
            "/images/cevicheria.jpg",
            "Famoso por sus ceviches innovadores y ambiente caribeño.",
            Arrays.asList("Ceviche de Camarón", "Ceviche Mixto", "Tiradito de Pargo"),
            "+57 5 7890123",
            "Calle 39 # 7-14, Cartagena",
            "www.lacevicheria.com"
        ));
        
        avlTree.insert(createRestaurant(
            "Carmen", "Gourmet", "Alto", "Cartagena", 4.8,
            "/images/carmen.jpg",
            "Fusión de sabores colombianos con técnicas internacionales en el corazón de Cartagena.",
            Arrays.asList("Lobster Thermidor", "Risotto de Cocos", "Mousse de Maracuyá"),
            "+57 5 8901234",
            "Calle 38 # 8-19, Cartagena",
            "www.carmenrestaurante.com"
        ));
        
        // Restaurantes para Cali
        avlTree.insert(createRestaurant(
            "El Zaguan", "Tradicional", "Medio", "Cali", 4.4,
            "/images/zaguan.jpg",
            "Especializado en comida valluna auténtica desde 1985.",
            Arrays.asList("Sancocho de Gallina", "Aborrajados", "Cholado"),
            "+57 2 9012345",
            "Avenida 5N # 16-30, Cali",
            "www.elzaguan.com"
        ));
        
        avlTree.insert(createRestaurant(
            "Ocio", "Gourmet", "Alto", "Cali", 4.7,
            "/images/ocio.jpg",
            "Cocina contemporánea con toques locales en un ambiente moderno.",
            Arrays.asList("Carpaccio de Res", "Raviolis de Chontaduro", "Volcán de Chocolate"),
            "+57 2 0123456",
            "Calle 15N # 9N-20, Cali",
            "www.ociorestaurante.com"
        ));
        
        // Restaurantes para Santa Marta
        avlTree.insert(createRestaurant(
            "Donde Chucho", "Tradicional", "Medio", "Santa Marta", 4.3,
            "/images/chucho.jpg",
            "Especializado en pescados y mariscos con recetas tradicionales de la región.",
            Arrays.asList("Pargo Frito Entero", "Arroz con Coco y Camarón", "Patacones"),
            "+57 5 1234567",
            "Carrera 1 # 22-10, Santa Marta",
            "www.dondechucho.com"
        ));
        
        avlTree.insert(createRestaurant(
            "Burukuka", "Mariscos", "Medio", "Santa Marta", 4.6,
            "/images/burukuka.jpg",
            "Restaurante playero con los mejores mariscos frescos y cócteles tropicales.",
            Arrays.asList("Ceviche de Pescado", "Langosta a la Parrilla", "Cocktail de Camarón"),
            "+57 5 2345678",
            "Playa Blanca, Santa Marta",
            "www.burukuka.com"
        ));
    }
    
    private Restaurant createRestaurant(String name, String type, String priceRange, String city, 
                                      double rating, String imagePath, String description, 
                                      List<String> featuredDishes, String phone, String address, 
                                      String website) {
        return new Restaurant(
            name, 
            type, 
            priceRange, 
            findDestinationByCity(city), 
            rating,
            imagePath,
            description,
            featuredDishes,
            phone,
            address,
            website
        );
    }
    
    private Destination findDestinationByCity(String city) {
        for (Destination d : destinations) {
            if (d.getCity().equalsIgnoreCase(city)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Destino no encontrado: " + city);
    }
    
    public List<Restaurant> getRestaurantsByFilters(String city, String type, String priceRange) {
        return avlTree.searchByFilters(city, type, priceRange);
    }
}
