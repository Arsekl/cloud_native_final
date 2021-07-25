FROM nat-harbor.daocloud.io/library/openjdk:8u232-jre-debian
ADD ./target/cloud_native_final-1.0-SNAPSHOT.jar /app/cloud_native_final-1.0-SNAPSHOT.jar
ADD runboot.sh /app/
WORKDIR /app
RUN chmod a+x runboot.sh
EXPOSE 8088
CMD /app/runboot.sh