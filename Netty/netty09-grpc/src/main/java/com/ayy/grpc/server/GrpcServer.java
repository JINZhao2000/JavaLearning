package com.ayy.grpc.server;

import com.ayy.grpc.proto.StudentServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/05/2021
 * @ Version 1.0
 */

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        GrpcServer server = new GrpcServer();
        server.start();
        server.await();
    }

    private Server server;

    private void start() throws IOException {
        int port = 10000;
        this.server = ServerBuilder.forPort(port).addService(new StudentServiceImpl()).build().start();
        System.out.println("Server started");
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Shutdown");
            GrpcServer.this.stop();
        }));
        System.out.println("And then");
    }

    private void stop(){
        if(null != this.server){
            this.server.shutdown();
        }
    }

    private void await() throws InterruptedException {
        if(null != this.server){
            this.server.awaitTermination();
        }
    }
}
