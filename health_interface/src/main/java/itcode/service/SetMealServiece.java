package itcode.service;

import itcode.entity.PageResult;
import itcode.pojo.Setmeal;

import java.util.List;

public interface SetMealServiece {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findpage(Integer currentPage, Integer pageSize, String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

//    Setmeal findById1(Integer id);
}
