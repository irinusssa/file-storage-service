package com.home.assignment.service;

import java.util.UUID;

import com.home.assignment.domain.FileWithContent;

public class MockDataGenerator {

	public static void initData(DistributedMachineAllocator all) {
		FileWithContent file;
		String fileName;
		for (int i = 1; i <= 10; i++) {
			file = new FileWithContent();
			fileName = UUID.randomUUID().toString();
			file = FileWithContent.build(fileName, fileName.getBytes());
			all.put(fileName, file, true);
		}
	}

}
