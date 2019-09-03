<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
 
<mapper namespace="io.github.pleuvoir.manager.dao.pay.${dataModel.name}Dao">

	<sql id="select_all">
		SELECT ${dataModel.allColumnsBySql} FROM ${dataModel.tableName} 
	</sql>
 
 	<resultMap id="paramRs" type="io.github.pleuvoir.manager.model.po.pay.${dataModel.name}PO">
    	<#list dataModel.metaData.columnExtendList as columnExtend>
			  <result column="${columnExtend.columnName?lower_case}" 		property="${columnExtend.field}"/>
    	</#list>
    </resultMap>
    
    <select id="find" resultMap="paramRs">
    	<include refid="select_all"/> 
    	<where>
    		<#list dataModel.metaData.columnExtendList as columnExtend>
    			${r'<if test="form.'}${columnExtend.field}!=null and form.${columnExtend.field}!=''">AND ${columnExtend.columnName?lower_case} = ${r"#{form."}${columnExtend.field}}${r"</if>"}
        	</#list>
    	</where> 
    </select>
    
</mapper>