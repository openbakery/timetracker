package org.openbakery.timetracker.web.bean;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;

public class MenuItem implements Serializable, Comparable<MenuItem> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String caption;

	private Class<? extends WebPage> destination;

	private int weight;

	private String mount;

    private String cssId;

	public MenuItem(String caption, Class<? extends WebPage> destination, String mount) {
		this(caption, destination, mount, 0);
	}

    public MenuItem(String caption, Class<? extends WebPage> destination, String mount, int weight) {
        this(caption, destination, mount, weight, null);
    }

    public MenuItem(String caption, Class<? extends WebPage> destination, String mount, int weight, String cssId) {
        this.caption = caption;
        this.destination = destination;
        this.weight = weight;
        this.mount = mount;
        this.cssId = cssId;
    }

	public String getCaption() {
		return caption;
	}

	public Class<? extends WebPage> getDestination() {
		return destination;
	}


	public String getMount() {
		return mount;
	}

    public String getCssId() {
        return cssId;
    }

    public int compareTo(MenuItem o) {
		if (o != null) {
			return weight - o.weight;
		}
		return -1;
	}


}
