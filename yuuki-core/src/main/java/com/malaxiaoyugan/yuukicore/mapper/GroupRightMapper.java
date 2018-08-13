package com.malaxiaoyugan.yuukicore.mapper;

import com.malaxiaoyugan.yuukicore.entity.GroupRight;
import com.malaxiaoyugan.yuukicore.entity.GroupRightExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupRightMapper {
    int countByExample(GroupRightExample example);

    int deleteByExample(GroupRightExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GroupRight record);

    int insertSelective(GroupRight record);

    List<GroupRight> selectByExample(GroupRightExample example);

    GroupRight selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GroupRight record, @Param("example") GroupRightExample example);

    int updateByExample(@Param("record") GroupRight record, @Param("example") GroupRightExample example);

    int updateByPrimaryKeySelective(GroupRight record);

    int updateByPrimaryKey(GroupRight record);
}