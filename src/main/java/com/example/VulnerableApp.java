package com.example;

import org.yaml.snakeyaml.Yaml;

public class VulnerableApp {

    public static void processYaml(String yamlContent) {
        Yaml yaml = new Yaml();
        // This start of payload is designed to guide the fuzzer towards the recursive
        // structure
        String yamlStr = "&a [ \"lol\"," + yamlContent + "]";
        // This is the vulnerable part that can lead to a denial of service
        Object data = yaml.load(yamlStr);
        // If we reach this point, the YAML was processed without exploiting the
        // vulnerability
        data.hashCode();
    }

    public static void main(String[] args) {
        // This main method should not be vulnerable as the input used in the vulnerable method is safe
        String payload = "Hello, World!";
        processYaml(payload);
    }
}