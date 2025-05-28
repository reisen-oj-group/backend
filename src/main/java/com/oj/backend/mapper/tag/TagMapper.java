package com.oj.backend.mapper.tag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.backend.pojo.tag.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * 题目标签数据访问接口
 * <p>基于MyBatis-Plus的标签(Tag)基础映射接口，提供默认的CRUD操作能力</p>
 *
 * <p><b>设计说明：</b></p>
 * <ul>
 *   <li>当前版本暂未定义自定义查询方法</li>
 *   <li>所有基础数据库操作均通过继承的{@code BaseMapper}提供</li>
 *   <li>如需扩展，建议优先使用{@code @Select}注解方式添加方法</li>
 * </ul>
 *
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper#insert(Object)  基础插入方法
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper#selectById(Serializable)  基础ID查询
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper#updateById(Object)  基础更新方法
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper#deleteById(Object)  基础删除方法
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
