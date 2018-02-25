package com.home.assignment.domain;

public class FileWithContent {

	private File file;
	private byte[] content;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public static FileWithContent build(String name, byte[] content) {
		FileWithContent result = new FileWithContent();
		result.setContent(content);
		result.setFile(new File(name));
		return result;
	}
}
