/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

public class ComplexManualHash {
    /**
     * Genera un código hash único a partir de una cadena de entrada.
     * @param input El texto a hashear (email + contraseña en nuestro caso).
     * @return Un código hexadecimal de 8 caracteres.
     */
    public static String generateHash(String input) {
        // Semilla inicial: Un número primo para reducir colisiones.
        int hash = 7; 
        
        // Salt dinámico: Combina un texto fijo con la longitud del input.
        // Ej: Si input = "admin123" (longitud 8), salt = "SALT_UNICO_8".
        String salt = "SALT_UNICO_" + input.length(); 
        
        // Procesar cada carácter del input:
        for (int i = 0; i < input.length(); i++) {
            // 1. Multiplicar hash por 31 (número primo usado en Java para dispersión).
            // 2. Sumar el valor ASCII del carácter actual.
            // 3. Aplicar XOR (^) con un carácter del salt (cíclico usando módulo).
            hash = (hash * 31 + input.charAt(i)) ^ salt.charAt(i % salt.length());
            
            // Rotación de bits: "Revuelve" los bits del hash para mayor aleatoriedad.
            // - hash << 5: Desplaza 5 bits a la izquierda.
            // - hash >>> 27: Desplaza 27 bits a la derecha (rellena con ceros).
            // - |: Combina ambos resultados.
            hash = (hash << 5) | (hash >>> 27);
        }
        
        // Convertir el hash a hexadecimal (ej: "1a3f8b").
        return Integer.toHexString(hash); 
    }
}
