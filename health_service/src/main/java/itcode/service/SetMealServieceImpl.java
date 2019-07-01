package itcode.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import itcode.common.RedisConstant;
import itcode.dao.SetMealDao;
import itcode.entity.PageResult;
import itcode.pojo.CheckGroup;
import itcode.pojo.CheckItem;
import itcode.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = SetMealServiece.class)
@Transactional
public class SetMealServieceImpl implements SetMealServiece {

    @Autowired
    private SetMealDao setMealDao;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        savePic2Redis(setmeal.getImg());
    }

    @Override
    public PageResult findpage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setMealDao.findByCondition(queryString);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Setmeal> getSetmeal() {
        List<Setmeal> setmeals = setMealDao.findAllMeal();
        return setmeals;
    }

    @Override
    public Setmeal findById(Integer id) {
        //setmeal包括多个checkgroup,一个checkgroup包括多个checkitem
        //先查出基本信息
        Setmeal setmeal = setMealDao.findSetmealById(id);
        //查checkgroup信息,通过中间表
        List<CheckGroup> checkGroups = setMealDao.findCheckGroupById(id);
        //查checkitem信息
        List<Integer> checkGroupIds = new ArrayList<>();
        for (CheckGroup checkGroup : checkGroups) {
            List<CheckItem> checkItems = setMealDao.findCheckItemBy(checkGroup.getId());
            checkGroup.setCheckItems(checkItems);
        }
        setmeal.setCheckGroups(checkGroups);
        return setmeal;
    }

    private void setSetmealAndCheckGroup(Integer setMealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer id : checkgroupIds) {
                setMealDao.setSetmealAndCheckGroup(setMealId, id);
            }
        }
    }

    //将保存到数据库的图片放到redis中
    private void savePic2Redis(String pic) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
    }
}
