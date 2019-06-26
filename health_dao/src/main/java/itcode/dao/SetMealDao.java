package itcode.dao;

import itcode.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

public interface SetMealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("setmealId") Integer setMealId, @Param("checkgroupId") Integer id);
}
