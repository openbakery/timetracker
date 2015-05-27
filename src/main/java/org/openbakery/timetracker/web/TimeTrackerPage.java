package org.openbakery.timetracker.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.web.bean.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class TimeTrackerPage extends WebPage {

	private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(TimeTrackerPage.class);

	public TimeTrackerPage() {
		super();
		init();
	}

	public TimeTrackerPage(PageParameters parameters) {
		super(parameters);
		init();
	}

	private void init() {
		add(new Label("pageTitle", new ResourceModel(getPageTitle())));

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);

		if (!getPageParameters().get("hideMenu").isEmpty()) {
			add(new RepeatingView("menu"));
			return;
		}

		add(createMenu());

	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
  }

		private RepeatingView createMenu() {
		RepeatingView menu = new RepeatingView("menu");

		for (MenuItem item : getSession().getMenuItems()) {

			User user = getSession().getUser();

			if (!user.isAuthenticated()) {
				continue;
			}

			RequireRole requireRole = item.getDestination().getAnnotation(RequireRole.class);
			log.debug("require Role for Page {} is {}", item.getDestination(), requireRole == null ? "null" : requireRole.value());
			if (requireRole != null) {
				if (!user.hasRole(requireRole.value())) {
					continue;
				}
			}

			WebMarkupContainer container = new WebMarkupContainer(menu.newChildId());
			menu.add(container);
			BookmarkablePageLink link = new BookmarkablePageLink("link", item.getDestination());
			container.add(link);
			link.add(new Label("caption", new StringResourceModel(item.getCaption(), this, new Model(getSession()))));


			if (item.getCssId() != null) {
				link.add(AttributeModifier.replace("id", item.getCssId()));
			}

			if (item.getDestination() == getClass()) {
				container.add(new AttributeModifier("class", new AbstractReadOnlyModel() {
					public Object getObject() {
						return "active";
					}
				}));

			}
		}
		return menu;

	}

	@Override
	public TimeTrackerSession getSession() {
		return (TimeTrackerSession) super.getSession();
	}

	public abstract String getPageTitle();


}
