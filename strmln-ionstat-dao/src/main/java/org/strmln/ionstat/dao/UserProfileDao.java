package org.strmln.ionstat.dao;

import org.strmln.ionstat.model.UserProfile;

public interface UserProfileDao extends GenericDao<UserProfile> {

	UserProfile findUserByUsername(String username);

}
