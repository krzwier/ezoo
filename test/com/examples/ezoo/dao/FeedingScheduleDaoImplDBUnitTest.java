package com.examples.ezoo.dao;

import java.sql.Connection;

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
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FeedingScheduleDaoImplDBUnitTest extends DataSourceBasedDBTestCase {

	
	private Connection connection;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl(
				"jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
		dataSource.setUser("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("data.xml"));
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

}
