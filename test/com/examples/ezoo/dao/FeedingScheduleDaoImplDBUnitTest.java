package com.examples.ezoo.dao;

import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import com.examples.ezoo.model.FeedingSchedule;


@RunWith(PowerMockRunner.class)
@PrepareForTest(DAOUtilities.class)
public class FeedingScheduleDaoImplDBUnitTest extends DataSourceBasedDBTestCase {
	
	private Connection connection;
	
	private FeedingScheduleDaoImpl fsdi = new FeedingScheduleDaoImpl();

	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
		dataSource.setUser("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.xml");
		return new FlatXmlDataSetBuilder().build(inputStream);
	}

	@Override
	protected DatabaseOperation getSetUpOperation() {
		return DatabaseOperation.REFRESH;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() {
		return DatabaseOperation.DELETE_ALL;
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		connection = getConnection().getConnection();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	
	@Test
	public void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
		IDataSet expectedDataSet = getDataSet();
		ITable expectedTable = expectedDataSet.getTable("animals");
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("animals");
		Assertion.assertEquals(expectedTable, actualTable);
	}
	

	
	@Test
	public void getAllFeedingSchedules_TestDataSet_ReturnList() throws Exception {

		FeedingSchedule fs1 = new FeedingSchedule(101,"8am", "Weekly on Sundays","Steak","Carnivores are super happy to eat steak.");
		FeedingSchedule fs2 = new FeedingSchedule(102,"10am", "daily","kibble",null);
		
		List<FeedingSchedule> expectedList = new ArrayList<>();
		expectedList.add(fs1);
		expectedList.add(fs2);

		// preparation for mocking static method
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		List<FeedingSchedule> result = fsdi.getAllFeedingSchedules();

		assertEquals(expectedList, result);

	}
	
	

}
