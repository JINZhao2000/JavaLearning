package com.ayy.schema;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;

import java.util.EnumSet;

/**
 * @ ClassName SchemaTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/01/2021 22H
 * @ Version 1.0
 */
public class SchemaTest {
    @Test
    public void testCreateDB(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        SchemaExport export = new SchemaExport();
        export.create(EnumSet.of(TargetType.DATABASE),metadata);
    }
}
