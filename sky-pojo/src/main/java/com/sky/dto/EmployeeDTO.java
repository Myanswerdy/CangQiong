package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工的数据传输类，用来接收前端传输信息
 */
@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
