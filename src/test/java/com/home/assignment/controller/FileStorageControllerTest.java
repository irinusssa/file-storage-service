package com.home.assignment.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.home.assignment.domain.File;
import com.home.assignment.domain.FileWithContent;
import com.home.assignment.service.FileStorageService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FileStorageController.class, secure = false)
public class FileStorageControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FileStorageService storageService;

	private File mockFile = new File("any_Fil3");
	private FileWithContent mockFileContent = FileWithContent.build("any_Fil3", null);
	private List<File> mockFiles = new ArrayList<File>(1);

	@Before
	public void init() {
		mockFiles.add(mockFile);
	}

	@Test
	public void testRead_findsFile() throws Exception {

		Mockito.when(storageService.read(Mockito.anyString())).thenReturn(mockFileContent);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/files/aaa")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{file:{name:any_Fil3}}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testEnumerate() throws Exception {

		Mockito.when(storageService.enumerate((Mockito.anyString()))).thenReturn(mockFiles);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/files/find/aaa")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{name:any_Fil3}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreate() throws Exception {
		String jsonIn = "{\"file\":{\"name\":\"filecreatedfrompostmanclient\"}, \"content\":\"asdadsadasda\"}";

		Mockito.when(storageService.create(Mockito.any(FileWithContent.class))).thenReturn(mockFile);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/files/").accept(MediaType.APPLICATION_JSON)
				.content(jsonIn).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value() == response.getStatus(), true);
	}

}
