package io.github.pleuvoir.manager.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pay.${dataModel.name}FormDTO;
import io.github.pleuvoir.manager.model.po.pay.${dataModel.POName};

public interface ${dataModel.name}Dao extends BaseMapper<${dataModel.POName}> {

	List<${dataModel.POName}> find(PageCondition page, @Param("form") ${dataModel.name}FormDTO form);

}
