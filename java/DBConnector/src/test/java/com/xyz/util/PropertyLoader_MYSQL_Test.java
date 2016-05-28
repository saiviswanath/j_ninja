package com.xyz.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class PropertyLoader_MYSQL_Test {
	@Test
	public void testGetProperties() {
		Properties props = PropertyLoader.getProperties();
		assertNotNull(props);
		String dsProp = props
				.getProperty(PropertyConstants.JNDI_DATASOURCE_NAME);
		assertNotNull(dsProp);
		assertThat("java:comp/env/jdbc/collegeDB", IsEqual.equalTo(dsProp));
	}
}
