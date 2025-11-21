package ca.bigbigbai.onlineshopping.db.dao;

import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingOrder;

public interface OnlineShoppingOrderDao {
    int insertOrder(OnlineShoppingOrder order);

    OnlineShoppingOrder queryOrderByNum(String orderNo);

    int updateOrder(OnlineShoppingOrder order);
}
