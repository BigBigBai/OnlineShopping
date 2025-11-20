package ca.bigbigbai.onlineshopping.db.mappers;

import ca.bigbigbai.onlineshopping.db.po.OnlineShoppingCommodity;

public interface OnlineShoppingCommodityMapper {
    int deleteByPrimaryKey(Long commodityId);

    int insert(OnlineShoppingCommodity record);

    int insertSelective(OnlineShoppingCommodity record);

    OnlineShoppingCommodity selectByPrimaryKey(Long commodityId);

    int updateByPrimaryKeySelective(OnlineShoppingCommodity record);

    int updateByPrimaryKey(OnlineShoppingCommodity record);
}