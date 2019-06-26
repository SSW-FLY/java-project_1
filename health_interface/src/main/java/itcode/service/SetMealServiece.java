package itcode.service;

import itcode.entity.PageResult;
import itcode.pojo.Setmeal;

public interface SetMealServiece {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findpage(Integer currentPage, Integer pageSize, String queryString);
}
