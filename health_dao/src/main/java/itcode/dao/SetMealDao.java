package itcode.dao;

import com.github.pagehelper.Page;
import itcode.pojo.CheckGroup;
import itcode.pojo.CheckItem;
import itcode.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("setmealId") Integer setMealId, @Param("checkgroupId") Integer id);

    Page<Setmeal> findByCondition(@Param("queryString") String queryString);

    List<Setmeal> findAllMeal();

    Setmeal findSetmealById(@Param("setMealId") Integer id);

    List<CheckGroup> findCheckGroupById(@Param("setMealId")Integer id);

    List<CheckItem> findCheckItemBy(@Param("checkgroupId") Integer id);

//    List<CheckItem> findCheckItemByCheckgroupIds(List<Integer> checkGroupIds);
}
