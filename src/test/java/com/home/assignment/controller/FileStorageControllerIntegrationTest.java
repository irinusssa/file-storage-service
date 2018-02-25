package com.home.assignment.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.home.assignment.FileStorageTestSuite;
import com.home.assignment.domain.File;
import com.home.assignment.domain.FileWithContent;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FileStorageControllerIntegrationTest extends FileStorageTestSuite {

	@Autowired
	private TestRestTemplate restTemplate;

	private static final String ROOT_URL = "/api/v1/";
	private String fileName = "9c087d18-f9dd-4470-a4b3-526d08b655fb";
	private String pattern = "-4470-";
	private String fileNameToDelete = "0eea2aea-eb81-4c3d-9fd1-673ebae3d7e2";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testEnumerate() throws IOException {
		ResponseEntity<List> response = restTemplate.getForEntity(ROOT_URL + "/files/find/" + pattern, List.class);

		List<File> result = (List<File>) response.getBody();
		
		System.out.println(result);

		//assertNotEquals(result.size(), 0);
	}

	@Test
	public void testRead() throws IOException {
		ResponseEntity<FileWithContent> response = restTemplate.getForEntity(ROOT_URL + "/files/" + fileName,
				FileWithContent.class);

		// allocator.displayValues();

		FileWithContent result = (FileWithContent) response.getBody();

		assertEquals(result.getFile().getName(), fileName);
	}

	@Test
	public void testCreate() throws IOException {
		String tempName = "newFile";
		FileWithContent toSave = FileWithContent.build(tempName, "222".getBytes());
		ResponseEntity<File> response = restTemplate.postForEntity(ROOT_URL + "/files/", toSave, File.class);

		// allocator.displayValues();

		File result = (File) response.getBody();

		assertEquals(result.getName(), tempName);
	}

	@Test
	public void testUpdate() throws IOException {
		ResponseEntity<FileWithContent> response = restTemplate.getForEntity(ROOT_URL + "/files/" + fileName,
				FileWithContent.class);
		FileWithContent oldFile = (FileWithContent) response.getBody();

		FileWithContent toUpdate = FileWithContent.build(fileName, "222".getBytes());
		restTemplate.put(ROOT_URL + "/files/" + fileName, toUpdate);

		response = restTemplate.getForEntity(ROOT_URL + "/files/" + fileName, FileWithContent.class);
		FileWithContent newFile = (FileWithContent) response.getBody();

		assertNotNull(oldFile);
		assertNotNull(newFile);
		assertNotEquals(oldFile, newFile);
	}

	@Test
	public void testDelete() throws IOException {
		ResponseEntity<FileWithContent> response = restTemplate.getForEntity(ROOT_URL + "/files/" + fileNameToDelete,
				FileWithContent.class);
		FileWithContent file = (FileWithContent) response.getBody();
		assertNotNull(file);

		restTemplate.delete(ROOT_URL + "/files/" + fileNameToDelete);

		response = restTemplate.getForEntity(ROOT_URL + "/files/" + fileNameToDelete, FileWithContent.class);
		file = (FileWithContent) response.getBody();
		assertNull(file);
	}

	@Test
	public void testSize() throws IOException {
		ResponseEntity<Long> response = restTemplate.getForEntity(ROOT_URL + "/files/size/", Long.class);

		Long result = (Long) response.getBody();

		assertNotNull(result);
		;
	}

}
