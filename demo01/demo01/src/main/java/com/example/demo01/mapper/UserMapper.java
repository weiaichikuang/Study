package com.example.demo01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo01.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title: UserMapper
 * @Author 陈友强
 * @Package com.weiaichikuang.securitystudy.mapper
 * @Date 2023/10/31 16:59
 * @description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
