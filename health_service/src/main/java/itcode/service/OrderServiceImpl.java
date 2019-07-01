package itcode.service;

import cn.hutool.core.date.DateTime;

import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import itcode.common.MessageConstant;
import itcode.common.OrderException;

import cn.hutool.core.collection.CollectionUtil;
import itcode.dao.MemberDao;
import itcode.dao.OrderDao;
import itcode.dao.OrdersDao;
import itcode.entity.Result;
import itcode.pojo.Member;
import itcode.pojo.Order;
import itcode.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderSettingDao;

    @Override
    public Result submit(Map order) {

        /**
         * 处理请求
         * 1.判断预约当天是否有预约
         *      a.通过时间查出预约信息
         * 2.预约存在再判断可预约人数是否充足
         *      a.查出预约信息，判断可预约人数与已预约人数
         * 3.收集用户信息
         *      a.先通过电话查询用户是否存在
         *      b.用户不存在创建新用户，保存姓名，电话，身份证等信息
         *      c.将新用户的数据保存到数据库
         * 4.判断用户是否重复预约
         *      a.通过用户ID,套餐ID,以及预约时间判断是否重复
         * 5.保存用户预约设置
         *      a.保存用户预约设置到数据库
         * 6.修改预约已预约人数+1
         *      a.通过之前查处的预约信息，修改已预约人数
         */
        //1.通过时间判断预约当天是否有预约设置
        //a.获取时间
        String orderDateStr = (String) order.get("orderDate");
        DateTime orderDate = DateUtil.parseDate(orderDateStr);
        //b.通过时间查询预约设置
        OrderSetting orderSetting = orderSettingDao.findOrderByDate1(orderDate);

        //2.判断人数
        if (orderSetting.getReservations()>=orderSetting.getNumber()){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //3.收集用户信息
        //  a.取到手机号
        String telephone = (String) order.get("telephone");
        //  b.查询用户是否存在
        Member member = memberDao.findMemberByTelephone(telephone);
        //判断用户不存在
        if (null == member){
            member = new Member();
            member.setName((String) order.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) order.get("idCard"));
            member.setRegTime(new Date());
            member.setSex((String) order.get("sex"));

            //将新用户插入数据库中
            memberDao.add(member);
        }

        //4.判断用户是否已经预约
        //a.用户id,套餐id,预约时间
        Integer memberId = member.getId();
        String setmealId = (String) order.get("setmealId");
        //b.封装上述信息
        Order query = new Order();
        query.setMemberId(memberId);
        query.setSetmealId(Integer.valueOf(setmealId));
        query.setOrderDate(orderDate);
        //c.查询
        List<Order> orders = orderDao.findOrderByCondition(query);

        if (CollectionUtil.isNotEmpty(orders)){
            return  new Result(false,MessageConstant.HAS_ORDERED);
        }

        //5.保存用户预约信息
        Order order1 = new Order();
        order1.setMemberId(memberId);
        order1.setSetmealId(Integer.valueOf(setmealId));
        order1.setOrderDate(orderDate);
        order1.setOrderType((String) order.get("orderType"));
        order1.setOrderStatus(Order.ORDERSTATUS_NO);
        //用户预约信息保存到数据库
        orderDao.add(order1);

        //6.修改预约设置的已预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS);
    }
}
