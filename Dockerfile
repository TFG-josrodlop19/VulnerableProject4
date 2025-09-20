# Empieza desde la imagen base de OSS-Fuzz
FROM gcr.io/oss-fuzz-base/base-builder-jvm

# Instala Maven (como ya hacías) y una versión específica de OpenJDK
RUN apt-get update && apt-get install -y maven openjdk-8-jdk

# Configura Java 8 como la versión por defecto dentro del contenedor
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
RUN update-alternatives --set java $JAVA_HOME/bin/java
RUN update-alternatives --set javac $JAVA_HOME/bin/javac

# Copia el resto de tus ficheros del proyecto
COPY . /src/
WORKDIR /src