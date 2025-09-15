package com.example;


import org.dom4j.io.SAXReader;

public class VulnerableApp {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java -jar <jar_file> <fichero.xml>");
            return;
        }

        String fileName = args[0];
        System.out.println("[+] Parseando el fichero XML: " + fileName);

        try {
            SAXReader reader = new SAXReader();
            
            // --- ¡LA LLAMADA VULNERABLE A LA DEPENDENCIA! ---
            // En esta versión de dom4j, el SAXReader por defecto no tiene protecciones
            // contra la expansión de entidades. Al llamar a .read() sobre un XML
            // malicioso, se disparará el consumo de recursos.
            reader.read(fileName);

            System.out.println("[+] El fichero XML fue parseado con éxito (esto no debería ocurrir con el payload).");

        } catch (Exception e) {
            System.err.println("[!] Ocurrió un error al parsear el XML.");
            // La aplicación probablemente crasheará con un OutOfMemoryError antes de llegar aquí.
            e.printStackTrace();
        }
    }
}