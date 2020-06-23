package com.examples.ezoo.dao;

import static org.junit.Assert.*;
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
	
	@Test
	public void getAllFeedingSchedules_EmptyTable_ReturnEmptyList() throws Exception {
		// arrange
		List<FeedingSchedule> empty = new ArrayList<>();
		
		// this preparation is needed before any mock of a static method
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(c);
		
		when(c.createStatement()).thenReturn(stmt);
				
		when(stmt.executeQuery(anyString())).thenReturn(rs);
		
		// act
		List<FeedingSchedule> result = fsdi.getAllFeedingSchedules();
		
		// assert
		assertEquals(empty,result);
		
		
	}
	
	@Test
	public void getAllFeedingSchedules_TableWithOneRow_ReturnRecord() throws Exception {
		// arrange
		List<FeedingSchedule> oneRow = new ArrayList<>();
		FeedingSchedule fs = new FeedingSchedule(1,"8am", "Weekly on Sundays","Steak","Carnivores are super happy to eat steak.");
		oneRow.add(fs);
		
		// this preparation is needed before any mock of a static method
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(c);
		
		when(c.createStatement()).thenReturn(stmt);
				
		when(stmt.executeQuery(anyString())).thenReturn(rs);
		
		when(rs.next()).thenReturn(true).thenReturn(false);
		when(rs.getInt("schedule_id")).thenReturn(1);
		when(rs.getString("feeding_time")).thenReturn("8am");
		when(rs.getString("recurrence")).thenReturn("Weekly on Sundays");
		when(rs.getString("food")).thenReturn("Steak");
		when(rs.getString("notes")).thenReturn("Carnivores are super happy to eat steak.");
		
		// act
		
		List<FeedingSchedule> result = fsdi.getAllFeedingSchedules();
		
		// assert
		assertEquals(oneRow,result);
		
		
	}
	
	

	
}