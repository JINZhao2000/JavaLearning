# _*_ coding:utf-8 _*_
__author__ = "Zhao JIN"

from thrift import Thrift
from com.ayy.thrift.py.generated import PersonService
from com.ayy.thrift.py.handler.PersonServiceHandler import PersonServiceHandler
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.transport import TSocket
from thrift.server import TServer

try:
    personServiceHandler = PersonServiceHandler()
    processor = PersonService.Processor(personServiceHandler)
    serverSocket = TSocket.TServerSocket(host="127.0.0.1",port=10000)

    transportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TSimpleServer(processor, serverSocket, transportFactory, protocolFactory)

    server.serve()
except Thrift.TException as ex:
    print("%s" % ex.message)