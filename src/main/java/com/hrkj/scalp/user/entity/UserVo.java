package com.hrkj.scalp.user.entity;

import lombok.Data;

/**
 * @author vring
 * @ClassName UserVo.java
 * @Description 存放APP端码商基础信息的实体对象
 * @createTime 2020/3/17 11:19
 */
@Data
public class UserVo {
    private String id;
    private String name;
    private String icon;
    private Integer voucherMoney;//佣金/抵用券
    private String groupName;
    private Integer sonNum;//下级的下级人数
    private Integer dayMoney; //当天收益（个人利润+下级返利）
    private Integer allMoney; //累积收益
}
