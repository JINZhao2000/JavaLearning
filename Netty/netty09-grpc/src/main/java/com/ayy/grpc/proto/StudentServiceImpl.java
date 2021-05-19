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
}
