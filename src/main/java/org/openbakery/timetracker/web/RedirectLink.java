package org.openbakery.timetracker.web;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedirectLink<T extends Serializable> extends Link<T> {

	private static Logger log = LoggerFactory.getLogger(RedirectLink.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class<? extends WebPage> pageClass;

	public RedirectLink(String id, Class<? extends WebPage> pageClass) {
		super(id);
		this.pageClass = pageClass;
	}

	public RedirectLink(String id, Class<? extends WebPage> pageClass, T model) {
		super(id, new Model<T>(model));
		this.pageClass = pageClass;
	}

	@Override
	public void onClick() {
		if (getModelObject() != null) {
			Constructor<? extends WebPage> constructor;
			try {
				constructor = pageClass.getConstructor(PageParameters.class, getModelObject().getClass());
				WebPage webPage = constructor.newInstance(new PageParameters(), getModelObject());
				setResponsePage(webPage);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} else {
			setResponsePage(pageClass);
		}
	}
}
