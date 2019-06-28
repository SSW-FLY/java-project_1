package itcode.service;

import com.alibaba.dubbo.config.annotation.Service;
import itcode.dao.OrderDao;
import itcode.pojo.OrderSetting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {


    @Autowired
    private OrderDao orderDao;

    @Override
    public void editNumberByDate(OrderSetting orderSetting) throws Exception {

        int count = orderDao.findOrderByDate(orderSetting.getOrderDate());
        OrderSetting os  = orderDao.findOrderByDate1(orderSetting.getOrderDate());
//        if (count > 0) {
//            //预约设置存在，更新
//            orderDao.updateNumberByDate(orderSetting);
//        } else {
//            //设置不存在，添加
//            orderDao.addNumberByDate(orderSetting);
//        }
        if (os==null){
            //不存在
            orderDao.addNumberByDate(orderSetting);
        }else {
            //存在
            if (os.getReservations()>orderSetting.getNumber()){
               //新数小不跟新
               throw new Exception("预约人数不能比已预约的人数少") ;
            }else {
                orderDao.updateNumberByDate(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String data) {
        String begainData = data + "-01";
        String EndData = data + "-31";

        List<Map> mapList = new ArrayList<>();

        List<OrderSetting> list = orderDao.getOrderSettingByMonth(begainData, EndData);

        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());//date
            orderSettingMap.put("number", orderSetting.getNumber());//人数
            orderSettingMap.put("reservations", orderSetting.getReservations());//已预约人数
            mapList.add(orderSettingMap);
        }
        return mapList;
    }


    @Override
    public void add(List<OrderSetting> list) throws Exception {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //检查此数据（日期）是否存在
                editNumberByDate(orderSetting);
            }
        }
    }
}

