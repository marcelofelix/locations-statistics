package com.vivareal.locations.statistic.builders;

import static com.vivareal.locations.statistic.model.Status.READY;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vivareal.locations.statistic.model.Report;
import com.vivareal.locations.statistic.model.Status;

public class ReportBuilder extends AbstractBuilder<Report> {

	public ReportBuilder date(Date value) {
		return set("date", value);
	}

	public ReportBuilder status(Status value) {
		return set("status", value);
	}

	public ReportBuilder id(long value) {
		return set("id", value);
	}

	public static ReportBuilder report() {
		return report(new Date());
	}

	public static ReportBuilder report(Date date) {
		return new ReportBuilder().date(date)
				.status(READY);
	}

	public static ReportBuilder report(String date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		try {
			return report(format.parse(date));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
