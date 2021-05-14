package com.ayy.service;

import com.ayy.thrift.generated.DataException;
import com.ayy.thrift.generated.Person;
import com.ayy.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/05/2021
 * @ Version 1.0
 */

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("username : "+username);
        Person person = new Person();
        person.setUsername(username);
        person.setAge(21);
        person.setMarried(true);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("username : "+person.getUsername()+", age : "+person.getAge()+", married : "+person.isMarried());
    }
}
