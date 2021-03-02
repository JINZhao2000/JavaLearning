package com.ayy.dao;

import com.ayy.bean.Prof;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 01/03/2021
 * @ Version 1.0
 */
public interface ProfMapper {
    List<Prof> getProfs();

    Prof getProf(@Param("pid") int id);

    Prof getProf2(@Param("pid") int id);
}
