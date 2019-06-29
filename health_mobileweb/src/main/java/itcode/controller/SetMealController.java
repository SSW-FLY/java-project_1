package itcode.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import itcode.common.MessageConstant;
import itcode.entity.Result;
import itcode.pojo.Setmeal;
import itcode.service.SetMealServiece;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealServiece setMealServiece;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmealList = setMealServiece.getSetmeal();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        //通过setMealId找到setmeal的详情
        try {
            Setmeal setmeal = setMealServiece.findById(id);
            return  new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
