package com.ayy.grpc.client;

import com.ayy.grpc.proto.MyRequest;
import com.ayy.grpc.proto.MyResponse;
import com.ayy.grpc.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/05/2021
 * @ Version 1.0
 */

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 10000)
                .usePlaintext().build();
        StudentServiceGrpc.StudentServiceBlockingStub stub = StudentServiceGrpc.newBlockingStub(channel);
        MyResponse resp = stub.getRealNameByStudentname(MyRequest.newBuilder().setStudentname("student1").build());
        System.out.println(resp.getRealname());
    }
}
