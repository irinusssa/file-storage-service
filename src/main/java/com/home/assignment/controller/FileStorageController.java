package com.home.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.home.assignment.domain.File;
import com.home.assignment.domain.FileWithContent;
import com.home.assignment.exception.FileStorageException;
import com.home.assignment.service.FileStorageService;

@RestController
@RequestMapping("api/v1/")
public class FileStorageController {
	
	@Autowired
	private FileStorageService service;

	@RequestMapping(value = "files/find/{searchWord}", method = RequestMethod.GET)
	public List<File> enumerate(@PathVariable String searchWord) {
		System.out.println("***********************GET enumerate searchWord*******************************");
		return service.enumerate(searchWord);
	}

	@RequestMapping(value = "files", method = RequestMethod.POST)
	public File create(@RequestBody FileWithContent file) throws FileStorageException {
		System.out.println("***********************POST name*******************************");
		return service.create(file);
	}

	@RequestMapping(value = "files/{name}", method = RequestMethod.GET)
	public FileWithContent read(@PathVariable String name) {
		System.out.println("***********************GET read name*******************************");
		return service.read(name);
	}

	@RequestMapping(value = "files/{name}", method = RequestMethod.PUT)
	public File update(@RequestBody FileWithContent file, @PathVariable String name) throws FileStorageException {
		return service.update(name, file);
	}

	@RequestMapping(value = "files/{name}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String name) {
		service.delete(name);
	}
	
	@RequestMapping(value = "files/size", method = RequestMethod.GET)
	public long size() {
		return service.storageSize();
	}

}
