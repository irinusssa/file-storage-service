package com.home.assignment;

import java.util.UUID;

import org.junit.Before;

import com.home.assignment.domain.FileWithContent;
import com.home.assignment.service.DistributedMachineAllocator;

public class FileStorageTestSuite {
	
	public DistributedMachineAllocator allocator = DistributedMachineAllocator.getInstance();
	public Long initialStorageSize;
	private static boolean initDone = false;
	
	@Before
	public void initStorage() {
		if (FileStorageTestSuite.initDone == false) {
			String fileName = "67da1d7f-d82b-45e8-8f51-5f2c2d1cd43b";
			FileWithContent file = FileWithContent.build(fileName, "aaaa".getBytes());
			allocator.put(fileName, file, true);

			String fileName2 = "9c087d18-f9dd-4470-a4b3-526d08b655fb";
			FileWithContent file2 = FileWithContent.build(fileName2, "aaaa".getBytes());
			allocator.put(fileName2, file2, true);

			int testBatch = 10; /* change to 1000000 to better test the distribution */
			String fileName3 = null;
			for (int i = 1; i <= testBatch; i++) {
				fileName3 = UUID.randomUUID().toString();
				FileWithContent file3 = FileWithContent.build(fileName3, fileName3.getBytes());
				allocator.put(fileName3, file3, true);
			}
			
			String fileName4 = "0eea2aea-eb81-4c3d-9fd1-673ebae3d7e2";
			FileWithContent file4 = FileWithContent.build(fileName4, "aaaa".getBytes());
			allocator.put(fileName4, file4, true);

			allocator.remove(fileName3);
			
			initialStorageSize = allocator.getTotalNbFiles();
			FileStorageTestSuite.initDone = true;
		}
	}

}
