package com.home.assignment.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DistributedMachine {

	private Integer code;
	private int nbFiles = 0;
	private FileStorage storage = new FileStorage();
	private static final String ROOT = "\\temp\\fileStorage";

	public DistributedMachine(Integer machineCode) {
		this.code = machineCode;
	}

	public List<File> search(String searchWord) {
		List<File> result = new ArrayList<File>();
		for (File file : storage.values()) {
			if (file.matchesPattern(searchWord)) {
				result.add(file);
			}
		}
		return result.size() == 0 ? new ArrayList<File>(0):result;
	}

	public File put(String name, FileWithContent file, boolean isCreation) {
		file.getFile().setFullPath(findFullPath(name));
		File result = storage.put(name, file);
		if (isCreation) {
			nbFiles++;
		}
		return result;
	}

	private String findFullPath(String name) {
		int mask = BigInteger.valueOf(2L).pow(8).intValue() - 1;
		;
		int hash = name.hashCode();
		int parent = (hash >> 16) & mask;
		int folder = (hash >> 24) & mask;
		return new StringBuilder(ROOT).append(java.io.File.separator).append(String.format("%03d", parent))
				.append(java.io.File.separator).append(String.format("%03d", folder)).append(java.io.File.separator)
				.append(name).toString();
	}

	public FileWithContent get(String name) {
		FileWithContent result = storage.get(name);
		return result;
	}

	public Collection<File> values() {
		Collection<File> result = storage.values();
		return result;
	}

	public File remove(String name) {
		File result = storage.remove(name);
		if (result != null) {
			nbFiles--;
		}
		return result;
	}

	public Set<String> getFileNames() {
		Set<String> result = storage.getFiles().keySet();
		return result;
	}

	public int getNbFiles() {
		return nbFiles;
	}

	public Integer getCode() {
		return code;
	}

	public FileStorage getStorage() {
		return storage;
	}

}
