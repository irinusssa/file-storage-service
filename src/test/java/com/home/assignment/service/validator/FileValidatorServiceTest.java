package com.home.assignment.service.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.home.assignment.domain.FileWithContent;
import com.home.assignment.exception.FileStorageException;
import com.home.assignment.service.DistributedMachineAllocator;

public class FileValidatorServiceTest {

	DistributedMachineAllocator allocator;
	FileValidatorService validator = new FileValidatorService();

	@Before
	public void initStorage() {
		allocator = new DistributedMachineAllocator(16);

		String fileName = "67da1d7f-d82b-45e8-8f51-5f2c2d1cd43b";
		FileWithContent file = FileWithContent.build(fileName, "aaaa".getBytes());
		allocator.put(fileName, file);

		String fileName2 = "9c087d18-f9dd-4470-a4b3-526d08b655fb";
		FileWithContent file2 = FileWithContent.build(fileName2, "aaaa".getBytes());
		allocator.put(fileName2, file2);
	}

	@Test
	public void testFileValidCreation() throws FileStorageException {
		String filename = "0Very_legal-filen3me";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		assertEquals(true, validator.isValidForCreation(file, allocator));
	}
	
	@Test
	public void testFileValidModification() throws FileStorageException {
		String filename = "67da1d7f-d82b-45e8-8f51-5f2c2d1cd43b";
		FileWithContent file = FileWithContent.build(filename, "bbbb".getBytes());

		assertEquals(true, validator.isValidForModification(file, allocator));
	}

	@Test
	public void testFileNamePatternCreation_spaces() {
		String filename = "illegal filename _Contains spaces";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		try {
			validator.isValidForCreation(file, allocator);
			assertEquals(true, false);
		} catch (FileStorageException e) {
			assertEquals(FileValidatorService.CHARACTER_NOT_ALLOWED, e.getMessage());
		}
	}

	@Test
	public void testFileNamePatternCreation_otherIllegalChars() {
		String filename = "0110 ill.egal filename _Contains !# bad Stuff @";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		try {
			validator.isValidForCreation(file, allocator);
			assertEquals(true, false);
		} catch (FileStorageException e) {
			assertEquals(FileValidatorService.CHARACTER_NOT_ALLOWED, e.getMessage());
		}
	}

	@Test
	public void testFileNameLengthCreation_moreThanAllowed() {
		String filename = "0110Very_legal-filen3me_0123456789_0123456789_0123456789_0123456789_0123456789_0123456789";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		try {
			validator.isValidForCreation(file, allocator);
			assertEquals(true, false);
		} catch (FileStorageException e) {
			assertEquals(FileValidatorService.FILENAME_LENGHT_TOO_BIG, e.getMessage());
		}
	}

	@Test
	public void testFileNameCreation_missing() {
		String filename = "";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		try {
			validator.isValidForCreation(file, allocator);
			assertEquals(true, false);
		} catch (FileStorageException e) {
			assertEquals(FileValidatorService.FILE_OR_FILENAME_ARE_MISSING, e.getMessage());
		}
	}
	
	@Test
	public void testFileNameModification_missing() {
		String filename = "";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		try {
			validator.isValidForModification(file, allocator);
			assertEquals(true, false);
		} catch (FileStorageException e) {
			assertEquals(FileValidatorService.FILE_OR_FILENAME_ARE_MISSING, e.getMessage());
		}
	}

	@Test
	public void testFileNameCreation_alreadyExists() {
		String filename = "67da1d7f-d82b-45e8-8f51-5f2c2d1cd43b";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		try {
			validator.isValidForCreation(file, allocator);
			assertEquals(true, false);
		} catch (FileStorageException e) {
			assertEquals(FileValidatorService.FILENAME_DUPLICATE, e.getMessage());
		}
	}
	
	@Test
	public void testFileNameModification_doesNotExist() {
		String filename = "0110 ill.egal filename _Contains !# bad Stuff @";
		FileWithContent file = FileWithContent.build(filename, "aaaa".getBytes());

		try {
			validator.isValidForModification(file, allocator);
			assertEquals(true, false);
		} catch (FileStorageException e) {
			assertEquals(FileValidatorService.NO_SUCH_FILENAME, e.getMessage());
		}
	}

}
