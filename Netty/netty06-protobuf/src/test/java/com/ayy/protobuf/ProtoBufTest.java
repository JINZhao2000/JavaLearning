package com.ayy.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/03/2021
 * @ Version 1.0
 */

public class ProtoBufTest {
    @Test
    public void StudentTest01() throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("Student1").setAge(18).setAddress("1 rue de A").build();

        byte[] byteStudent = student.toByteArray();

        DataInfo.Student student2 = DataInfo.Student.parseFrom(byteStudent);

        System.out.println(student2);
    }
}
