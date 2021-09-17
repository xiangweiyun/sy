package com.sy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.center.common.constant.SysConstant;
import com.sy.center.common.exception.CustomException;
import com.sy.center.common.utils.BlankUtils;
import com.sy.center.common.utils.PasswordHash;
import com.sy.center.common.utils.SecurityUtils;
import com.sy.center.common.utils.StringUtils;
import com.sy.sys.entity.SysUser;
import com.sy.ecis.service.SysUserService;
import com.sy.sys.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 系统_用户 服务实现类
 * </p>
 *
 * @author xiangwy
 * @since 2020-12-01
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Value("${sy.name}")
	private String name;

	@Override
	public void add(SysUser sysUser) {
		try {
			if (StringUtils.isBlank(sysUser.getPassword())) {
				sysUser.setPassword(PasswordHash.createHash(SysConstant.INIT_PWD));
			} else {
				sysUser.setPassword(PasswordHash.createHash(sysUser.getPassword()));
			}
		} catch (Exception e) {
			log.error("用户密码加密错误");
			throw new CustomException("用户密码加密错误");
		}
		super.save(sysUser);
	}

	@Override
	public void resetPwd(Long id) {
		SysUser user = new SysUser();
		user.setId(id);
		try {
			user.setPassword(PasswordHash.createHash(SysConstant.INIT_PWD));
		} catch (Exception e) {
			log.error("用户密码加密错误");
			throw new CustomException("用户密码加密错误");
		}
		super.updateById(user);
	}

	@Override
	public void updatePwd(Long id, String newpwd) {
		SysUser user = new SysUser();
		user.setId(id);
		user.setPassword(SecurityUtils.MD5Encode(newpwd));
	}

	@Override
	public SysUser getByUsernameAndPassword(String username, String password) {
		SysUser sysUser = getByUserName(username);
		boolean result = false;
		try {
			result = PasswordHash.validatePassword(password, sysUser.getPassword());
		} catch (Exception e) {
			log.error("用户密码加密错误");
			throw new CustomException("用户密码加密错误");
		}
		if (!result) {
			return null;
		}
		return sysUser;
	}

	@Override
	public SysUser getByEmailAndPassword(String email, String password) {
		SysUser sysUser = getByEmail(email);
		boolean result = false;
		try {
			result = PasswordHash.validatePassword(password, sysUser.getPassword());
		} catch (Exception e) {
			log.error("用户密码加密错误");
			throw new CustomException("用户密码加密错误");
		}
		if (!result) {
			return null;
		}
		return sysUser;
	}

	@Override
	public SysUser getByMobileAndPassword(String mobile, String password) {
		SysUser sysUser = getByMobile(mobile);
		boolean result = false;
		try {
			result = PasswordHash.validatePassword(password, sysUser.getPassword());
		} catch (Exception e) {
			log.error("用户密码加密错误");
			throw new CustomException("用户密码加密错误");
		}
		if (!result) {
			return null;
		}
		return sysUser;
	}

	@Override
	public Map<String, Object> getLoginDatas(Long userId, boolean menuPerms) {
		Map<String, Object> rtDatas = new HashMap<String, Object>();
		SysUser sysUser = super.getById(userId);
		rtDatas.put("userInfo", sysUser);
		return rtDatas;
	}

	/**
	 * roleids 拼接成字符串 ‘id1’，‘id2’的格式
	 * 
	 * @author: xiangwy
	 * @date: 2020-12-03 10:01:18
	 * @param roleIdsroleIds
	 * @return
	 */
	@SuppressWarnings("unused")
	private String coverRoleIds(Set<String> roleIdsroleIds) {
		StringBuffer sb = new StringBuffer();
		for (String roleId : roleIdsroleIds) {
			sb.append("'").append(roleId).append("',");
		}
		String rt = sb.toString();
		if (rt.contains(",")) {
			rt = rt.substring(0, rt.lastIndexOf(","));
		}
		return rt;
	}

	@Override
	public SysUser getByUserName(String username) {
		QueryWrapper<SysUser> wrapper = Wrappers.query();
		wrapper.eq(SysUser.USERNAME, username);
		List<SysUser> list = super.list(wrapper);
		SysUser sysUser = null;
		if (BlankUtils.isNotBlank(list)) {
			sysUser = list.get(0);
		}
		return sysUser;
	}

	@Override
	public SysUser getByEmail(String email) {
		QueryWrapper<SysUser> wrapper = Wrappers.query();
		wrapper.eq(SysUser.EMAIL, email);
		List<SysUser> list = super.list(wrapper);
		SysUser sysUser = null;
		if (BlankUtils.isNotBlank(list)) {
			sysUser = list.get(0);
		}
		return sysUser;
	}

	@Override
	public SysUser getByMobile(String mobile) {
		QueryWrapper<SysUser> wrapper = Wrappers.query();
		wrapper.eq(SysUser.MOBILE, mobile);
		List<SysUser> list = super.list(wrapper);
		SysUser sysUser = null;
		if (BlankUtils.isNotBlank(list)) {
			sysUser = list.get(0);
		}
		return sysUser;
	}

	@Override
	public String getName() {
		return name;
	}

}
