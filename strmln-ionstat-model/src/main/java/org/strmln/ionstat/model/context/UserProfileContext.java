package org.strmln.ionstat.model.context;

import org.strmln.ionstat.model.UserProfile;

public class UserProfileContext {

	private static ThreadLocal<UserProfile> _userProfileContext = new ThreadLocal<>();

	public static void clear() {
		_userProfileContext.remove();
	}

	public static UserProfile getCurrentUserProfile() {
		return _userProfileContext.get();
	}

	public static void setCurrentUserProfile(UserProfile userProfile) {
		_userProfileContext.set(userProfile);
	}
}
