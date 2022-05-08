package org.example.service.Impl;

import org.example.dao.GoodsDao;
import org.example.dao.SaleDao;
import org.example.domain.Goods;
import org.example.domain.Sale;
import org.example.exception.NotEnoughException;
import org.example.service.BuyGoodsService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BuyGoodsServiceImpl implements BuyGoodsService {
    private GoodsDao goodsDao;
    private SaleDao saleDao;
    //@Transactional放在public方法的上面，表示方法有事务功能
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false, timeout = 20,
            rollbackFor = {NullPointerException.class, NotEnoughException.class})
    @Override
    public void buy(Integer goodId, Integer num) {
        System.out.println("====buy方法的开始");

        //生成销售记录
        Sale sale = new Sale();
        sale.setGid(goodId);
        sale.setNum(num);
        saleDao.insertSale(sale); //默认情况，事务自动提交

//        查询商品
        Goods goods = goodsDao.selectById(goodId);


        if (goods == null) {
            throw new NullPointerException(goodId + ",商品不存在");
        } else if (goods.getAmount() < num) {
            throw new NotEnoughException(goodId + "，库存不足");
        }
        //更新库存
        Goods buyGoods = new Goods();
        buyGoods.setId(goodId);
        buyGoods.setAmount(num);
        goodsDao.updateGoods(buyGoods);
    }

    public GoodsDao getGoodsDao() {
        return goodsDao;
    }

    public void setGoodsDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public SaleDao getSaleDao() {
        return saleDao;
    }

    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }
}
