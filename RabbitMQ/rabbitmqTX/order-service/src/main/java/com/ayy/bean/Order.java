package com.ayy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private String orderId;
    private String userId;
    private String orderContent;
    private Date createTime;
}
