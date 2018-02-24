package com.home.assignment.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.home.assignment.domain.File;
import com.home.assignment.domain.FileWithContent;

@Component
public class FileStorageService {

	private DistributedMachineAllocator allocator = new DistributedMachineAllocator(16);

	@PostConstruct
	public void init() {
		MockDataGenerator.initData(allocator);
	}

	public List<File> enumerate(String searchWord) {
		return allocator.values();
	}

	public File create(FileWithContent file) {
		return allocator.put(file.getFile().getName(), file);
	}

	public FileWithContent read(String name) {
		return allocator.get(name);
	}

	public File update(String name, FileWithContent file) {
		return allocator.put(name, file);
	}

	public void delete(String name) {
		allocator.remove(name);
	}

	public Long storageSize() {
		return allocator.getTotalNbFiles();
	}

}
