package com.examples.ezoo.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.examples.ezoo.model.FeedingSchedule;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PowerMockRunner.class)
//Initialize UtilityClass.class (the class that has a static method)
@PrepareForTest(DAOUtilities.class)
public class FeedingScheduleDaoImplTest {
	
	//@Rule
	//public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	//@Mock
	//private DAOUtilities util;
	
	@Mock
	private Connection c;
	
	@Mock
	private Statement stmt;
	
	@Mock
	private ResultSet rs;
	
	
	private FeedingScheduleDaoImpl fsdi = new FeedingScheduleDaoImpl();
	
	/**
	 * Helper function that sets up mock database and creates sample FeedingSchedule
	 */
	void setUp() {
		
		
		//fs = new FeedingSchedule(1,"8am", "Weekly on Sundays","Steak","Carnivores are super happy to eat steak.");

	}
	
	//@DisplayName("getAllFeedingSchedules() should return empty list when table is empty.")
	@Test
	public void getAllFeedingSchedules_EmptyTable_ReturnEmptyList() throws Exception {
		// arrange
		List<FeedingSchedule> empty = new ArrayList<>();
		
		
		
		
		// this preparation is needed before any mock of a static method
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(c);
		
		Connection c = mock(Connection.class);
		when(c.createStatement()).thenReturn(stmt);
				
		
		Statement stmt = mock(Statement.class);
		when(stmt.executeQuery(anyString())).thenReturn(rs);
		
		ResultSet rs = mock(ResultSet.class);
		when(rs.next()).thenReturn(false);
		
		
		// act
		List<FeedingSchedule> result = fsdi.getAllFeedingSchedules();
		
		// assert
		assertEquals(empty,result);
		
		
	}
	
	

	
}