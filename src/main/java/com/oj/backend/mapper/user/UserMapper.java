package com.oj.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.backend.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问接口
 * <p>基于MyBatis-Plus的用户(User)基础映射接口，提供默认的CRUD操作能力</p>
 *
 * <p><b>设计说明：</b></p>
 * <ul>
 *   <li>继承MyBatis-Plus提供的通用{@code BaseMapper}接口</li>
 *   <li>默认包含基础的数据库操作方法（insert/update/delete/select）</li>
 *   <li>如需扩展自定义查询，建议采用MyBatis注解方式实现</li>
 * </ul>
 *
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper 基础映射接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 接口体保持为空，所有基础操作通过继承的BaseMapper提供
    // 自定义方法可按需在此处添加
}
