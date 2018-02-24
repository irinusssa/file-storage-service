package com.home.assignment.domain;

public class File {
	private String name;
	private String fullPath;

	public File() {
	}

	public File(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String path) {
		this.fullPath = path;
	}

}
