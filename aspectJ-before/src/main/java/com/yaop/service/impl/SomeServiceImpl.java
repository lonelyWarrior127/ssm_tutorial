package com.yaop.service.impl;

import com.yaop.service.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome(String name, Integer age) {
        System.out.println("业务方法doSome()，创建商品的订单");

    }
}


