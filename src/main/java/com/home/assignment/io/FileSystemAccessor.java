package com.home.assignment.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.home.assignment.domain.File;
import com.home.assignment.domain.FileWithContent;

public class FileSystemAccessor {

	public FileWithContent retrieveFromFileSystem(File file) {
		FileWithContent result = new FileWithContent();
		result.setFile(file);
		try {
			result.setContent(Files.readAllBytes(Paths.get(file.getFullPath())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public File storeToFileSystem(FileWithContent content) {
		try {
			if (content.getContent() != null) {
				Path parentDir = Paths.get(content.getFile().getFullPath()).getParent();
				if (!Files.exists(parentDir)) {
					Files.createDirectories(parentDir);
				}
				Files.write(Paths.get(content.getFile().getFullPath()), content.getContent(), StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.getFile();
	}

	public void deleteFileFromFileSystem(File file) {
		try {
			if (file != null) {
				Files.deleteIfExists(Paths.get(file.getFullPath()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
