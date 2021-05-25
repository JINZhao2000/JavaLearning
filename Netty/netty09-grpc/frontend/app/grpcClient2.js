let service = require("./static/Student_grpc_pb");
let message = require("./static/Student_pb");
// npm install -g grpc-tools
// grpc_tools_node_protoc --js_out=import_style=commonjs,binary:../app/static/ --grpc_out=grpc_js:../app/static/ Student.proto

let grpc = require("@grpc/grpc-js");

let client = new service.StudentServiceClient("localhost:10000", grpc.credentials.createInsecure());

let request = new message.MyRequest();
request.setStudentname("StudentName");

client.getRealNameByStudentname(request, function (err, data){
    console.log(data.getRealname());
})
