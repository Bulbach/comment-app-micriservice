#FROM openjdk:17-alpine
#
#COPY . /app
#WORKDIR /app
#
##RUN ./gradlew build
#
#EXPOSE 8082
#WORKDIR /app
#
#CMD java -jar ./build/libs/comment-app-micriservice-0.0.1-SNAPSHOT.jar

# Используем базовый образ с JRE
FROM openjdk:17-alpine

# Копируем JAR-файл в рабочую директорию
COPY build/libs/comment-app-micriservice-0.0.1-SNAPSHOT.jar /app.jar

# Экспорт порта, если приложение должно быть доступно через сеть
EXPOSE 8082

# Команда для запуска приложения
ENTRYPOINT ["java","-jar","/app.jar"]