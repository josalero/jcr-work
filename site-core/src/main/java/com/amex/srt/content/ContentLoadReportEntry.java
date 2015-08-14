package com.amex.srt.content;

public class ContentLoadReportEntry {

	private String contentItemPath;
	private String operation;
	private String outcome;

	public String getContentItemPath() {
		return contentItemPath;
	}

	public void setContentItemPath(String contentItemPath) {
		this.contentItemPath = contentItemPath;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
}
