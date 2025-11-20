package ca.bigbigbai.onlineshopping.db.dao;

import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingCommodity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OnlineShoppingCommodityDaoTest {
    @Resource
    OnlineShoppingCommodityDao onlineShoppingCommodityDao;

    @BeforeEach
    void setUp() {

    }

    @Test
    void insertCommodity() {
        OnlineShoppingCommodity onlineShoppingCommodity = OnlineShoppingCommodity.builder()
                .commodityName("name test")
                .commodityDesc("desc test")
                .availableStock(112)
                .totalStock(112)
                .price(111)
                .lockStock(0)
                .creatorUserId(123L)
                .build();

        onlineShoppingCommodityDao.insertCommodity(onlineShoppingCommodity);
    }


    @Test
    void selectCommodity() {
        OnlineShoppingCommodity onlineShoppingCommodity = onlineShoppingCommodityDao.selectCommodity(1004L);
        log.info(onlineShoppingCommodity.toString());
    }

    @Test
    void listCommodityByUserId() {
        List<OnlineShoppingCommodity> onlineShoppingCommodity = onlineShoppingCommodityDao.listCommoditiesByUserId(123L);
        log.info(onlineShoppingCommodity.toString());
    }
}