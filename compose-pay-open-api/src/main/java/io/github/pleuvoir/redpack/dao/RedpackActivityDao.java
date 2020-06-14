package io.github.pleuvoir.redpack.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.github.pleuvoir.redpack.model.po.RedpackActivityPO;
import org.apache.ibatis.annotations.Select;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface RedpackActivityDao extends BaseMapper<RedpackActivityPO> {

    @Select("select * from t_redpack_activity where id = #{id} for update")
    RedpackActivityPO selectByIdForUpdate(Long id);
}
