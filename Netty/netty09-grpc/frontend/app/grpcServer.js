let PROTO_FILE_PATH = '../proto/Student.proto';
let grpc = require('@grpc/grpc-js');
let protoLoader = require('@grpc/proto-loader');
let packageDefinition = protoLoader.loadSync(PROTO_FILE_PATH, {
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true
});
let protoDescriptor = grpc.loadPackageDefinition(packageDefinition);
let studentService = protoDescriptor.com.ayy.grpc;

let server = new grpc.Server();
server.addService(studentService.StudentService.service, {
    getRealNameByStudentname: getRealNameByStudentname,
    getStudentsByAge: getStudentsByAge,
    getStudentsWrapperByAges: getStudentsWrapperByAges,
    bidirectionalTalk: bidirectionalTalk
});
server.bindAsync("127.0.0.1:10000", grpc.ServerCredentials.createInsecure(), ()=>{
    server.start();
})


function getRealNameByStudentname(call, callback) {
    console.log(call.request);
    // callback(err, obj)
    callback(null, {realname: "realname"});
}

function getStudentsByAge(call, callback) {}

function getStudentsWrapperByAges(call, callback) {}

function bidirectionalTalk(call, callback) {}