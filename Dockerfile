# Dockerfile

# jdk17 base image
FROM openjdk:17

# 인자 설정 - JAR_FILE
ARG JAR_FILE=build/libs/*.jar

# jar 파일 복제
COPY ${JAR_FILE} jnuwiki.jar

# 실행
ENTRYPOINT ["java", "-jar", "jnuwiki.jar"]
