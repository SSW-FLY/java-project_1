package itcode.service;

import itcode.entity.PageResult;
import itcode.entity.Result;
import itcode.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemService {

    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize,String queryString);

    int deleteById(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}

