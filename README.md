# VulnerableProject4

This is a sample Java project that contains a known vulnerability for testing Autofuzz. 

## Vulnerability Details

The project includes SnakeYaml version 1.30, which is known to have two relevant vulnerabilities: 
- CVE-2022-1471. This vulnerability allows for arbitrary code execution via deserialization of untrusted data.
- CVE-2022-25857: This vulnerability allows Denial of Service (DoS) attacks by deserializing specially crafted YAML documents caused by not applying any restrictions in recursion depth.
## Purpose
The purpose of this project is to provide a controlled environment for testing and demonstrating the Autofuzz tool's capabilities in identifying and exploiting vulnerabilities in Java applications.