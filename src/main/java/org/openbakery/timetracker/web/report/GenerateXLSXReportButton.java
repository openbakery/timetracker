package org.openbakery.timetracker.web.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.poi.XlsxResourceStream;
import org.openbakery.timetracker.service.TimeEntryService;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.util.DurationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.HyperlinkEvent;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 26.02.13
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */
public class GenerateXLSXReportButton extends Button {
	private static Logger log = LoggerFactory.getLogger(GenerateCSVReportButton.class);

	private static final String SEPARATOR = ";";
	private static final String NEWLINE = System.getProperty("line.separator");

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DateHelper.DATE_PATTERN);
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(DateHelper.TIME_PATTERN);

	@SpringBean
	private TimeEntryService timeEntryService;

	private Report report;

	public GenerateXLSXReportButton(String id, Report report) {
		super(id, new ResourceModel(id));
		this.report = report;
	}

	@Override
	public void onSubmit() {
		User user = report.getUser();

		List<TimeEntry> timeEntryList = null;
		if (report.getCustomer() == null) {
			timeEntryList = timeEntryService.getTimeEntries(user, report.getBegin(), report.getEnd());
		} else {
			timeEntryList = timeEntryService.getTimeEntries(user, report.getBegin(), report.getEnd(), report.getCustomer());
		}
		if (log.isDebugEnabled()) {
			for (TimeEntry entry : timeEntryList) {
				log.debug("entry: {}", entry);
			}
		}

		XlsxResourceStream resourceStream = new XlsxResourceStream(createWorkbook(timeEntryList));
		ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(resourceStream);
		StringBuilder builder = new StringBuilder();
		builder.append("Report-");
		if (report.getCustomer() != null) {
			builder.append(report.getCustomer().getName());
			builder.append("-");
		}
		builder.append(DATE_FORMAT.format(report.getBegin()));
		builder.append("-");
		builder.append(DATE_FORMAT.format(report.getEnd()));
		builder.append(".xlsx");


		handler.setFileName(builder.toString());
		handler.setContentDisposition(ContentDisposition.ATTACHMENT);
		getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
	}

	private Cell newCellForRow(SXSSFWorkbook workbook, Row row) {
		short number =row.getLastCellNum();
		if (number < 0) {
			number = 0;
		}
		return row.createCell(number);
	}


	private Cell newCellForRow(SXSSFWorkbook workbook, Row row, String value) {
		Cell cell = newCellForRow(workbook, row);
		cell.setCellValue(value);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.cloneStyleFrom(cell.getCellStyle());
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		return cell;
	}

	private Cell newCellForRow(SXSSFWorkbook workbook, Row row, double value) {
		Cell cell = newCellForRow(workbook, row);
		log.debug("adding value {}", value);
		cell.setCellValue(value);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.cloneStyleFrom(cell.getCellStyle());
		DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		cell.setCellStyle(cellStyle);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC );
		return cell;
	}

	private Cell newCellForRow(SXSSFWorkbook workbook, Row row, int value) {
		Cell cell = newCellForRow(workbook, row);
		cell.setCellValue(value);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC );
		return cell;
	}

	private void setHyperlink(SXSSFWorkbook workbook, Cell cell, String value) {
		CreationHelper createHelper = workbook.getCreationHelper();
		Hyperlink hyperlink = createHelper.createHyperlink(Hyperlink.LINK_URL);
		hyperlink.setAddress(value);

		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(cell.getCellStyle());

		Font font = workbook.createFont();
		font.setUnderline(Font.U_SINGLE);
		font.setColor(IndexedColors.BLUE.getIndex());
		style.setFont(font);
		cell.setHyperlink(hyperlink);
		cell.setCellStyle(style);
	}

	private void setBold(SXSSFWorkbook workbook, Cell cell) {
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.cloneStyleFrom(cell.getCellStyle());
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	private SXSSFWorkbook createWorkbook(List<TimeEntry> entryList) {
		log.debug("create workbook for {} entries", entryList.size());
		SXSSFWorkbook workbook = new SXSSFWorkbook(entryList.size()+10);
		Sheet sheet = workbook.createSheet();

		sheet.setColumnWidth(0, 14*256);
		sheet.setColumnWidth(1, 8*256);
		sheet.setColumnWidth(2, 8*256);
		sheet.setColumnWidth(3, 8*256);
		sheet.setColumnWidth(4, 40*256);
		sheet.setColumnWidth(5, 8*256);
		sheet.setColumnWidth(6, 20*256);
		sheet.setColumnWidth(7, 14*256);
		sheet.setColumnWidth(8, 14*256);

		Row headerRow = sheet.createRow(0);

		Localizer localizer = getLocalizer();

		log.debug("create header row");


		newCellForRow(workbook, headerRow, localizer.getString("date", this));
		newCellForRow(workbook, headerRow, localizer.getString("begin", this));
		newCellForRow(workbook, headerRow, localizer.getString("end", this));
		newCellForRow(workbook, headerRow, localizer.getString("duration", this));
		newCellForRow(workbook, headerRow, localizer.getString("description", this));
		newCellForRow(workbook, headerRow, localizer.getString("issue", this));
		newCellForRow(workbook, headerRow, localizer.getString("project", this));
		newCellForRow(workbook, headerRow, localizer.getString("customer", this));
		newCellForRow(workbook, headerRow, localizer.getString("user", this));

		for (int i=0; i<headerRow.getLastCellNum(); i++) {
			setBold(workbook, headerRow.getCell(i));
		}

		for (TimeEntry entry : entryList) {
			log.debug("add entry row");
			Row row = sheet.createRow(sheet.getLastRowNum()+1);
			newCellForRow(workbook, row, DATE_FORMAT.format(entry.getBegin()));
			newCellForRow(workbook, row, TIME_FORMAT.format(entry.getBegin()));
			newCellForRow(workbook, row, TIME_FORMAT.format(entry.getEnd()));
			newCellForRow(workbook, row, DurationHelper.toTime(entry.getDuration()));
			Cell descriptionCell = newCellForRow(workbook, row, entry.getDescription());
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.cloneStyleFrom(descriptionCell.getCellStyle());
			cellStyle.setWrapText(true);
			descriptionCell.setCellStyle(cellStyle);
			Cell issueCell = newCellForRow(workbook, row, entry.getIssue());
			setHyperlink(workbook, issueCell, entry.getProject().getIssueTrackerURL() + entry.getIssue());
			newCellForRow(workbook, row, entry.getProject().getName());
			newCellForRow(workbook, row, entry.getProject().getCustomer().getName());
			newCellForRow(workbook, row, entry.getUser().getName());
		}

		Row row = sheet.createRow(sheet.getLastRowNum()+1);
		Cell sumCell = row.createCell(3);
		sumCell.setCellFormula("=SUM(D2:D" + (entryList.size()+1) + ")");
		setBold(workbook, sumCell);

		log.debug("done creating workbook");
		return workbook;
	}
}
