package itcode.dao;


import cn.hutool.core.date.DateTime;
import itcode.pojo.Order;
import itcode.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作订单表
 */
public interface OrdersDao {

    List<Order> findOrderByCondition(Order query);

    void add(Order order1);
}
