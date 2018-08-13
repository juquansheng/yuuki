package com.malaxiaoyugan.yuukicore.mapper;

import com.malaxiaoyugan.yuukicore.entity.Right;
import com.malaxiaoyugan.yuukicore.entity.RightExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RightMapper {
    int countByExample(RightExample example);

    int deleteByExample(RightExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Right record);

    int insertSelective(Right record);

    List<Right> selectByExample(RightExample example);

    Right selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Right record, @Param("example") RightExample example);

    int updateByExample(@Param("record") Right record, @Param("example") RightExample example);

    int updateByPrimaryKeySelective(Right record);

    int updateByPrimaryKey(Right record);
}