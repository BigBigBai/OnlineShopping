package ca.bigbigbai.onlineshopping.controller;

import ca.bigbigbai.onlineshopping.db.dao.OnlineShoppingCommodityDao;
import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingCommodity;
import ca.bigbigbai.onlineshopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class CommodityController {
    @Resource
    OnlineShoppingCommodityDao onlineShoppingCommodityDao;

    @Autowired
    private OrderService orderService;

    @GetMapping("/addItem")
    public String addCommodityStatic() {
        return "add_commodity";
    }

    @PostMapping("/commodities")
    public String addCommodities(@RequestParam("commodityId") long commodityId,
                                 @RequestParam("commodityName") String commodityName,
                                 @RequestParam("commodityDesc") String commodityDesc,
                                 @RequestParam("price") int price,
                                 @RequestParam("creatorUserId") long creatorUserId,
                                 @RequestParam("availableStock") int availableStock,
                                 Map<String, Object> resultMap
                                 ) {
        OnlineShoppingCommodity commodity = OnlineShoppingCommodity.builder()
                .commodityId(commodityId)
                .commodityName(commodityName)
                .commodityDesc(commodityDesc)
                .availableStock(availableStock)
                .totalStock(availableStock)
                .lockStock(0)
                .creatorUserId(creatorUserId)
                .price(price)
                .build();
        onlineShoppingCommodityDao.insertCommodity(commodity);
        resultMap.put("Item", commodity);
        return "add_commodity_success";
    }

    @GetMapping("commodities/{sellerId}")
    public String listCommodities(@PathVariable("sellerId") long sellerId, Map<String, Object> resultMap) {
        List<OnlineShoppingCommodity> commodities = onlineShoppingCommodityDao.listCommoditiesByUserId(sellerId);
        resultMap.put("itemList", commodities);
        return "list_items";
    }

    @GetMapping("item/{commodityId}")
    public String getCommodityDetail(@PathVariable("commodityId") long commodityId, Map<String, Object> resultMap) {
        OnlineShoppingCommodity commodity = onlineShoppingCommodityDao.selectCommodity(commodityId);
        resultMap.put("commodity", commodity);
        return "item_detail";
    }
}
