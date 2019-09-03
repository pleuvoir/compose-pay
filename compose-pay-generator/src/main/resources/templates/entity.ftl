package io.github.pleuvoir.lazy.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "${dataModel.tableName?lower_case}")
public class ${dataModel.entityName} implements Serializable {
	
	
	<#list dataModel.metaData.columnExtendList as columnExtend>
	<#if "${columnExtend.columnName}" == "${dataModel.metaData.primaryKey}">
    @Id
	@GeneratedValue(generator = "${dataModel.entityName?uncap_first}Seq")
	@GenericGenerator(name="${dataModel.entityName?uncap_first}Seq",strategy="uuid")
	</#if>
	<#if "${columnExtend.columnTypeName}" == "BLOB">
	@Lob
    @Basic(fetch = FetchType.LAZY)
	</#if>
	@Column(name = "${columnExtend.columnName?lower_case}", <#if "${columnExtend.convertedType}" == "byte[]">columnDefinition = "BLOB",</#if><#if "${columnExtend.convertedType}" == "BigDecimal">precision = ${columnExtend.precision}, scale = ${columnExtend.scale},</#if><#if "${columnExtend.convertedType}" == "String">length = ${columnExtend.columnDisplaySize},</#if> nullable = ${columnExtend.isNullable})
	<#if "${columnExtend.columnTypeName}" == "TIMESTAMP">
	@Temporal(TemporalType.TIMESTAMP)
	</#if>
	private ${columnExtend.convertedType} ${columnExtend.field};
	
	</#list>
	
	// getter and setter
	
	<#list dataModel.metaData.columnExtendList as columnExtend>
	public ${columnExtend.convertedType} get${columnExtend.field?cap_first}() {
		return ${columnExtend.field};
	}
	public void set${columnExtend.field?cap_first}(${columnExtend.convertedType} ${columnExtend.field}) {
		this.${columnExtend.field} = ${columnExtend.field};
	}
	</#list>
}
	