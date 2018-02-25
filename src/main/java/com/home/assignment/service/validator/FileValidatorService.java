package com.home.assignment.service.validator;

import org.springframework.stereotype.Component;

import com.home.assignment.domain.FileWithContent;
import com.home.assignment.exception.FileStorageException;
import com.home.assignment.service.DistributedMachineAllocator;

@Component
public class FileValidatorService {

	private static final int FILENAME_MAX_LENGTH = 64;
	public static final String FILE_OR_FILENAME_ARE_MISSING = "File and filename are mandatory\n";
	public static final String CHARACTER_NOT_ALLOWED = "Only alphanumeric, underscore and dash characters are allowed\n";
	public static final String FILENAME_LENGHT_TOO_BIG = "Filename length must be at most " + FILENAME_MAX_LENGTH
			+ " characters\n";
	public static final String FILENAME_DUPLICATE = "Filename already exists\n";
	public static final String NO_SUCH_FILENAME = "File does not exist\n";

	public boolean isValidForCreation(FileWithContent file, DistributedMachineAllocator allocator)
			throws FileStorageException {
		StringBuilder messageSB = new StringBuilder();
		String regex = "[\\w-]*";

		if (file == null || file.getFile() == null || file.getFile().getName() == null
				|| file.getFile().getName().isEmpty()) {
			messageSB.append(FILE_OR_FILENAME_ARE_MISSING);
		} else {
			if (file.getFile().getName().length() > FILENAME_MAX_LENGTH) {
				messageSB.append(FILENAME_LENGHT_TOO_BIG);
			}
			if (!file.getFile().getName().matches(regex)) {
				messageSB.append(CHARACTER_NOT_ALLOWED);
			}
			FileWithContent existing = allocator.get(file.getFile().getName());
			if (existing != null) {
				messageSB.append(FILENAME_DUPLICATE);
			}
		}

		if (messageSB.length() > 0) {
			throw new FileStorageException(messageSB.toString());
		}

		return true;
	}

	public boolean isValidForModification(FileWithContent file, DistributedMachineAllocator allocator)
			throws FileStorageException {
		StringBuilder messageSB = new StringBuilder();

		if (file == null || file.getFile() == null || file.getFile().getName() == null
				|| file.getFile().getName().isEmpty()) {
			messageSB.append(FILE_OR_FILENAME_ARE_MISSING);
		} else {
			FileWithContent existing = allocator.get(file.getFile().getName());
			if (existing == null) {
				messageSB.append(NO_SUCH_FILENAME);
			}
		}

		if (messageSB.length() > 0) {
			throw new FileStorageException(messageSB.toString());
		}

		return true;
	}

}
