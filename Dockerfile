# Этап 1: Сборка приложения
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

# Копируем файлы проекта в контейнер
COPY pom.xml .
COPY src ./src

# Собираем приложение
RUN mvn clean package -DskipTests

# Этап 2: Формирование финального образа
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Копируем сгенерированный JAR-файл из предыдущего этапа
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
