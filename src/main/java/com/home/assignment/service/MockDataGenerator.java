package com.home.assignment.service;

import java.util.UUID;

import com.home.assignment.domain.File;

public class MockDataGenerator {

	public static void initData(DistributedMachineAllocator all) {
		for (int i = 1; i <= 100; i++) {
			String fileName = UUID.randomUUID().toString();
			all.put(fileName, new File(fileName));
		}
	}

}
