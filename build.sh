#!/bin/bash -eu

# --- Paso 1: Compilación del proyecto con Maven ---
echo "Compilando proyecto con Maven..."
mvn -q clean dependency:copy-dependencies -DoutputDirectory=target/dependency-jars

# --- Paso 2: Configuración del classpath ---
echo "Configurando classpath..."
BUILD_CLASSPATH="$JAZZER_API_PATH:$(find "target/dependency-jars" -name "*.jar" | paste -sd: -)"

# --- Paso 3: Compilación de clases principales ---
echo "Compilando clases principales..."
javac -encoding UTF-8 -cp "$BUILD_CLASSPATH" $(find src/main/java/ -name "*.java") -d $OUT

# --- Paso 4: Compilación de fuzzers ---
echo "Compilando fuzzers..."

# Buscar y compilar todos los fuzzers en src/test/java/
find src/test/java/ -name "*Fuzzer.java" | while read -r fuzzer_file; do
    fuzzer_name=$(basename "$fuzzer_file" .java)
    echo "Procesando: $fuzzer_name"
    
    # Intentar compilar el fuzzer
    if javac -encoding UTF-8 -cp "$BUILD_CLASSPATH:$OUT" "$fuzzer_file" -d $OUT 2>/dev/null; then
        echo "✅ $fuzzer_name: COMPILADO"
    else
        echo "❌ $fuzzer_name: FALLÓ AL COMPILAR"
    fi
done

# --- Paso 5: Copia de artefactos ---
cp target/dependency-jars/*.jar $OUT/
cp /usr/local/bin/jazzer_driver $OUT/
cp /usr/local/bin/jazzer_agent_deploy.jar $OUT/

# --- Paso 6: Creación dinámica de lanzadores ---
find "$OUT" -name "*Fuzzer.class" | while read -r fuzzer_class_file; do
    fuzzer_class_name=$(echo "$fuzzer_class_file" | sed "s|^$OUT/||; s|\.class$||; s|/|\.|g")
    fuzzer_script_name=$(basename "$fuzzer_class_file" .class)

    echo "Creando lanzador para: $fuzzer_class_name"

    cat > "$OUT/$fuzzer_script_name" << EOF
#!/bin/bash
set -eu
this_dir=\$(dirname "\$0")
cd "\$this_dir"
JARS=\$(find . -name "*.jar" | paste -sd:)
RUNTIME_CLASSPATH=".:\$JARS"
LD_LIBRARY_PATH="\${JVM_LD_LIBRARY_PATH:-.}" \\
./jazzer_driver \\
  --agent_path=./jazzer_agent_deploy.jar \\
  --cp="\$RUNTIME_CLASSPATH" \\
  --target_class=$fuzzer_class_name \\
  --jvm_args="-Djava.awt.headless=true" \\
  "\$@"
EOF

    chmod +x "$OUT/$fuzzer_script_name"
done

echo "Build completado."