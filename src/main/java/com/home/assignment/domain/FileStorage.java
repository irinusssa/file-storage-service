package com.home.assignment.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.home.assignment.io.FileSystemAccessor;

public class FileStorage {

	private Map<String, File> files = new HashMap<String, File>();
	private FileSystemAccessor fsAccess = new FileSystemAccessor();

	public File put(String name, FileWithContent file) {
		File result = fsAccess.storeToFileSystem(file);
		files.put(name, result);
		return result;
	}

	public FileWithContent get(String name) {
		File file = files.get(name);
		FileWithContent result = null;
		if (file != null) {
			result = fsAccess.retrieveFromFileSystem(file);
		}
		return result;
	}

	public Collection<File> values() {
		Collection<File> result = files.values();
		return result;
	}

	public File remove(String name) {
		File result = files.remove(name);
		fsAccess.deleteFileFromFileSystem(result);
		return result;
	}

	public Map<String, File> getFiles() {
		return files;
	}

	public void setFiles(Map<String, File> files) {
		this.files = files;
	}

}
