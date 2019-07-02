package itcode.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import itcode.common.MessageConstant;
import itcode.common.RedisMessageConstant;
import itcode.entity.Result;
import itcode.pojo.Member;
import itcode.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String code = jedisPool.getResource().get(telephone+RedisMessageConstant.SENDTYPE_LOGIN);
        if (code ==null || !code.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Member member = memberService.findMemberByTelephone(telephone);
        if (null == member){
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }

        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60*24*60*30);
        response.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
