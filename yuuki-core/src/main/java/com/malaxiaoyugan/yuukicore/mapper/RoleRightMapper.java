package com.malaxiaoyugan.yuukicore.mapper;

import com.malaxiaoyugan.yuukicore.entity.RoleRight;
import com.malaxiaoyugan.yuukicore.entity.RoleRightExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleRightMapper {
    int countByExample(RoleRightExample example);

    int deleteByExample(RoleRightExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleRight record);

    int insertSelective(RoleRight record);

    List<RoleRight> selectByExample(RoleRightExample example);

    RoleRight selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleRight record, @Param("example") RoleRightExample example);

    int updateByExample(@Param("record") RoleRight record, @Param("example") RoleRightExample example);

    int updateByPrimaryKeySelective(RoleRight record);

    int updateByPrimaryKey(RoleRight record);
}