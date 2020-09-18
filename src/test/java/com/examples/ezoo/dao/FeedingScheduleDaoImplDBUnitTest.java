package com.examples.ezoo.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
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

import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.FeedingSchedule;


@RunWith(PowerMockRunner.class)
@PrepareForTest(DAOUtilities.class)
public class FeedingScheduleDaoImplDBUnitTest extends DataSourceBasedDBTestCase {

	private Connection connection;

	private FeedingScheduleDaoImpl fsdi = new FeedingScheduleDaoImpl();

	
	@Override
	protected DatabaseOperation getSetUpOperation() {
		return DatabaseOperation.REFRESH;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() {
		return DatabaseOperation.DELETE_ALL;
	}
	
	/*
	public void FeedingSheduleDaoImplDBUnitTest(String name) {
		//super(name);
		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:default");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "schema.sql");
		
	}
	*/
	
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
		dataSource.setUser("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}

	protected IDataSet getDataSet() throws Exception {
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.xml");
		return new FlatXmlDataSetBuilder().build(inputStream);
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

		FeedingSchedule fs1 = new FeedingSchedule(101, "8am", "Weekly on Sundays", "Steak",
				"Carnivores are super happy to eat steak.");
		FeedingSchedule fs2 = new FeedingSchedule(102, "10am", "daily", "kibble", null);
		FeedingSchedule fs3 = new FeedingSchedule(103, "1pm", "daily", "Backyard Basics",
				"Helps chickens survive even though they prefer grubs and scratch.");

		List<FeedingSchedule> expectedList = new ArrayList<>();
		expectedList.add(fs1);
		expectedList.add(fs2);
		expectedList.add(fs3);

		// preparation for mocking static method
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		List<FeedingSchedule> result = fsdi.getAllFeedingSchedules();

		assertEquals(expectedList, result);

	}

	@Test
	public void saveFeedingSchedule_feedingScheduleAlreadyInDatabase_ThrowsException() throws Exception {

		FeedingSchedule fs = new FeedingSchedule(103, "1pm", "daily", "Backyard Basics",
				"Helps chickens survive even though they prefer grubs and scratch.");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		Throwable thrown = catchThrowable(() -> {
			fsdi.saveFeedingSchedule(fs);
		});

		assertThat(thrown).isInstanceOf(Exception.class).hasMessageMatching(".*Insert.*fail.*");

	}

	@Test
	public void saveFeedingSchedule_feedingScheduleNotAlreadyInDatabase_ThrowsException() throws Exception {

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("afterInsert.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedTable = expectedDataSet.getTable("feeding_schedules");

		FeedingSchedule fs = new FeedingSchedule(104, "5pm", "daily", "Hay", "Elephants love to eat hay.");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		fsdi.saveFeedingSchedule(fs);

		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("feeding_schedules");

		Assertion.assertEquals(expectedTable, actualTable);

	}

	@Test
	public void deleteFeedingSchedule_FeedingScheduleExistsInDatabaseAndNotesFieldIsNull_RecordDeleted()
			throws Exception {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("afterDelete.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedTable = expectedDataSet.getTable("feeding_schedules");

		FeedingSchedule fs = new FeedingSchedule(102, "10am", "daily", "kibble", null);

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		fsdi.deleteFeedingSchedule(fs);

		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("feeding_schedules");

		Assertion.assertEquals(expectedTable, actualTable);

	}
	
	@Test
	public void deleteFeedingSchedule_AnimalAssociatedWithFeedingSchedule_ReferenceDeletedinAnimalsTable()
			throws Exception {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("animalsAfterDelete.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedTable = expectedDataSet.getTable("animals");

		FeedingSchedule fs = new FeedingSchedule(103, "1pm", "daily", "Backyard Basics",
				"Helps chickens survive even though they prefer grubs and scratch.");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		fsdi.deleteFeedingSchedule(fs);

		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("animals");

		Assertion.assertEquals(expectedTable, actualTable);

	}

	@Test
	public void deleteFeedingSchedule_FeedingScheduleExistsInDatabaseAndNotesFieldIsNotNull_RecordDeleted()
			throws Exception {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("afterDelete2.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedTable = expectedDataSet.getTable("feeding_schedules");

		FeedingSchedule fs = new FeedingSchedule(103, "1pm", "daily", "Backyard Basics",
				"Helps chickens survive even though they prefer grubs and scratch.");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		fsdi.deleteFeedingSchedule(fs);

		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("feeding_schedules");

		Assertion.assertEquals(expectedTable, actualTable);

	}

	@Test
	public void deleteFeedingSchedule_FeedingScheduleNotInDatabase_ThrowsException() throws Exception {
		FeedingSchedule fs = new FeedingSchedule(105, "11am", "daily", "kibble", null);

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		Throwable thrown = catchThrowable(() -> {
			fsdi.deleteFeedingSchedule(fs);
		});

		assertThat(thrown).isInstanceOf(Exception.class).hasMessageMatching(".*Delete.*fail.*");

	}

	@Test
	public void getFeedingSchedule_givenAnimalWithValidFeedingSchedule_ReturnsFeedingSchedule() throws Exception {
		FeedingSchedule expectedFeedingSchedule = new FeedingSchedule(103, "1pm", "daily", "Backyard Basics",
				"Helps chickens survive even though they prefer grubs and scratch.");

		Animal a = new Animal(2L, "Toosty", "Animalia", "Chordata", "Aves", "Galliformes", "Phasianidae", "Gallus",
				"G. gallus", 30.00d, 3.00d, "Bird (Domestic)", "Healthy");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		FeedingSchedule actualFeedingSchedule = fsdi.getFeedingSchedule(a);

		assertThat(actualFeedingSchedule).isEqualToComparingFieldByField(expectedFeedingSchedule);

	}
	
	@Test
	public void getFeedingSchedule_givenAnimalNotInDatabase_ThrowsException() throws Exception {

		Animal a = new Animal(5L, "Toosty", "Animalia", "Chordata", "Aves", "Galliformes", "Phasianidae", "Gallus",
				"G. gallus", 30.00d, 3.00d, "Bird (Domestic)", "Healthy");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		Throwable thrown = catchThrowable(() -> {
			fsdi.getFeedingSchedule(a);
		});
		
		assertThat(thrown).isInstanceOf(Exception.class).hasMessageMatching(".*not.*feeding.*schedule.*");
		

	}

	@Test
	public void getFeedingSchedule_givenAnimalWithNoFeedingSchedule_ReturnsNull() throws Exception {
		FeedingSchedule expectedFeedingSchedule = null;

		Animal a = new Animal(3L, "Chipmunk", "Animalia", "Chordata", "Aves", "Galliformes", "Phasianidae", "Gallus",
				"G. gallus", 30.00d, 3.00d, "Bird (Domestic)", "Dead");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		FeedingSchedule actualFeedingSchedule = fsdi.getFeedingSchedule(a);

		assertEquals(expectedFeedingSchedule, actualFeedingSchedule);
	}
	
	
	@Test
	public void unassignFeedingSchedule_givenAnimalWithFeedingSchedule_ChangesFeedingScheduleToNull() throws Exception {
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("afterUnassign.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedTable = expectedDataSet.getTable("animals");

		Animal a = new Animal(2L, "Toosty", "Animalia", "Chordata", "Aves", "Galliformes", "Phasianidae", "Gallus",
				"G. gallus", 30.00d, 3.00d, "Bird (Domestic)", "Healthy");
		
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);
		
		
		fsdi.unassignFeedingSchedule(a);
		
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("animals");
		
		Assertion.assertEquals(expectedTable, actualTable);

		
	}
	
	@Test
	public void unassignFeedingSchedule_givenAnimalWithNoFeedingSchedule_NoChange() throws Exception {
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedTable = expectedDataSet.getTable("animals");

		Animal a = new Animal(3L, "Chipmunk", "Animalia", "Chordata", "Aves", "Galliformes", "Phasianidae", "Gallus",
				"G. gallus", 30.00d, 3.00d, "Bird (Domestic)", "Dead");
		
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);
		
		
		fsdi.unassignFeedingSchedule(a);
		
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("animals");
		
		Assertion.assertEquals(expectedTable, actualTable);

		
	}
	
	@Test
	public void assignFeedingSchedule_givenAnimalWithNoFeedingScheduleAndScheduleAlreadyInDatabase_AssignFeedingScheduleToAnimal() throws Exception {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("afterAssign.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedAnimalsTable = expectedDataSet.getTable("animals");
		ITable expectedFeedingSchedulesTable = expectedDataSet.getTable("feeding_schedules");
		
		Animal a = new Animal(3L, "Chipmunk", "Animalia", "Chordata", "Aves", "Galliformes", "Phasianidae", "Gallus",
				"G. gallus", 30.00d, 3.00d, "Bird (Domestic)", "Dead");
		FeedingSchedule fs = new FeedingSchedule(103, "1pm", "daily", "Backyard Basics",
				"Helps chickens survive even though they prefer grubs and scratch.");
		
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);
		
		fsdi.assignFeedingSchedule(a,fs);
		
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualAnimalsTable = databaseDataSet.getTable("animals");
		ITable actualFeedingSchedulesTable = databaseDataSet.getTable("feeding_schedules");
		
		Assertion.assertEquals(expectedAnimalsTable, actualAnimalsTable);
		Assertion.assertEquals(expectedFeedingSchedulesTable, actualFeedingSchedulesTable);
		
		
	}
	
	@Test
	public void getFeedingSchedule_givenValidID_ReturnRecord() throws Exception {
		FeedingSchedule expectedFeedingSchedule = new FeedingSchedule(103, "1pm", "daily", "Backyard Basics",
				"Helps chickens survive even though they prefer grubs and scratch.");

		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);

		FeedingSchedule actualFeedingSchedule = fsdi.getFeedingSchedule(103);

		assertEquals(expectedFeedingSchedule, actualFeedingSchedule);
	}


	/*
	@Test
	public void assignFeedingSchedule_givenAnimalWithNoFeedingScheduleAndScheduleNotAlreadyInDatabase_SaveFeedingScheduleAndAssignToAnimal() throws Exception {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("afterAssign2.xml");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStream);
		ITable expectedAnimalsTable = expectedDataSet.getTable("animals");
		ITable expectedFeedingSchedulesTable = expectedDataSet.getTable("feeding_schedules");
		
		Animal a = new Animal(3L, "Chipmunk", "Animalia", "Chordata", "Aves", "Galliformes", "Phasianidae", "Gallus",
				"G. gallus", 30.00d, 3.00d, "Bird (Domestic)", "Dead");
		FeedingSchedule fs = new FeedingSchedule(104, "midnight", "never", "Nothing",
				"Dead animals do not need to eat.");
		
		PowerMockito.mockStatic(DAOUtilities.class);
		when(DAOUtilities.getConnection()).thenReturn(connection);
		
		fsdi.assignFeedingSchedule(a,fs);
		
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualAnimalsTable = databaseDataSet.getTable("animals");
		ITable actualFeedingSchedulesTable = databaseDataSet.getTable("feeding_schedules");
		
		Assertion.assertEquals(expectedAnimalsTable, actualAnimalsTable);
		Assertion.assertEquals(expectedFeedingSchedulesTable, actualFeedingSchedulesTable);
		
		
	}

*/

}
