# MongoDB

MongoDb 是 文档型（NoSQL）非关系型数据库

## 1. 基本概念

- 数据库 Database

    - 数据库不需要手动创建，在插入第一个文档时会自动创建

        ```mongodb
        show dbs;
        show databases;
        use <db>;
        db;
        ```

- 集合 Collection

    ```mongodb
    show collections;
    ```

- 文档 Document

## 2. CRUD

- insert

    ```mongodb
    db.<collection>.insert(<doc>);
    db.<collection>.insert([<doc1>,<doc2>]);
    db.<collection>.insertOne(<doc>);
    db.<collection>.insertMany([<doc1>,<doc2>]);
    ```

- query

    ```mongodb
    db.<collection>.find();
    db.<collection>.find({});
    db.<collection>.findOne({});
    db.<collection>.find({}).count();
    db.<collection>.find({}).length();
    ```

- update

    ```mongodb
    db.<collection>.update(<condition>,<doc>); // 替换
    db.<collection>.update(<condition>,{$set:<doc>}); // 修改域
    db.<collection>.update(<condition>,{$unset:<doc>}); // 删除域
    // update 默认只改一个
    db.<collection>.update(<condition>,{$unset:<doc>},<parameters>); 
    db.<collection>.updateMany();
    ```

- delete

    ```mongodb
    db.<collection>.remove();
    db.<collection>.deleteOne();
    db.<collection>.deleteMany();
    ```

    

