package com.ayy.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhao JIN
 */

public class MyUDTF extends GenericUDTF {
    private ArrayList<String> output = new ArrayList<>();

    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        List<String> fielsNames = new ArrayList<>();
        fielsNames.add("word");
        List<ObjectInspector> fieldOIs = new ArrayList<>();
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fielsNames, fieldOIs);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        String s = args[0].toString();
        String[] words = s.split((args[1] == null ? "," : args[1].toString()));
        for (String word : words) {
            output.clear();
            output.add(word);
            forward(output);
        }
    }

    @Override
    public void close() throws HiveException {
        output.clear();
    }
}
