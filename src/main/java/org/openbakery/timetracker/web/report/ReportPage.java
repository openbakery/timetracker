package org.openbakery.timetracker.web.report;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.CustomerService;
import org.openbakery.timetracker.service.UserService;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.web.RendererHelper;
import org.openbakery.timetracker.web.TimeTrackerPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * User: rene
 * Date: 06.05.11
 */
@RequireRole(value= Role.USER)
public class ReportPage extends TimeTrackerPage {
	private static Logger log = LoggerFactory.getLogger(ReportPage.class);

	private static final long serialVersionUID = 1L;

    @SpringBean
    private CustomerService customerService;


    @SpringBean
    private UserService userService;


    public ReportPage() {
        this(new Report());
    }


    public ReportPage(Report report) {
        super();

        Form<Report> form = new Form<Report>("form");
        add(form);

        List<User> userList;
        if (getSession().getUser().getRole() == Role.ADMINISTRATOR)
        {
            userList = userService.getAllActiveUsers();
        } else {
            userList = Collections.singletonList(getSession().getUser());
        }


        DropDownChoice<User> userChoice = new DropDownChoice<User>("user", new PropertyModel<User>(report, "User"), userList, RendererHelper.createUserRenderer());
        form.add(userChoice);
        if (userList.size() == 1)
        {
            report.setUser(userList.get(0));
        }

        DateTextField beginTextField = DateTextField.forDatePattern("begin", new PropertyModel<Date>(report, "begin"), DateHelper.DATE_PATTERN);
        beginTextField.setRequired(true);
        form.add(beginTextField);
        DateTextField endTextField = DateTextField.forDatePattern("end", new PropertyModel<Date>(report, "end"), DateHelper.DATE_PATTERN);
        form.add(endTextField);
        endTextField.setRequired(true);


        final List<Customer> customers = customerService.getAllActiveCustomers();

        DropDownChoice<Customer> customerChoice = new DropDownChoice<Customer>("customer", new PropertyModel<Customer>(report, "customer"), customers, RendererHelper.createCustomerRenderer());
        form.add(customerChoice);



			form.add(new GenerateCSVReportButton("generateCSV", report));
			form.add(new GenerateXLSXReportButton("generateXLSX", report));
    }


    @Override
    public String getPageTitle() {
        return "reportPage.title";
    }
}
