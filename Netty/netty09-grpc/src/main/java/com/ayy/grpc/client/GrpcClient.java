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
        StudentServiceGrpc.StudentServiceStub stub2 = StudentServiceGrpc.newStub(channel);

        MyResponse resp = stub.getRealNameByStudentname(MyRequest.newBuilder().setStudentname("student1").build());
        System.out.println(resp.getRealname());

//        System.out.println("------------------");
//
//        Iterator<StudentResponse> students = stub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
//
//        while (students.hasNext()){
//            StudentResponse response = students.next();
//            System.out.println(response.getName()+"-"+response.getAge()+"-"+response.getCity());
//        }
//
//        System.out.println("------------------");
//
//        StreamObserver<StudentResponseList> observer = new StreamObserver<StudentResponseList>() {
//            @Override
//            public void onNext(StudentResponseList value) {
//                value.getStudentResponseList().forEach((s) -> {
//                    System.out.println(s.getName()+"-"+s.getAge()+"-"+s.getCity());
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("Completed");
//            }
//        };
//
//        StreamObserver<StudentRequest> requestObserver = stub2.getStudentsWrapperByAges(observer);
//
//        requestObserver.onNext(StudentRequest.newBuilder().setAge(10).build());
//        requestObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
//        requestObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
//        requestObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
//        requestObserver.onCompleted();
//
//        StreamObserver<StreamRequest> request = stub2.bidirectionalTalk(new StreamObserver<StreamResponse>() {
//            @Override
//            public void onNext(StreamResponse value) {
//                System.out.println(value.getResponseInfo());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("completed");
//            }
//        });
//
//        request.onNext(StreamRequest.newBuilder().setRequestInfo("Info1").build());
//        request.onNext(StreamRequest.newBuilder().setRequestInfo("Info2").build());
//        request.onNext(StreamRequest.newBuilder().setRequestInfo("Info3").build());
//        request.onNext(StreamRequest.newBuilder().setRequestInfo("Info4").build());
//        request.onCompleted();
//
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
