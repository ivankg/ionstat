package org.strmln.ionstat.mvc.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.strmln.ionstat.model.UserProfileDetails;
import org.strmln.ionstat.model.context.UserProfileContext;

public class UserProfileContextManagementFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		preHandle((HttpServletRequest) request, (HttpServletResponse) response);

		chain.doFilter(request, response);

		postHandle((HttpServletRequest) request, (HttpServletResponse) response);

	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response) {
		// clearing just userProfileContexts because Spring's
		// SecurityContextHolder will be cleared by
		// SECURITY_CONTEXT_FILTER in chain
		UserProfileContext.clear();
	}

	private UserProfileDetails getUserProfileDetails(HttpServletRequest request) {
		UserProfileDetails userProfileDetails = null;

		// if exists, security context is already loaded in
		// SecurityContextHolder by SECURITY_CONTEXT_FILTER in chain
		SecurityContext securityContext = SecurityContextHolder.getContext();

		if (securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();

			if (authentication != null) {
				Object principal = authentication.getPrincipal();

				if (principal instanceof UserProfileDetails) {
					userProfileDetails = (UserProfileDetails) principal;
				}
			}
		}

		return userProfileDetails;
	}

	private void preHandle(HttpServletRequest request,
			HttpServletResponse response) {
		// extract principal details and cache it in SecurityContextHolder
		UserProfileDetails userProfileDetails = getUserProfileDetails(request);
		if (userProfileDetails != null) {
			UserProfileContext.setCurrentUserProfile(userProfileDetails
					.getUserProfile());
		}
	}

}
