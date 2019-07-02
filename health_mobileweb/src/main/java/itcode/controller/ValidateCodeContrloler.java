package itcode.controller;

import itcode.common.MessageConstant;
import itcode.common.RedisMessageConstant;
import itcode.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.JuheSmsUtil;
import utils.SMSUtils;
import utils.ValidateCodeUtils;



@RestController
@RequestMapping("/validateCode")
public class ValidateCodeContrloler {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("send4Order")
    public Result send4Order(String telephone){
        //生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //发送验证码
        JuheSmsUtil.sendCode(telephone,String.valueOf(code));
        //保存到redis,提交时验证
        Jedis jedis = jedisPool.getResource();
        jedis.setex(telephone+RedisMessageConstant.SENDTYPE_ORDER,60*100,String.valueOf(code));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("send4Login")
    public Result send4Login(String telephone){
        //生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //发送验证码
        JuheSmsUtil.sendCode(telephone,String.valueOf(code));
        //保存到redis,提交时验证
        Jedis jedis = jedisPool.getResource();
        jedis.setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,60*100,String.valueOf(code));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
