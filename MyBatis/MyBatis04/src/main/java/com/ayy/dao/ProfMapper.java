package com.ayy.dao;

import com.ayy.bean.Prof;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 01/03/2021
 * @ Version 1.0
 */
public interface ProfMapper {
    @Select("select * from prof where pid = #{pid}")
    Prof getProf(@Param("pid") int id);
}
