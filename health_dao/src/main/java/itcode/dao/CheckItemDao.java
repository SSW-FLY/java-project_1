package itcode.dao;

import com.github.pagehelper.Page;
import itcode.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> findPage(@Param("queryString") String queryString);

    int deleteById(@Param("id") Integer id);

    CheckItem findById(@Param("id") Integer id);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();
}
