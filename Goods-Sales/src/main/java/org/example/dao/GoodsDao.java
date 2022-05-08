package org.example.dao;

import org.example.domain.Goods;

public interface GoodsDao {
    Goods selectById(Integer id);
    //参数goods 表示是本次购买的商品id和购买数量
    // id 商品id amoun：本次购买的此商品的数量
    int updateGoods(Goods goods);
}
