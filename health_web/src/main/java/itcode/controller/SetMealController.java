package itcode.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import itcode.common.MessageConstant;
import itcode.common.RedisConstant;
import itcode.entity.PageResult;
import itcode.entity.QueryPageBean;
import itcode.entity.Result;
import itcode.pojo.Setmeal;
import itcode.service.SetMealServiece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import utils.QiniuUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {


    @Reference
    private SetMealServiece setMealServiece;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取原始文件名称
            String filename = imgFile.getOriginalFilename();
            //获取文件后缀
            int i = filename.lastIndexOf(".");
            String suffix = filename.substring(i);
            //产生UUID
            String fileName = UUID.randomUUID().toString() + suffix;
            //图片上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //将上传图片名称存入Redis,基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            //图片上传成功，返回结果为true
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setMealServiece.add(setmeal, checkgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("findpage")
    public PageResult findpage(@RequestBody QueryPageBean queryPageBean) {
            PageResult pageResult = setMealServiece.findpage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),
                    queryPageBean.getQueryString());

            return  pageResult;
    }
}
