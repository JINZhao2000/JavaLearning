package com.ayy;

import com.ayy.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/03/2021
 * @ Version 1.0
 */
@SpringBootTest
public class BeanTest {
    @Autowired
    private Person person;

    @Test
    public void testCouple(){
        System.out.println(person);
    }
}
