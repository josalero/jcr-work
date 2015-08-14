package com.amex.srt.content;

import java.util.ArrayList;
import java.util.List;

public class ContentLoadReport {

	private String status;

	private List<ContentLoadReportEntry> successfulAddedEntries = new ArrayList<ContentLoadReportEntry>();
	private List<ContentLoadReportEntry> successfulUpdatedEntries = new ArrayList<ContentLoadReportEntry>();
	private List<ContentLoadReportEntry> successfulDeletedEntries = new ArrayList<ContentLoadReportEntry>();

	private List<ContentLoadReportEntry> failedEntries = new ArrayList<ContentLoadReportEntry>();

	private List<ContentLoadReportEntry> tileImageEntriesEntries = new ArrayList<ContentLoadReportEntry>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ContentLoadReportEntry> getSuccessfulAddedEntries() {
		return successfulAddedEntries;
	}

	public List<ContentLoadReportEntry> getSuccessfulUpdatedEntries() {
		return successfulUpdatedEntries;
	}

	public List<ContentLoadReportEntry> getSuccessfulDeletedEntries() {
		return successfulDeletedEntries;
	}

	public List<ContentLoadReportEntry> getFailedEntries() {
		return failedEntries;
	}

	public List<ContentLoadReportEntry> getTileImageEntriesEntries() {
		return tileImageEntriesEntries;
	}
}
