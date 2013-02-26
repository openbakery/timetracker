package org.openbakery.timetracker.poi;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 26.02.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class XlsxResourceStream extends AbstractResourceStream
{
	/** */
	private static final long serialVersionUID = 1L;
	private transient SXSSFWorkbook workbook;
	private transient InputStream inputStream;

	public XlsxResourceStream(SXSSFWorkbook workbook)
	{
		this.workbook = workbook;
	}

	public InputStream getInputStream() throws ResourceStreamNotFoundException
	{
		if (inputStream == null)
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try
			{
				workbook.write(out);
				out.close();
				workbook.dispose();
				inputStream = new ByteArrayInputStream(out.toByteArray());
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return inputStream;
	}

	public void close() throws IOException
	{
		if (inputStream != null)
		{
			inputStream.close();
			inputStream = null;
		}
	}

	@Override
	public String getContentType()
	{
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	}

	@Override
	public Time lastModifiedTime()
	{
		return Time.now();
	}
}
