package itcode.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import itcode.common.MessageConstant;
import itcode.entity.Result;
import itcode.pojo.CheckGroup;
import itcode.pojo.Setmeal;
import itcode.service.SetMealServiece;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.QiniuUtils;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {


    @Reference
    private SetMealServiece setMealServiece;

    @RequestMapping("upload")
    public Result upload(@RequestParam ("imgFile") MultipartFile imgFile){
        try {
            //获取原始文件名称
            String filename = imgFile.getOriginalFilename();
            //获取文件后缀
            int i = filename.lastIndexOf(".");
            String suffix = filename.substring(i);
            //产生UUID
            String fileName = UUID.randomUUID().toString()+suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    @RequestMapping("add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setMealServiece.add(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
}
