FROM tomcat:8.0.43-jre8
ADD ./demopipeline/target/SpringSecurity-1.war /usr/local/tomcat/webapps/
COPY ./server.xml $CATALINA_HOME/conf/
COPY ./context.xml $CATALINA_HOME/conf/
# MySQL driver jar
# RUN wget http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.36/mysql-connector-java-5.1.36.jar $CATALINA_HOME/lib/
ADD ./mysql-connector-java-5.1.36.jar $CATALINA_HOME/lib/
EXPOSE 9095
CMD chmod +x /usr/local/tomcat/bin/catalina.sh
CMD ["catalina.sh", "run"]
