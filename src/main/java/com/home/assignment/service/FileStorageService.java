package com.home.assignment.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.home.assignment.domain.File;

@Component
public class FileStorageService {

	private DistributedMachineAllocator allocator = new DistributedMachineAllocator(16);

	public List<File> enumerate(String searchWord) {
		//MockDataGenerator.initData(allocator);
		return allocator.values();
	}

	public File create(File file) {
		return allocator.put(file.getName(), file);
	}

	public File read(String name) {
		return allocator.get(name);
	}

	public File update(String name, File file) {
		return allocator.put(name, file);
	}

	public void delete(String name) {
		allocator.remove(name);
	}

	public Long storageSize() {
		return allocator.getTotalNbFiles();
	}

}
