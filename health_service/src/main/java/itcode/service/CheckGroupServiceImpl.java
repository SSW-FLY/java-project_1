package itcode.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import itcode.dao.CheckGroupDao;
import itcode.entity.PageResult;
import itcode.pojo.CheckGroup;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.add(checkGroup);

        setCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> list = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        return list;
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新checkgroup
        checkGroupDao.editCheckGroup(checkGroup);
        //删除对应中间表
        deleteCheckGroupAndCheckItem(checkGroup.getId());
        //插入对应中间表
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);

    }

    private  void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkItemIds){
        if (checkItemIds != null && checkItemIds.length > 0){
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.setCheckGroupAndCheckItem(checkGroupId,checkItemId);
            }
        }
    }

    private void deleteCheckGroupAndCheckItem(Integer checkGroupId){
        checkGroupDao.deleteCheckGroupAndCheckItem(checkGroupId);
    }
}
