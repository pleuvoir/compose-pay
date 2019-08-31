package io.github.pleuvoir.manager.service.pub;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubParamFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubParamPO;
import io.github.pleuvoir.manager.model.vo.pub.PubParamListVO;

/**
 * 参数Service
 * @author abeir
 *
 */
public interface PubParamService {
	/**
	 * 列表查询
	 * @param form
	 * @return
	 */
	PubParamListVO queryList(PubParamFormDTO form);
	/**
	 * 按编号查询
	 * @param code
	 * @return
	 */
	PubParamPO getParam(String code);
	/**
	 * 修改参数，必须传入
	 * @param param
	 */
	void modify(PubParamPO param) throws BusinessException;
	/**
	 * 保存新参数
	 * @param param
	 */
	void save(PubParamPO param) throws BusinessException;
	/**
	 * 删除参数
	 * @param code 
	 * @throws BusinessException
	 */
	void remove(String code) throws BusinessException;

	/**
	 * 获取登录连续失败最大次数
	 * @return
	 */
	public int getLoginErrorCount();

	/**
	 * 登录用户锁定时间（分）
	 * @return
	 */
	public int getLoginLockTime();

	/**
	 * 获取文件服务器访问地址
	 * @return
	 */
	String getFileServerAccessUrl();

	/**
	 * 获取定时任务访问地址
	 * @return
	 */
	String getTaskAddress();

	/**
	 * 临时文件存放路径
	 * @return
	 */
	String getTempFilePath();

	/**
	 * 图片压缩高度
	 */
	int getCompressImagePixel();
	/**
	 * 专场拍品量下限
	 */
	int getLotTotalLowerLimit();
	/**
	 * 专场拍品量上限
	 */
	int getLotTotalUpperLimit();
	
	  /**
     * 获取fastdfs文件存放路径
     */
    String getFastdfsFileUrl();
}
