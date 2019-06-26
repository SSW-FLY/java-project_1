package itcode.service;

import com.alibaba.dubbo.config.annotation.Service;
import itcode.dao.SetMealDao;
import itcode.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = SetMealServiece.class)
@Transactional
public class SetMealServieceImpl implements SetMealServiece {

    @Autowired
    private SetMealDao setMealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
    }

    private void setSetmealAndCheckGroup(Integer setMealId,Integer[] checkgroupIds) {
        if (checkgroupIds !=null&& checkgroupIds.length>0){
            for (Integer id : checkgroupIds) {
                setMealDao.setSetmealAndCheckGroup(setMealId,id);
            }
        }
    }
}
