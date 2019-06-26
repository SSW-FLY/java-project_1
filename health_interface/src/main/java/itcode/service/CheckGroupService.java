package itcode.service;

import itcode.entity.PageResult;
import itcode.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();

}
