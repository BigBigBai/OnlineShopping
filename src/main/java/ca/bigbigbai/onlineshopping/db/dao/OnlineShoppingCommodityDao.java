package ca.bigbigbai.onlineshopping.db.dao;

import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingCommodity;

import java.util.List;

public interface OnlineShoppingCommodityDao {
    int insertCommodity(OnlineShoppingCommodity commodity);
    OnlineShoppingCommodity selectCommodity(long commodityId);
    List<OnlineShoppingCommodity> listCommoditiesByUserId(Long userId);
}
