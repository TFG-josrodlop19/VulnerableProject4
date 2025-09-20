package com.example;

import org.yaml.snakeyaml.Yaml;

public class VulnerableApp {

    /**
     * Este es el método que el fuzzer debe atacar.
     * Acepta cualquier string y es vulnerable.
     * CRÍTICO: No tiene un bloque try-catch que oculte el error.
     */
    public static void processYaml(String yamlContent) {
        Yaml yaml = new Yaml();
        Object data = yaml.load(yamlContent);
        // Si el yamlContent es recursivo, la siguiente línea causará un StackOverflowError
        // que NO será capturado aquí, y por lo tanto, será visible para el fuzzer.
        data.hashCode();
    }

    /**
     * El método main se puede quedar como estaba, para tus pruebas manuales (PoC).
     * No será usado por el fuzzer.
     */
    public static void main(String[] args) {
        String recursiveYamlPayload = "&a [ \"lol\", *a ]";
        try {
            processYaml(recursiveYamlPayload);
        } catch (Throwable t) {
            System.err.println("\n[!] La PoC manual detonó la vulnerabilidad:");
            t.printStackTrace();
        }
    }
}