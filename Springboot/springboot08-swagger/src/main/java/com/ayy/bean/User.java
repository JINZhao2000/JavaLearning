package com.ayy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 03/04/2021
 * @ Version 1.0
 */
@ApiModel("User")
// @Api
@Data
public class User {
    @ApiModelProperty("Username")
    private String uname;
    @ApiModelProperty("Password")
    private String pwd;
}
