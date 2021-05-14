# _*_ coding:utf-8 _*_
__author__ = "Zhao JIN"

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import  TCompactProtocol
from com.ayy.thrift.py.generated import PersonService
from com.ayy.thrift.py.generated.ttypes import Person

transport = None
try:
    tSocket = TSocket.TSocket('localhost', 10000)
    tSocket.setTimeout(900)

    transport = TTransport.TFramedTransport(tSocket)
    protocol = TCompactProtocol.TCompactProtocol(transport)

    client = PersonService.Client(protocol)
    transport.open()

    person = client.getPersonByUsername("Me")
    print(person)
    print(person.username)
    print(person.age)
    print(person.married)

    person2 = Person()
    person2.username = "Others"
    person2.age = 18
    person2.married = False

    client.savePerson(person2)
except Thrift.TException as tx:
    print('%s' % tx.message)
finally:
    transport.close()
