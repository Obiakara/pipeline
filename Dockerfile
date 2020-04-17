FROM tomcat:8.0.51-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
ADD ./target/demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/spring.war
CMD ["catalina.sh", "run"]
EXPOSE 8085