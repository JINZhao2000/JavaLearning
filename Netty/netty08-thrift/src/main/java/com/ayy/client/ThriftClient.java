package com.ayy.client;

import com.ayy.thrift.generated.Person;
import com.ayy.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/05/2021
 * @ Version 1.0
 */

public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 10000));
        TProtocol protocol = new TCompactProtocol(transport);

        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();

            Person person = client.getPersonByUsername("me");

            System.out.println(person.getUsername()+"--"+person.getAge()+"--"+person.isMarried());
            System.out.println(person);

            System.out.println("------");

            Person person1 = new Person();
            person1.setUsername("me2");
            person1.setAge(18);
            person1.setMarried(false);

            client.savePerson(person1);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            transport.close();
        }
    }
}
