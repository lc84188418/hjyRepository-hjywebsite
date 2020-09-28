package com.hjy.system.service.impl;

import com.hjy.common.utils.IDUtils;
import com.hjy.system.dao.TSysDeptMapper;
import com.hjy.system.entity.ReDeptUser;
import com.hjy.system.entity.TSysDept;
import com.hjy.system.service.TSysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (TSysDept)表服务实现类
 *
 * @author makejava
 * @since 2020-09-28 09:48:46
 */
@Service
public class TSysDeptServiceImpl implements TSysDeptService {
    @Resource
    private TSysDeptMapper tSysDeptMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param pkDeptId 主键
     * @return 实例对象
     */
    @Override
    public TSysDept selectById(String pkDeptId) throws Exception {
        return this.tSysDeptMapper.selectById(pkDeptId);
    }

    /**
     * 新增数据
     *
     * @param tSysDept 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public int insert(TSysDept tSysDept) throws Exception {
        return tSysDeptMapper.insertSelective(tSysDept);
    }

    /**
     * 修改数据
     *
     * @param tSysDept 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public int updateById(TSysDept tSysDept) throws Exception {
        return tSysDeptMapper.updateById(tSysDept);
    }

    /**
     * 通过主键删除数据
     *
     * @param pkDeptId 主键
     * @return 是否成功
     */
    @Transactional()
    @Override
    public int deleteById(String pkDeptId){
        return tSysDeptMapper.deleteById(pkDeptId);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<TSysDept> selectAll() throws Exception {
        return this.tSysDeptMapper.selectAll();
    }

    /**
     * 通过实体查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<TSysDept> selectAllByEntity(TSysDept tSysDept) throws Exception {
        return this.tSysDeptMapper.selectAllByEntity(tSysDept);
    }

    @Override
    public List<String> selectAllDeptName() {
        return tSysDeptMapper.selectAllDeptName();
    }

    @Override
    public List<String> selectDeptUser_userIded() {
        return tSysDeptMapper.selectDeptUser_userIded();
    }

    @Override
    public List<String> selectDeptUserByDept(String deptIdStr) {
        return tSysDeptMapper.selectDeptUserByDept(deptIdStr);
    }
    @Transactional()
    @Override
    public int deleteDeptUserByDeptId(String fk_dept_id) {
        return tSysDeptMapper.deleteDeptUserByDeptId(fk_dept_id);
    }

    @Transactional()
    @Override
    public int addDeptUserByList(String fk_dept_id, List<String> idList) {
        List<ReDeptUser> deptUsers = new ArrayList<>();
        for (String s:idList){
            ReDeptUser deptUser = new ReDeptUser();
            deptUser.setPk_deptUser_id(IDUtils.currentTimeMillis());
            deptUser.setFk_user_id(s);
            deptUser.setFk_dept_id(fk_dept_id);
            deptUsers.add(deptUser);
        }
        return tSysDeptMapper.addDeptUserByList(deptUsers);
    }
}