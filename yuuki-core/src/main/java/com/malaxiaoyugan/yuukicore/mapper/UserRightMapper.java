package com.malaxiaoyugan.yuukicore.mapper;

import com.malaxiaoyugan.yuukicore.entity.UserRight;
import com.malaxiaoyugan.yuukicore.entity.UserRightExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRightMapper {
    int countByExample(UserRightExample example);

    int deleteByExample(UserRightExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRight record);

    int insertSelective(UserRight record);

    List<UserRight> selectByExample(UserRightExample example);

    UserRight selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserRight record, @Param("example") UserRightExample example);

    int updateByExample(@Param("record") UserRight record, @Param("example") UserRightExample example);

    int updateByPrimaryKeySelective(UserRight record);

    int updateByPrimaryKey(UserRight record);
}