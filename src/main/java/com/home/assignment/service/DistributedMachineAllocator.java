package com.home.assignment.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.home.assignment.domain.DistributedMachine;
import com.home.assignment.domain.File;
import com.home.assignment.domain.FileWithContent;

/**
 * 
 * Max number of files that can be stored = (2^nbBitsMask - 1) * (2^32 - 1)</br>
 * (2^nbBitsMask - 1) - number of keys used in the main map, this will depend on
 * the nbBitsMask constructor parameter</br>
 * (2^32 - 1) - max number of values a Map can hold </br>
 * </br>
 * If nbBitsMask = 32, then the max size will be 18.446.744.065.119.617.025,
 * order of 10^19 </br>
 * If nbBitsMask = 16, then the max size will be 281.470.681.677.825, order of
 * 10^14
 *
 */
public class DistributedMachineAllocator {

	private int mask;
	private long totalNbFiles = 0;
	private Map<Integer, DistributedMachine> slaves = new HashMap<Integer, DistributedMachine>();

	public DistributedMachineAllocator(int nbBitsMask) {
		mask = BigInteger.valueOf(2L).pow(nbBitsMask).intValue() - 1;
	}

	public File put(String name, FileWithContent file) {
		int hash = name.hashCode();
		int key = hash & mask;
		File result = null;
		DistributedMachine machine = slaves.get(key);
		if (machine == null) {
			machine = new DistributedMachine(key);
			slaves.put(key, machine);
		}
		result = machine.put(name, file);
		totalNbFiles++;
		return result;
	}

	public FileWithContent get(String name) {
		int hash = name.hashCode();
		int key = hash & mask;
		DistributedMachine machine = slaves.get(key);
		FileWithContent result = null;
		if (machine != null) {
			result = machine.get(name);
		}
		return result;
	}

	public List<File> values() {
		ArrayList<File> result = new ArrayList<File>();
		DistributedMachine machine = null;
		for (Integer hash : slaves.keySet()) {
			machine = slaves.get(hash);
			for (String fileName : machine.getFileNames()) {
				result.add(machine.get(fileName).getFile());
			}
		}
		return result;
	}

	public File remove(String name) {
		int hash = name.hashCode();
		int key = hash & mask;
		DistributedMachine machine = slaves.get(key);
		File result = null;
		if (machine != null) {
			result = machine.remove(name);
			if (machine.getNbFiles() == 0) {
				slaves.remove(key);
			}
		}		
		if (result != null) {
			totalNbFiles--;
		}
		return result;
	}

	public void displayDistribution() {
		DistributedMachine machine = null;
		for (Integer hash : slaves.keySet()) {
			machine = slaves.get(hash);
			System.out.println(hash + " >> " + machine.getNbFiles());
		}
	}

	public void displayValues() {
		DistributedMachine machine = null;
		for (Integer hash : slaves.keySet()) {
			System.out.println(hash);
			machine = slaves.get(hash);
			for (File file : machine.values()) {
				System.out.println("\t" + file.getFullPath());
			}
		}
	}

	public long getTotalNbFiles() {
		return totalNbFiles;
	}

	public Map<Integer, DistributedMachine> getSlaves() {
		return slaves;
	}

}
