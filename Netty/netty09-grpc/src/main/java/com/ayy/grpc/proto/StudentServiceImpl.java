package com.ayy.grpc.proto;

import io.grpc.stub.StreamObserver;

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
}
