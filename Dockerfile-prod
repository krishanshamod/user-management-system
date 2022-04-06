FROM openjdk:19-jdk-alpine3.15
EXPOSE 8090:8090
COPY ./target/user-management-system.jar /opt/ums/user-management-system.jar
WORKDIR /opt/ums
ENTRYPOINT ["java","-jar","user-management-system.jar"]
