package util;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/02/2021
 * @ Version 1.0
 */
public class MyBatisUtils {

    static {
        try {
            InputStream is = MyBatisUtils.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
