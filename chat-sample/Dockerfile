FROM openjdk:8

COPY backend/target/chat-sample-dist.tar.gz /root

WORKDIR /root
RUN tar -zxvf chat-sample-dist.tar.gz

WORKDIR /root/chat-sample/bin
RUN chmod +x service.sh
CMD ["/bin/bash", "service.sh", "start"]