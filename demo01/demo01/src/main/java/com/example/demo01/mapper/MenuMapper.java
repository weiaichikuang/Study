package com.example.demo01.mapper;

/**
 * @Title: MenuMapper
 * @Author 陈友强
 * @Package com.example.demo01.mapper
 * @Date 2023/11/2 11:01
 * @description:
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo01.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
