package ca.bigbigbai.onlineshopping.controller;

import ca.bigbigbai.onlineshopping.db.dao.OnlineShoppingCommodityDao;
import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingCommodity;
import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingOrder;
import ca.bigbigbai.onlineshopping.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

public class OrderController {
    // create order
    // get order detail

    @Resource
    OrderService orderService;

    @Resource
    OnlineShoppingCommodityDao onlineShoppingCommodityDao;

    @RequestMapping("/commodity/buy/{userId}/{commodityId}")
    public String buyCommodity(@PathVariable("userId") long userId,
                               @PathVariable("commodityId") long commodityId,
                               Map<String, Object> resultMap) {
        OnlineShoppingOrder order = orderService.placeOrder(userId, commodityId);
        if (order == null) {
            resultMap.put("resultInfo", "Order create failed, check log for detail");
            resultMap.put("orderNo", "");
        } else {
            resultMap.put("resultInfo", "Order created successfully");
            resultMap.put("orderNo", order.getOrderNo());
        }
        return "order_result";
    }

    @GetMapping("/commodity/orderQuery/{orderNo}")
    public String getOrderDetail(@PathVariable("orderNo") String orderNo, Map<String, Object> resultMap) {
        OnlineShoppingOrder order = orderService.queryOrderByNum(orderNo);

        OnlineShoppingCommodity commodity = onlineShoppingCommodityDao.selectCommodity(order.getCommodityId());
        resultMap.put("order", order);
        resultMap.put("commodity", commodity);
        return "order_check";
    }

    @RequestMapping("commodity/payOrder/{orderNum}")
    public String payOrder(@PathVariable("orderNum") String orderNum, Map<String, Object> resultMap) {
        orderService.payOrder(orderNum);
        return getOrderDetail(orderNum, resultMap);
    }
}
