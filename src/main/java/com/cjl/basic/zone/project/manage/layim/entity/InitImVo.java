package com.cjl.basic.zone.project.manage.layim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author chen
 * @Date 2020/4/7 21:41
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InitImVo<T> {
    private String msg;
    private Integer code;
    private T data;
}
