package org.openbakery.timetracker;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.openbakery.timetracker.web.TimeTrackerSession;

public class TestWebApplication extends WebApplication {

	private Class<? extends WebPage> webpage;

	public TestWebApplication(Class<? extends WebPage> webpage) {
		this.webpage = webpage;
	}

	@Override
	public Class<? extends WebPage> getHomePage() {
		return webpage;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new TimeTrackerSession(request);
	}

}
