package ca.bigbigbai.onlineshopping.service;

import ca.bigbigbai.onlineshopping.db.dao.OnlineShoppingCommodityDao;
import ca.bigbigbai.onlineshopping.db.dao.OnlineShoppingOrderDao;
import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingCommodity;
import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    @Resource
    OnlineShoppingOrderDao orderDao;

    @Resource
    OnlineShoppingCommodityDao commodityDao;

    public OnlineShoppingOrder placeOrder(long userId, long commodityId) {
        OnlineShoppingCommodity onlineShoppingCommodity = commodityDao.selectCommodity(commodityId);
        Integer availableStock = onlineShoppingCommodity.getAvailableStock();

        if (availableStock > 0) {
            availableStock--;

            onlineShoppingCommodity.setAvailableStock(availableStock);

            int result = commodityDao.updateCommodity(onlineShoppingCommodity);
            if (result == 1) {
                OnlineShoppingOrder order = OnlineShoppingOrder.builder()
                        .userId(userId)
                        .orderNo(UUID.randomUUID().toString())
                        .commodityId(commodityId)
                        .orderStatus(1)
                        .orderAmount(1L)
                        .createTime(new Date())
                        .build();
                orderDao.insertOrder(order);
                return order;
            }
        }

        log.warn("commodity out of stock, commodityId:" + onlineShoppingCommodity.getCommodityId());
        return null;
    }

    public OnlineShoppingOrder queryOrderByNum(String orderNo) {
        return orderDao.queryOrderByNum(orderNo);
    }

    // 1.pending payment
    // 2.finish payment
    // 99.overtime order
    public int payOrder(String orderNum) {
        OnlineShoppingOrder order = queryOrderByNum(orderNum);
        order.setOrderStatus(2);
        order.setPayTime(new Date());
        return orderDao.updateOrder(order);
    }
}
