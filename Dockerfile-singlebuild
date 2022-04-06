FROM maven:3.8.5-ibm-semeru-17-focal as build
COPY . /home/app
WORKDIR /home/app
RUN mvn clean package

FROM openjdk:19-jdk-alpine3.15 as production
EXPOSE 8090:8090
COPY --from=build /home/app/target/user-management-system.jar /opt/ums/user-management-system.jar
WORKDIR /opt/ums
ENTRYPOINT ["java","-jar","user-management-system.jar"]
