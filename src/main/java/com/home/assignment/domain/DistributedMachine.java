package com.home.assignment.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DistributedMachine {

	private Integer machineCode;
	private int nbFiles = 0;
	private FileStorage storage = new FileStorage();

	public DistributedMachine(Integer machineCode) {
		this.machineCode = machineCode;
	}

	public File put(String name, File file) {
		File result = storage.getFiles().put(name, file);
		nbFiles++;
		return result;
	}

	public File get(String name) {
		File result = storage.getFiles().get(name);
		return result;
	}

	public List<File> values() {
		List<File> result = (ArrayList<File>) storage.getFiles().values();
		return result;
	}

	public File remove(String name) {
		File result = storage.getFiles().remove(name);
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
