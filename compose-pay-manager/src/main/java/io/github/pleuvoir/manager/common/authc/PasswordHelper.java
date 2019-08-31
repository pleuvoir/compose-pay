package io.github.pleuvoir.manager.common.authc;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.SimpleByteSource;

import io.github.pleuvoir.manager.common.Const;
import io.github.pleuvoir.manager.model.po.pub.PubUserPO;

public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private final int hashIterations = 2;

    public void encryptPassword(PubUserPO user) {
    	String newPassword = "";
    	String newPayPasswd = "";
    	
        // User对象包含最基本的字段Username和Password
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        // 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
        if(StringUtils.isNotBlank(user.getPassword())) {
        newPassword = new SimpleHash(Const.PASSWORD_ALGORITHM_NAME, user.getPassword(),
        		new SimpleByteSource(user.getSalt()), hashIterations).toHex();
        }
        if(StringUtils.isNotBlank(user.getSafePassword())) {
        newPayPasswd = new SimpleHash(Const.PASSWORD_ALGORITHM_NAME, user.getSafePassword(),
        		new SimpleByteSource(user.getSalt()), hashIterations).toHex();
        }
        user.setPassword(newPassword);
        user.setSafePassword(newPayPasswd);
    }
    
    public String encryptPassword(String password,String salt) {
    	String newPassword = "";
    	if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(salt)) {
    		newPassword = new SimpleHash(Const.PASSWORD_ALGORITHM_NAME, password,
        		new SimpleByteSource(salt), hashIterations).toHex();
        }
    	return newPassword;
    }
    
}
