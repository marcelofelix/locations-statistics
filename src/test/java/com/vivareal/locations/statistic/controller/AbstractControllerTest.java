package com.vivareal.locations.statistic.controller;

import java.io.File;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vivareal.locations.ApplicationTest;
import com.vivareal.locations.statistic.builders.Builders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Sql(value = "classpath:/data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class AbstractControllerTest {

	@Autowired
	private WebApplicationContext context;

	public MockMvc mvc;

	@Value("classpath:/files")
	private Resource testPath;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		Builders.clean();
	}

	public ResultActions perform(MockHttpServletRequestBuilder request) throws Exception {
		request.contentType(MediaType.APPLICATION_JSON);
		return mvc.perform(request);
	}

	protected void saveAll() {
		Builders.saveAll();

	}

	public File getFile(String name) throws Exception {
		for (File f : testPath.getFile().listFiles()) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		throw new RuntimeException("File not found " + name);
	}
}
