package com.home.assignment.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.home.assignment.domain.File;
import com.home.assignment.domain.FileWithContent;
import com.home.assignment.exception.FileStorageException;
import com.home.assignment.service.validator.FileValidatorService;

@Component
public class FileStorageService {

	@Autowired
	private FileValidatorService fileValidator;
	
	private DistributedMachineAllocator allocator = new DistributedMachineAllocator(16);

	@PostConstruct
	public void init() {
		MockDataGenerator.initData(allocator);
	}

	public List<File> enumerate(String searchWord) {
		return allocator.values();
	}

	public File create(FileWithContent file) throws FileStorageException {
		fileValidator.isValid(file, allocator);
		return allocator.put(file.getFile().getName(), file);
	}

	public FileWithContent read(String name) {
		return allocator.get(name);
	}

	public File update(String name, FileWithContent file) throws FileStorageException {
		if (file.getFile().getName() == null) {
			file.getFile().setName(name);
		}
		fileValidator.isValid(file, allocator);
		return allocator.put(name, file);
	}

	public void delete(String name) {
		allocator.remove(name);
	}

	public Long storageSize() {
		return allocator.getTotalNbFiles();
	}

}
