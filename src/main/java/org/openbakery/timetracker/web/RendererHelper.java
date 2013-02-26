package org.openbakery.timetracker.web;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.User;

/**
 * User: rene
 * Date: 06.05.11
 */
public class RendererHelper {


	public static IChoiceRenderer<Customer> createCustomerRenderer() {
		return new IChoiceRenderer<Customer>() {
				private static final long serialVersionUID = 1L;

				public String getDisplayValue(Customer customer) {
					return customer.getName();
				}

				public String getIdValue(Customer customer, int index) {
					return Integer.toString(customer.getId());
				}
			};
	}

    public static IChoiceRenderer<User> createUserRenderer() {
        return new IChoiceRenderer<User>() {
            private static final long serialVersionUID = 1L;

            public String getDisplayValue(User user) {
                return user.getName();
            }

            public String getIdValue(User user, int index) {
                return Integer.toString(user.getId());
            }
        };
    }
}
