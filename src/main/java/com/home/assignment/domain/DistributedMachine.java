package com.home.assignment.domain;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

public class DistributedMachine {

	private Integer machineCode;
	private int nbFiles = 0;
	private FileStorage storage = new FileStorage();
	private static final String ROOT = "C:\\temp\\fileStorage";

	public DistributedMachine(Integer machineCode) {
		this.machineCode = machineCode;
	}

	public File put(String name, FileWithContent file) {
		file.getFile().setFullPath(findFullPath(name));
		if (file.getFile().getName() == null) {
			file.getFile().setName(name);
		}
		File result = storage.put(name, file);
		nbFiles++;
		return result;
	}

	private String findFullPath(String name) {
		int mask = BigInteger.valueOf(2L).pow(8).intValue() - 1;;
		int hash = name.hashCode();
		int firstDir = (hash >> 16) & mask;
		int secondDir = (hash >> 24) & mask;
		return new StringBuilder(ROOT).append(java.io.File.separator).append(String.format("%03d", firstDir))
				.append(java.io.File.separator).append(String.format("%03d", secondDir)).append(java.io.File.separator)
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

	public Integer getMachineCode() {
		return machineCode;
	}

	public FileStorage getStorage() {
		return storage;
	}

}
