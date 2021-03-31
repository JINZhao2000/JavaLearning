package com.ayy.mapper;

import com.ayy.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 31/03/2021
 * @ Version 1.0
 */

@Repository
@Mapper
public interface UserMapper {
    User queryUserByName(@Param("name") String name);
}
