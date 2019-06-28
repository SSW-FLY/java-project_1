package itcode.service;

import itcode.pojo.OrderSetting;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void editNumberByDate(OrderSetting orderSetting) throws Exception;

    List<Map> getOrderSettingByMonth(String data);

    void add(List<OrderSetting> orderSettingList) throws Exception;
}
