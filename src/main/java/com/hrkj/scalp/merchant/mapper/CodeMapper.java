package com.hrkj.scalp.merchant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.merchant.entity.Code;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_code
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
 @Repository
public interface CodeMapper extends BaseMapper<Code> {

}
