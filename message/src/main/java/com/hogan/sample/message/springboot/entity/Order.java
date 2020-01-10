package com.hogan.sample.message.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TODO
 * wujun
 * 2020/01/08 11:51
 */
@Data

public class Order implements Serializable {

    private String orderId;

    private BigDecimal amount;
}
