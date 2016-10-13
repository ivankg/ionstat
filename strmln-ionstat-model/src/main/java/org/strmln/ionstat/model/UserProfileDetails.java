package org.strmln.ionstat.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserProfileDetails extends UserDetails {

	UserProfile getUserProfile();

	void setUserProfile(UserProfile userProfile);

}
