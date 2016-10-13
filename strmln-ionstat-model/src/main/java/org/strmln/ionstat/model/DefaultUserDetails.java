package org.strmln.ionstat.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class DefaultUserDetails implements UserProfileDetails {

	private static final long serialVersionUID = 4795141353160474392L;

	private static final String POLICY_ACCESS_IONSTAT = "POLICY_ACCESS_IONSTAT";

	private UserProfile _userProfile;
	private Set<GrantedAuthority> _grantedAuthorities;

	public DefaultUserDetails(UserProfile userProfile) {
		_userProfile = userProfile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (_grantedAuthorities == null) {
			Set<Policy> policies = _userProfile.getRole().getPolicies();

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			CollectionUtils.collect(policies, new Transformer() {

				@Override
				public Object transform(Object policy) {
					return new SimpleGrantedAuthority(policy.toString());
				}
			}, grantedAuthorities);

			grantedAuthorities.add(new SimpleGrantedAuthority(
					POLICY_ACCESS_IONSTAT));

			_grantedAuthorities = grantedAuthorities;

		}

		return _grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return _userProfile.getPassword();
	}

	@Override
	public String getUsername() {
		return _userProfile.getUsername();
	}

	@Override
	public UserProfile getUserProfile() {
		return _userProfile;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void setUserProfile(UserProfile userProfile) {
		_userProfile = userProfile;
	}

}
