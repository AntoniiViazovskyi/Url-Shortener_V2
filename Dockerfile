FROM openjdk:22-jdk-slim as builder

COPY . /software
WORKDIR /software

RUN apt-get update && apt-get install -y wget unzip \
    && wget https://services.gradle.org/distributions/gradle-8.8-rc-2-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-8.8-rc-2-bin.zip \
    && ln -s /opt/gradle/gradle-8.8-rc-2/bin/gradle /usr/bin/gradle \
    && rm -rf /var/lib/apt/lists/* /tmp/gradle-8.8-rc-2-bin.zip

RUN gradle bootJar

FROM openjdk:22-jdk-slim

COPY --from=builder /software/build/libs/*.jar /opt/UrlShortenerApplication.jar

ENTRYPOINT ["java", "-jar", "opt/UrlShortenerApplication.jar"]

