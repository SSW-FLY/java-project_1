package itcode.dao;

import com.github.pagehelper.Page;
import itcode.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

public interface SetMealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("setmealId") Integer setMealId, @Param("checkgroupId") Integer id);

    Page<Setmeal> findByCondition(@Param("queryString") String queryString);
}
