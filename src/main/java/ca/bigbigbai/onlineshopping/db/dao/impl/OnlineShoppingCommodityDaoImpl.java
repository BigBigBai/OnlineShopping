package ca.bigbigbai.onlineshopping.db.dao.impl;

import ca.bigbigbai.onlineshopping.db.dao.OnlineShoppingCommodityDao;
import ca.bigbigbai.onlineshopping.db.mappers.OnlineShoppingCommodityMapper;
import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingCommodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Repository
public class OnlineShoppingCommodityDaoImpl implements OnlineShoppingCommodityDao {
    @Resource
    private OnlineShoppingCommodityMapper mapper;

    @Override
    public int insertCommodity(OnlineShoppingCommodity commodity) {
        return mapper.insert(commodity);
    }

    @Override
    public OnlineShoppingCommodity selectCommodity(long commodityId) {
        return mapper.selectByPrimaryKey(commodityId);
    }

    @Override
    public List<OnlineShoppingCommodity> listCommoditiesByUserId(Long userId) {
        return mapper.listCommoditiesByUserId(userId);
    }

    @Override
    public int updateCommodity(OnlineShoppingCommodity commodity) {
        return mapper.updateByPrimaryKeySelective(commodity);
    }
}
