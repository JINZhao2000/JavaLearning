let service = require("./static/Student_grpc_pb");
let message = require("./static/Student_pb");
let grpc = require("@grpc/grpc-js");

let server = new grpc.Server();
server.addService(service.StudentServiceService, {
    getRealNameByStudentname: getRealNameByStudentname,
    getStudentsByAge: getStudentsByAge,
    getStudentsWrapperByAges: getStudentsWrapperByAges,
    bidirectionalTalk: bidirectionalTalk
});

server.bindAsync("localhost:10000",grpc.credentials.createInsecure(),()=>{
    server.start();
});

function getRealNameByStudentname(call, callback) {
    console.log(call.request.getStudentname());
    // callback(err, obj)
    let resp = new message.MyResponse();
    resp.setRealname("realname");
    callback(null, resp);
}

function getStudentsByAge(call, callback) {}

function getStudentsWrapperByAges(call, callback) {}

function bidirectionalTalk(call, callback) {}