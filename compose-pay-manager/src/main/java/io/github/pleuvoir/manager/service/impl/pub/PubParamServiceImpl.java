package io.github.pleuvoir.manager.service.impl.pub;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.manager.common.Const;
import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.dao.pub.PubParamDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubParamFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubParamPO;
import io.github.pleuvoir.manager.model.vo.pub.PubParamListVO;
import io.github.pleuvoir.manager.service.pub.PubParamService;

import java.math.BigDecimal;
import java.util.List;

@Service("pubParamService")
public class PubParamServiceImpl implements PubParamService {

	@Autowired
	private PubParamDao paramDao;
	
	@Override
	public PubParamListVO queryList(PubParamFormDTO form) {
		
		PageCondition pageCondition = PageCondition.create(form);
		
		if(StringUtils.isNotBlank(form.getName())) {
			form.setName("%".concat(form.getName()).concat("%"));
		}
		List<PubParamPO> list = paramDao.find(pageCondition, form);

		PubParamListVO paramList = new PubParamListVO(pageCondition);
		paramList.setRows(list);
		return paramList;
	}

	@Override
	public PubParamPO getParam(String code) {
		return paramDao.selectById(StringUtils.trim(code));
	}
	
	private PubParamPO checkParamValue(PubParamPO param) throws BusinessException {
		if(StringUtils.isBlank(param.getType())) {
			throw new BusinessException("未选择参数类型");
		}
		switch (param.getType()) {
		case PubParamPO.TYPE_DECIMAL:
			if(param.getDecimalVal()==null) {
				throw new BusinessException("未填写小数类型参数值");
			}
			param.setIntVal(0);
			param.setStrVal(null);
			param.setBooleanVal(null);
			break;
		case PubParamPO.TYPE_INT:
			if(param.getIntVal()==null) {
				throw new BusinessException("未填写整数类型参数值");
			}
			param.setStrVal(null);
			param.setDecimalVal(BigDecimal.ZERO);
			param.setBooleanVal(null);
			break;
		case PubParamPO.TYPE_STRING:
			if(StringUtils.isBlank(param.getType())) {
				throw new BusinessException("未填写字符类型参数值");
			}
			param.setIntVal(0);
			param.setDecimalVal(BigDecimal.ZERO);
			param.setBooleanVal(null);
			break;
		case PubParamPO.TYPE_BOOLEAN:
			if(param.getBooleanVal()==null) {
				throw new BusinessException("未填写布尔类型参数值");
			}
			param.setIntVal(0);
			param.setStrVal(null);
			param.setDecimalVal(BigDecimal.ZERO);
			break;
		default:
			throw new BusinessException("参数类型有误：" + param.getType());
		}
		return param;
	}

	@Override
	public void modify(PubParamPO param) throws BusinessException {
		param = checkParamValue(param);
		Integer rs = paramDao.updateAllColumnById(param);
		AssertUtil.assertOne(rs, "更新参数失败");
	}

	@Override
	public void save(PubParamPO param) throws BusinessException {
		int count = paramDao.countByCode(param.getCode());
		if(count>0) {
			throw new BusinessException("参数编码已存在");
		}
		param = checkParamValue(param);
		Integer rs = paramDao.insert(param);
		AssertUtil.assertOne(rs, "保存参数失败");
	}

	@Override
	public void remove(String code) throws BusinessException {
		Integer rs = paramDao.deleteById(code);
		AssertUtil.assertOne(rs, "删除参数失败");
	}

	/**
	 * 获取登录连续失败最大次数
	 * @return
	 */
	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public int getLoginErrorCount() {
		return 5;
	}

	/**
	 * 登录用户锁定时间（分）
	 * @return
	 */
	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public int getLoginLockTime() {
		return 30 * 60;
	}

	/**
	 * 获取文件服务器访问地址
	 * @return
	 */
	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public String getFileServerAccessUrl() {
		return paramDao.getStringValue(PubParamPO.CODE_FASTDFS_ADDRESS);
	}

	/**
	 * 获取定时任务访问地址
	 * @return
	 */
	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public String getTaskAddress() {
		return paramDao.getStringValue(PubParamPO.CODE_AUCTIONTASK_ADDRESS);
	}

	/**
	 * 临时文件存放路径
	 */
	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public String getTempFilePath() {
		return paramDao.getStringValue(PubParamPO.CODE_TEMP_FILE_PATH);
	}

	/**
	 * 图片压缩高度
	 */
	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public int getCompressImagePixel() {
		Integer height = paramDao.getIntegerValue(PubParamPO.CODE_COMPRESS_IMAGE_PIXEL);
		return height==null ? 1400 : height;
	}

	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public int getLotTotalLowerLimit() {
		Integer val = paramDao.getIntegerValue(PubParamPO.CODE_LOT_TOTAL_LOWER_LIMIT);
		return val==null ? 20 : val;
	}

	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public int getLotTotalUpperLimit() {
		Integer val = paramDao.getIntegerValue(PubParamPO.CODE_LOT_TOTAL_UPPER_LIMIT);
		return val==null ? 500 : val;
	}

	@Cacheable(key = Const.CACHEABLE_KEY_EXPRESSION, value = Const.CACHEABLE_VALUE)
	@Override
	public String getFastdfsFileUrl() {
		return paramDao.getStringValue(PubParamPO.CODE_FASTDFS_ADDRESS);
	}

}
