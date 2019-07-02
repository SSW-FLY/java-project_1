package itcode.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import itcode.common.MessageConstant;
import itcode.common.RedisMessageConstant;
import itcode.entity.Result;
import itcode.pojo.Order;
import itcode.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import utils.JuheSmsUtil;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    /**
     * 提交预约，处理用户预约
     *
     * @param order
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map order) {

        Result result = null;
        try {
            doSubmit(order);
        } catch (Exception e) {
            e.printStackTrace();
            for (int i = 0; i < 2; i++) {
                doSubmit(order);
            }
        }
        return result;
    }


    private Result doSubmit(Map order) {
        /**
         * 1.判断验证码是否正确
         *      a.先取出前端传过来的电话与验证码
         *      b.再取出放在缓存中的的验证码
         *      c.比较验证码是否正确
         * 2.验证码正确再处理用户请求
         */
        String telephone = (String) order.get("telephone");
        String validateCode = (String) order.get("validateCode");
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (null == code) {

            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        if (!code.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //设置此次预约的方式
        order.put("orderType", Order.ORDERTYPE_WEIXIN);

        //调用service层处理预约请求
        Result result = orderService.submit(order);
        //请求处理结束后，发送短信通知用户预约成功
        if (result.isFlag()) {
            JuheSmsUtil.sendNotice(telephone);
        }
        return result;
    }

}
