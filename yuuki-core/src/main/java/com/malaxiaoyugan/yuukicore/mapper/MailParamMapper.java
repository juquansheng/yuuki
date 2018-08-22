package com.malaxiaoyugan.yuukicore.mapper;

import com.malaxiaoyugan.yuukicore.entity.MailParam;
import com.malaxiaoyugan.yuukicore.entity.MailParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MailParamMapper {
    int countByExample(MailParamExample example);

    int deleteByExample(MailParamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MailParam record);

    int insertSelective(MailParam record);

    List<MailParam> selectByExample(MailParamExample example);

    MailParam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MailParam record, @Param("example") MailParamExample example);

    int updateByExample(@Param("record") MailParam record, @Param("example") MailParamExample example);

    int updateByPrimaryKeySelective(MailParam record);

    int updateByPrimaryKey(MailParam record);
}