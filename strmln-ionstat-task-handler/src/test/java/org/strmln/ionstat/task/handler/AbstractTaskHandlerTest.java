package org.strmln.ionstat.task.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.strmln.ionstat.model.Policy;
import org.strmln.ionstat.model.Role;
import org.strmln.ionstat.model.UserProfile;
import org.strmln.ionstat.model.context.UserProfileContext;
import org.strmln.ionstat.service.DeviceService;
import org.strmln.ionstat.task.handler.groovy.AbstractGroovyTest;
import org.strmln.ionstat.task.handler.groovy.annotations.TestUser;
import org.strmln.ionstat.task.handler.manager.TaskManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(locations = {
		"classpath:org/strmln/ionstat/task/handler/spring/applicationContext-test-mocks.xml",
		"classpath:org/strmln/ionstat/task/handler/spring/applicationContext-tasks.xml" }))
public abstract class AbstractTaskHandlerTest extends AbstractGroovyTest {

	private static final String TEST_ROLE_NAME = "TestRole";

	private static final String TEST_SUITE_ROOT = "src/test/resources/groovy/";


	@Rule
	public TestName _testName = new TestName();
	
	@Autowired
	private DeviceService _deviceService;

	@Autowired
	private TaskManager _taskManager;

	@After
	public void afterTest() {
		Mockito.validateMockitoUsage();

		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	@Before
	public void beforeTest() {
		prepareAnnotatedMethodModel(_testName.getMethodName());

		mockUserProfile();

		resetMocks();
	}

	private UserProfile initializeUserProfile() {
		String methodName = _testName.getMethodName();
		try {
			Class<?> clazz = this.getClass();
			Method testMethod = clazz.getMethod(methodName, new Class<?>[] {});

			String policy;
			if (testMethod.isAnnotationPresent(TestUser.class)) {
				TestUser methodAnnotation = testMethod.getAnnotation(TestUser.class);
				policy = methodAnnotation.policy();
			} else {
				policy = StringUtils.EMPTY;
			}

			Role role = new Role();
			role.setRoleName(TEST_ROLE_NAME);
			role.addPolicy(new Policy(policy));
			UserProfile userProfile = new UserProfile();
			userProfile.setRole(role);

			return userProfile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DeviceService getDeviceService() {
		return _deviceService;
	}

	protected TaskManager getTaskManager() {
		return _taskManager;
	}

	@Override
	protected String getTestSuiteRoot() {
		return TEST_SUITE_ROOT;
	}

	protected void mockUserProfile() {
		UserProfile userProfile = initializeUserProfile();

		UserProfileContext.setCurrentUserProfile(userProfile);

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
				userProfile.getRole().getPolicies().iterator().next().toString());
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userProfile, null,
				authorities);
		SecurityContextHolder.getContext().setAuthentication(authToken);

	}

	protected void resetMocks() {
	}

}
