package com.ayy.server;

import com.ayy.service.PersonServiceImpl;
import com.ayy.thrift.generated.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/05/2021
 * @ Version 1.0
 */

public class ThriftServer {
    public static void main(String[] args) throws Exception {
        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(10000);
        THsHaServer.Args arg = new THsHaServer.Args(serverSocket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        TServer server = new THsHaServer(arg);
        System.out.println("Thrift Server Started");

        server.serve();
    }
}
