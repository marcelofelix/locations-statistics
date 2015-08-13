package com.vivareal.locations.statistic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class Report {

	@Id
	private String id;

	@NotNull
	private Date date = new Date();

	@NotNull
	private Status status;

	private List<Node> result = new ArrayList<Node>();

	public String getId() {
		return id;
	}

	public List<Node> getResult() {
		return result;
	}

	public void setResult(List<Node> result) {
		this.result = result;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getFileName() {
		return "report-" + id + ".csv";
	}

	public String getFileZipName() {
		return "report-" + id + ".zip";
	}

	public boolean isReady() {
		return Status.READY.equals(status);
	}

	public void compareWith(Report last) {
		result.forEach(n -> {
			Optional<Node> node = last.result.stream().filter(n2 -> n.getName().equals(n2.getName())).findFirst();
			if (node.isPresent()) {
				n.compareWith(node.get());
			}
		});

	}

	@Override
	public String toString() {
		return date.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
