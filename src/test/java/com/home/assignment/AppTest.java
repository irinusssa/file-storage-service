package com.home.assignment;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.home.assignment.domain.DistributedMachine;
import com.home.assignment.domain.File;
import com.home.assignment.service.DistributedMachineAllocator;

public class AppTest {

	DistributedMachineAllocator allocator;

	@Before
	public void initStorage() {
		allocator = new DistributedMachineAllocator(16);

		String fileName = null;
		String fileName2 = "9c087d18-f9dd-4470-a4b3-526d08b655fb";
		allocator.put(fileName2, new File(fileName2));

		for (int i = 1; i <= 1000000; i++) {
			fileName = UUID.randomUUID().toString();
			allocator.put(fileName, new File(fileName));
		}

		allocator.remove(fileName);
		allocator.remove("67da1d7f-d82b-45e8-8f51-5f2c2d1cd43b");
	}

	// @Test
	public void displayFileDistribution() {
		allocator.displayDistribution();
	}

	// @Test
	public void displayFileNames() {
		allocator.displayValues();
	}

	// @Test
	public void testDistribution() {
		long sum = 0;
		for (Integer hash : allocator.getSlaves().keySet()) {
			sum += allocator.getSlaves().get(hash).getNbFiles();
		}

		assertEquals(allocator.getTotalNbFiles(), sum);
	}

	@Test
	public void testFileNumbering() {
		DistributedMachine machine;
		long sum = 0;
		for (Integer hash : allocator.getSlaves().keySet()) {
			machine = allocator.getSlaves().get(hash);
			sum += machine.getNbFiles();
			assertEquals(machine.getNbFiles(), machine.getStorage().getFiles().values().size());
		}

		assertEquals(allocator.getTotalNbFiles(), sum);
	}
}
