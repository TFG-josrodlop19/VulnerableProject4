package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

/** Fuzzer generated for class: VulnerableApp. Target: method processYaml in line 12. */
public class VulnerableAppProcessyamlFuzzer {

  public static void fuzzerTestOneInput(FuzzedDataProvider dataProvider) {

    // Generate parameters for the target method or constructor
    java.lang.String yamlContent = dataProvider.consumeString(1000);

    // Instance creation

    // Method call
    try {
      VulnerableApp.processYaml(yamlContent);
    } catch (Exception e) {
      // Catch all exceptions to prevent the fuzzer from stopping

    }
  }
}
