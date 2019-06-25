package itcode.dao;

import com.github.pagehelper.Page;
import itcode.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkItemId") Integer checkItemId);

    Page<CheckGroup> findPage(@Param("queryString") String queryString);

    CheckGroup findById(@Param("checkGroupId") Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(@Param("checkGroupId") Integer id);

    void deleteCheckGroupAndCheckItem(@Param("checkGroupId") Integer checkGroupId);

    void editCheckGroup(CheckGroup checkGroup);
}
