package ca.bigbigbai.onlineshopping.db.dao.impl;

import ca.bigbigbai.onlineshopping.db.dao.OnlineShoppingOrderDao;
import ca.bigbigbai.onlineshopping.db.mappers.OnlineShoppingOrderMapper;
import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OnlineShoppingOrderDaoImpl implements OnlineShoppingOrderDao {
    @Resource
    OnlineShoppingOrderMapper mapper;

    public int insertOrder(OnlineShoppingOrder order) {
        return mapper.insert(order);
    }

    @Override
    public OnlineShoppingOrder queryOrderByNum(String orderNo) {
        return mapper.queryOrderByNum(orderNo);
    }

    @Override
    public int updateOrder(OnlineShoppingOrder order) {
        return mapper.updateByPrimaryKey(order);
    }
}
