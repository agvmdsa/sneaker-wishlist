FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
RUN mkdir -p /root/.m2 && printf '<toolchains><toolchain><type>jdk</type><provides><version>21</version></provides><configuration><jdkHome>%s</jdkHome></configuration></toolchain></toolchains>' "$JAVA_HOME" > /root/.m2/toolchains.xml
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
