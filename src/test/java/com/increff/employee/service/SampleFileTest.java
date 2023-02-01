package com.increff.employee.service;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

public class SampleFileTest {

	@Test
	public void testEmployeeFile() {
		InputStream is = null;
		is = SampleFileTest.class.getResourceAsStream("/com/increff/employee/employee.tsv");
		assertNotNull(is);
	}
	@Test
	public void testBrandFile() {
		InputStream is = null;
		is = SampleFileTest.class.getResourceAsStream("/com/increff/employee/brand.csv");
		assertNotNull(is);
	}
	@Test
	public void testProductFile() {
		InputStream is = null;
		is = SampleFileTest.class.getResourceAsStream("/com/increff/employee/product.csv");
		assertNotNull(is);
	}
	@Test
	public void testTemplateFile() {
		InputStream is = null;
		is = SampleFileTest.class.getResourceAsStream("/template.xsl");
		assertNotNull(is);
	}
}
