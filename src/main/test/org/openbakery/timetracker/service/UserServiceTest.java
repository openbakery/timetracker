package org.openbakery.timetracker.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.persistence.Persistence;
import org.openbakery.timetracker.util.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

public class UserServiceTest {

	@Test
	public void createDefaultUser() {

		final Persistence persistence = mock(Persistence.class);

		when(persistence.querySingle("Select count(user.id) from User user", Number.class)).thenReturn(Long.valueOf(0));

		DefaultUserService userService = new DefaultUserService();
		ReflectionTestUtils.setField(userService, "persistence", persistence);

		userService.initialize();

		User user = new User();
		user.setName("admin");
		user.setPassword(PasswordEncoder.encode("admin"));
		user.setRole(Role.ADMINISTRATOR);
		verify(persistence).store(user);

	}

	@Test
	public void hasUsers() {

		final Persistence persistence = mock(Persistence.class);

		when(persistence.querySingle("Select count(user.id) from User user", Number.class)).thenReturn(Long.valueOf(10));

		DefaultUserService userService = new DefaultUserService();
		ReflectionTestUtils.setField(userService, "persistence", persistence);

		userService.initialize();

		verify(persistence, never()).store(any(User.class));

	}
}
