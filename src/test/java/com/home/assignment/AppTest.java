package com.home.assignment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.home.assignment.domain.DistributedMachine;

public class AppTest extends FileStorageTestSuite {

	@Test
	public void displayFileDistribution() {
		allocator.displayDistribution();
	}

	@Test
	public void displayFileNames() {
		allocator.displayValues();
	}

	@Test
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
			/*if (machine.getNbFiles() != machine.getStorage().getFiles().values().size()) {
				System.out.println("hash = " + hash + "; nbFiles = " + machine.getNbFiles() + "; actual_values = "
						+ machine.getStorage().getFiles().values().size());
				allocator.displayValues();
				return;
			}*/
			assertEquals(machine.getNbFiles(), machine.getStorage().getFiles().values().size());
		}

		assertEquals(allocator.getTotalNbFiles(), sum);
	}
}
