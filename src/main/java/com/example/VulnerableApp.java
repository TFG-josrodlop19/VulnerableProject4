package com.example;


import org.dom4j.io.SAXReader;

public class VulnerableApp {

    public static void main(String[] args) {
        String fileName = "payload.xml";

        try {
            SAXReader reader = new SAXReader();
           
            reader.read(fileName);

            System.out.println("[+] El fichero XML fue parseado con éxito (esto no debería ocurrir con el payload).");

        } catch (Exception e) {
            System.err.println("[!] Ocurrió un error al parsear el XML.");
            // La aplicación probablemente crasheará con un OutOfMemoryError antes de llegar aquí.
            e.printStackTrace();
        }
    }
}