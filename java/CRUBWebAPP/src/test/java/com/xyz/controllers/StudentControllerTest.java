package com.xyz.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.xyz.crudserviceclient.client.StudentServiceClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
@WebAppConfiguration
public class StudentControllerTest {
  private MockMvc mockMvc; 
  @InjectMocks
  private StudentController controller;
  @Mock
  private StudentServiceClient stuClient;
  @Autowired
  private WebApplicationContext webAppContext;
  
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    MockitoAnnotations.initMocks(this);
    Mockito.reset(stuClient);
  }
  
  @Test
  public void test() {
    
  }
}
