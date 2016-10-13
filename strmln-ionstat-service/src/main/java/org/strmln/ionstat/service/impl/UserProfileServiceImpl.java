package org.strmln.ionstat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.strmln.ionstat.dao.UserProfileDao;
import org.strmln.ionstat.model.DefaultUserDetails;
import org.strmln.ionstat.model.UserProfile;
import org.strmln.ionstat.service.UserProfileService;

@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileDao _userProfileDao;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserProfile userProfile = getUserProfileDao().findUserByUsername(
				username);
		return new DefaultUserDetails(userProfile);
	}

	private UserProfileDao getUserProfileDao() {
		return _userProfileDao;
	}

}
