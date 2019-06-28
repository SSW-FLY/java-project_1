package itcode.dao;

import itcode.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    int findOrderByDate(@Param("orderDate") Date orderDate);

    void updateNumberByDate(OrderSetting orderSetting);

    void addNumberByDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(@Param("begainData") String begainData,@Param("endData") String endData);

    void add(@Param("date")String date,@Param("num") String num);

    OrderSetting findOrderByDate1(@Param("orderDate")Date orderDate);
}
