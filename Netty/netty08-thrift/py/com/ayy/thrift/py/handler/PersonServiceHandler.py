# _*_ coding:utf-8 _*_
__author__ = "Zhao JIN"

from com.ayy.thrift.py.generated.ttypes import Person


class PersonServiceHandler:
    def getPersonByUsername(self, username):
        print("username : ", username)
        person = Person()
        person.username = username
        person.age = 21
        person.married = True
        return person

    def savePerson(self, person):
        print("username : ", person.username, ", age : ", person.age,", married : ",person.married)
