package com.ayy.grpc.proto;

import io.grpc.stub.StreamObserver;

import java.util.UUID;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/05/2021
 * @ Version 1.0
 */

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{
    @Override
    public void getRealNameByStudentname(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("parameters form client : "+request.getStudentname());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("realname").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("age : "+request.getAge());

        responseObserver.onNext(StudentResponse.newBuilder().setName("Student1").setAge(20).setCity("City1").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("Student2").setAge(30).setCity("City2").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("Student3").setAge(40).setCity("City3").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("Student4").setAge(50).setCity("City4").build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("On Next : "+value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse response1 = StudentResponse.newBuilder().setName("Student1").setAge(10).setCity("City1").build();
                StudentResponse response2 = StudentResponse.newBuilder().setName("Student2").setAge(12).setCity("City2").build();

                StudentResponseList responseList = StudentResponseList.newBuilder().addStudentResponse(response1).addStudentResponse(response2).build();

                responseObserver.onNext(responseList);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StreamRequest> bidirectionalTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println(value.getRequestInfo());

                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
