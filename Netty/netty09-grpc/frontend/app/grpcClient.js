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

let client = new studentService.StudentService('localhost:10000', grpc.credentials.createInsecure());

client.getRealNameByStudentname({username: 'usr1'}, function(err, data) {
    console.log(data);
})
