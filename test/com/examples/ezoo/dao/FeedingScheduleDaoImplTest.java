package com.examples.ezoo.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import com.examples.ezoo.model.FeedingSchedule;

import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FeedingScheduleDaoImplTest {
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Mock
	private DataSource ds;
	
	@Mock
	private Connection c;
	
	private FeedingSchedule fs;
	
	/**
	 * Helper function that sets up mock database and creates sample FeedingSchedule
	 */
	void setUp() {
		assertNotNull(ds);
		try {
			c = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		fs = new FeedingSchedule();
		fs.setSchedule_ID(1);
	}
	
	@Test
	@DisplayName("Sample test")
	void test() {
		assertNotNull(ds);
		try {
			c = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		assertTrue(true);
	}
	

	
}