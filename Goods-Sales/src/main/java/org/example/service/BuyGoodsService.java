package org.example.service;

public interface BuyGoodsService {

    /**
     *
     * @param goodId：购买商品id
     * @param num  购买商品数量
     */
    void buy(Integer goodId,Integer num);
}
