package com.cjl.basic.zone.project.manage.user.domain;

import lombok.Data;

/**
 * 访客类
 */
@Data
public class Visitor {
    private User currentAccount;
    private User toAccount;
}
