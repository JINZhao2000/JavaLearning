// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('@grpc/grpc-js');
var Student_pb = require('./Student_pb.js');

function serialize_com_ayy_grpc_MyRequest(arg) {
  if (!(arg instanceof Student_pb.MyRequest)) {
    throw new Error('Expected argument of type com.ayy.grpc.MyRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_ayy_grpc_MyRequest(buffer_arg) {
  return Student_pb.MyRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_com_ayy_grpc_MyResponse(arg) {
  if (!(arg instanceof Student_pb.MyResponse)) {
    throw new Error('Expected argument of type com.ayy.grpc.MyResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_ayy_grpc_MyResponse(buffer_arg) {
  return Student_pb.MyResponse.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_com_ayy_grpc_StreamRequest(arg) {
  if (!(arg instanceof Student_pb.StreamRequest)) {
    throw new Error('Expected argument of type com.ayy.grpc.StreamRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_ayy_grpc_StreamRequest(buffer_arg) {
  return Student_pb.StreamRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_com_ayy_grpc_StreamResponse(arg) {
  if (!(arg instanceof Student_pb.StreamResponse)) {
    throw new Error('Expected argument of type com.ayy.grpc.StreamResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_ayy_grpc_StreamResponse(buffer_arg) {
  return Student_pb.StreamResponse.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_com_ayy_grpc_StudentRequest(arg) {
  if (!(arg instanceof Student_pb.StudentRequest)) {
    throw new Error('Expected argument of type com.ayy.grpc.StudentRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_ayy_grpc_StudentRequest(buffer_arg) {
  return Student_pb.StudentRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_com_ayy_grpc_StudentResponse(arg) {
  if (!(arg instanceof Student_pb.StudentResponse)) {
    throw new Error('Expected argument of type com.ayy.grpc.StudentResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_ayy_grpc_StudentResponse(buffer_arg) {
  return Student_pb.StudentResponse.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_com_ayy_grpc_StudentResponseList(arg) {
  if (!(arg instanceof Student_pb.StudentResponseList)) {
    throw new Error('Expected argument of type com.ayy.grpc.StudentResponseList');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_ayy_grpc_StudentResponseList(buffer_arg) {
  return Student_pb.StudentResponseList.deserializeBinary(new Uint8Array(buffer_arg));
}


var StudentServiceService = exports.StudentServiceService = {
  getRealNameByStudentname: {
    path: '/com.ayy.grpc.StudentService/GetRealNameByStudentname',
    requestStream: false,
    responseStream: false,
    requestType: Student_pb.MyRequest,
    responseType: Student_pb.MyResponse,
    requestSerialize: serialize_com_ayy_grpc_MyRequest,
    requestDeserialize: deserialize_com_ayy_grpc_MyRequest,
    responseSerialize: serialize_com_ayy_grpc_MyResponse,
    responseDeserialize: deserialize_com_ayy_grpc_MyResponse,
  },
  getStudentsByAge: {
    path: '/com.ayy.grpc.StudentService/GetStudentsByAge',
    requestStream: false,
    responseStream: true,
    requestType: Student_pb.StudentRequest,
    responseType: Student_pb.StudentResponse,
    requestSerialize: serialize_com_ayy_grpc_StudentRequest,
    requestDeserialize: deserialize_com_ayy_grpc_StudentRequest,
    responseSerialize: serialize_com_ayy_grpc_StudentResponse,
    responseDeserialize: deserialize_com_ayy_grpc_StudentResponse,
  },
  getStudentsWrapperByAges: {
    path: '/com.ayy.grpc.StudentService/GetStudentsWrapperByAges',
    requestStream: true,
    responseStream: false,
    requestType: Student_pb.StudentRequest,
    responseType: Student_pb.StudentResponseList,
    requestSerialize: serialize_com_ayy_grpc_StudentRequest,
    requestDeserialize: deserialize_com_ayy_grpc_StudentRequest,
    responseSerialize: serialize_com_ayy_grpc_StudentResponseList,
    responseDeserialize: deserialize_com_ayy_grpc_StudentResponseList,
  },
  bidirectionalTalk: {
    path: '/com.ayy.grpc.StudentService/BidirectionalTalk',
    requestStream: true,
    responseStream: true,
    requestType: Student_pb.StreamRequest,
    responseType: Student_pb.StreamResponse,
    requestSerialize: serialize_com_ayy_grpc_StreamRequest,
    requestDeserialize: deserialize_com_ayy_grpc_StreamRequest,
    responseSerialize: serialize_com_ayy_grpc_StreamResponse,
    responseDeserialize: deserialize_com_ayy_grpc_StreamResponse,
  },
};

exports.StudentServiceClient = grpc.makeGenericClientConstructor(StudentServiceService);
