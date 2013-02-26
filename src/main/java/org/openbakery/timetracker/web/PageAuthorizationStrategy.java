package org.openbakery.timetracker.web;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: rene
 * Date: 04.05.11
 */
public class PageAuthorizationStrategy extends AbstractPageAuthorizationStrategy {

    private static Logger log = LoggerFactory.getLogger(PageAuthorizationStrategy.class);

    protected <T extends Page> boolean isPageAuthorized(Class<T> pageClass)
	{
        RequireRole requireRole = pageClass.getAnnotation(RequireRole.class);

        log.debug("requireRole: {}", requireRole);
        if (requireRole == null) {
            return true;
        }

        User user = ((TimeTrackerSession)Session.get()).getUser();

		if (!user.isAuthenticated()) {
				return false;
		}

        return user.hasRole(requireRole.value());
	}

}
