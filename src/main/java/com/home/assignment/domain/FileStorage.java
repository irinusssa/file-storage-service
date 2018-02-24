package com.home.assignment.domain;

import java.util.HashMap;
import java.util.Map;

public class FileStorage {

	private Map<String, File> files = new HashMap<String, File>();

	public Map<String, File> getFiles() {
		return files;
	}

	public void setFiles(Map<String, File> files) {
		this.files = files;
	}

}
