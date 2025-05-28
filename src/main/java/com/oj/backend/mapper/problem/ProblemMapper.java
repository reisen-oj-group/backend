package com.oj.backend.mapper.problem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.backend.pojo.problem.Problem;

/**
 * 题目数据访问接口
 * <p>基于MyBatis-Plus框架的题目表基础映射接口</p>
 *
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper MyBatis-Plus基础映射接口
 * @see com.oj.backend.pojo.problem.Problem 对应的题目实体类
 */
public interface ProblemMapper extends BaseMapper<Problem> {
    // 继承自BaseMapper的默认方法：
    // - 插入: int insert(Problem entity)
    // - 按ID删除: int deleteById(Serializable id)
    // - 按ID更新: int updateById(Problem entity)
    // - 按ID查询: Problem selectById(Serializable id)
    // - 分页查询: IPage<Problem> selectPage(IPage<Problem> page, Wrapper<Problem> queryWrapper)
    // - ......
}
