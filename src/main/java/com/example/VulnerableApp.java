package com.example;


import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.InputStream;

public class VulnerableApp {

    public static void main(String[] args) {

        String fileName = "payload.yaml";
        System.out.println("[+] Parseando el fichero YAML: " + fileName);

        try (InputStream inputStream = new FileInputStream(fileName)) {
            Yaml yaml = new Yaml();
            
            // --- ¡LA LLAMADA VULNERABLE A LA DEPENDENCIA! ---
            // El método `load()` en esta versión de SnakeYAML no previene las
            // anclas recursivas. Al intentar construir el objeto en memoria,
            // entrará en una recursión infinita que causará un StackOverflowError.
            Object data = yaml.load(inputStream);

            System.out.println("[+] El fichero YAML fue parseado con éxito (esto no debería ocurrir).");
            // La línea de abajo nunca se ejecutará, porque el `toString()` implícito
            // del objeto recursivo es lo que dispara el StackOverflowError.
            System.out.println(data.toString());

        } catch (Exception e) {
            System.err.println("[!] Ocurrió un error al parsear el YAML.");
            e.printStackTrace();
        }
    }
}