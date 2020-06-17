package com.examples.ezoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.FeedingSchedule;

public class FeedingScheduleDaoImpl implements FeedingScheduleDAO {

	@Override
	public void saveFeedingSchedule(FeedingSchedule feedingScheduleToSave) throws Exception {

		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;

		try {
			connection = DAOUtilities.getConnection();

			String sql = "INSERT INTO feeding_schedules VALUES (?,?,?,?,?)";

			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, feedingScheduleToSave.getSchedule_ID());
			stmt.setString(2, feedingScheduleToSave.getFeeding_time());
			stmt.setString(3, feedingScheduleToSave.getRecurrence());
			stmt.setString(4, feedingScheduleToSave.getFood());
			stmt.setString(5, feedingScheduleToSave.getNotes());

			success = stmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Insert feeding schedule failed:" + feedingScheduleToSave +
					". " + e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (success == 0) {
				// then update didn't occur, throw an exception
				throw new Exception("Insert feeding schedule failed:" + feedingScheduleToSave);
			}
		}

	}

	@Override
	public void deleteFeedingSchedule(FeedingSchedule feedingScheduleToDelete) throws Exception {

		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;

		try {
			connection = DAOUtilities.getConnection();

			if (feedingScheduleToDelete.getNotes() == null) {
				// notes field is null

				String sql = "DELETE FROM feeding_schedules WHERE " + "schedule_id = ? AND " + "feeding_time = ? AND "
						+ "recurrence = ? AND " + "food = ? AND " + "notes IS NULL";

				stmt = connection.prepareStatement(sql);
				stmt.setLong(1, feedingScheduleToDelete.getSchedule_ID());
				stmt.setString(2, feedingScheduleToDelete.getFeeding_time());
				stmt.setString(3, feedingScheduleToDelete.getRecurrence());
				stmt.setString(4, feedingScheduleToDelete.getFood());
				
			} else {
				// notes field is not null

				String sql = "DELETE FROM feeding_schedules WHERE " + 
						"schedule_id = ? AND " + 
						"feeding_time = ? AND " + 
						"recurrence = ? AND " + 
						"food = ? AND " + 
						"notes = ?";

				stmt = connection.prepareStatement(sql);
				stmt.setLong(1, feedingScheduleToDelete.getSchedule_ID());
				stmt.setString(2, feedingScheduleToDelete.getFeeding_time());
				stmt.setString(3, feedingScheduleToDelete.getRecurrence());
				stmt.setString(4, feedingScheduleToDelete.getFood());
				stmt.setString(5, feedingScheduleToDelete.getNotes());

			}

			success = stmt.executeUpdate();
		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (success == 0) {
				// then update didn't occur, throw an exception
				throw new Exception("Delete feeding schedule failed:" + feedingScheduleToDelete);
			}
		}

	}

	@Override
	public List<FeedingSchedule> getAllFeedingSchedules() {
		List<FeedingSchedule> feedingSchedules = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;

		try {

			connection = DAOUtilities.getConnection();

			stmt = connection.createStatement();

			String sql = "SELECT * FROM feeding_schedules";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				FeedingSchedule fs = new FeedingSchedule();

				fs.setSchedule_ID(rs.getInt("schedule_id"));
				fs.setFeeding_time(rs.getString("feeding_time"));
				fs.setRecurrence(rs.getString("recurrence"));
				fs.setFood(rs.getString("food"));
				fs.setNotes(rs.getString("notes"));

				feedingSchedules.add(fs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return feedingSchedules;
	}

	@Override
	public FeedingSchedule getFeedingSchedule(Animal animal) throws Exception {

		FeedingSchedule fs = new FeedingSchedule();
		boolean success = false;
		
		Connection connection = null;
		PreparedStatement stmtGetFeedingScheduleID = null;
		PreparedStatement stmtGetFeedingScheduleRecord = null;
		
		try {
			connection = DAOUtilities.getConnection();
			
			String sqlGetFeedingScheduleID = "SELECT feeding_schedule FROM animals WHERE animalid = ?";
			
			stmtGetFeedingScheduleID = connection.prepareStatement(sqlGetFeedingScheduleID);
			
			stmtGetFeedingScheduleID.setLong(1,animal.getAnimalID());
	

			ResultSet rs = stmtGetFeedingScheduleID.executeQuery();
			
			if (rs.next()) {
				// animal exists in database
				Long feedingScheduleID = rs.getLong("feeding_schedule");
				
				if (feedingScheduleID != null) {
					// animal has feeding schedule
					String sqlGetFeedingScheduleRecord = "SELECT * FROM feeding_schedules WHERE schedule_id = ?";
					
					stmtGetFeedingScheduleRecord = connection.prepareStatement(sqlGetFeedingScheduleRecord);
					
					stmtGetFeedingScheduleRecord.setLong(1,feedingScheduleID);
					
					ResultSet rs2 = stmtGetFeedingScheduleRecord.executeQuery();
					
					if (rs2.next()) {
						// feeding schedule exists in database
						
						fs.setSchedule_ID(rs2.getInt("schedule_id"));
						fs.setFeeding_time(rs2.getString("feeding_time"));
						fs.setRecurrence(rs2.getString("recurrence"));
						fs.setFood(rs2.getString("food"));
						fs.setNotes(rs2.getString("notes"));
					} else {
						//feeding schedule does not exist in database
						fs = null;
					}
					
				} 
				success = true;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (stmtGetFeedingScheduleID != null) {
					stmtGetFeedingScheduleID.close();
				}
				if (stmtGetFeedingScheduleRecord != null) {
					stmtGetFeedingScheduleRecord.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (success == false) {
				// then update didn't occur, throw an exception
				throw new Exception("Could not retrieve feeding schedule:" + animal );
			}
		}
		
		return fs;

	}

	@Override
	public void assignFeedingSchedule(Animal animal, FeedingSchedule feedingSchedule) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtGetFeedingScheduleRecord = null;
		
		int success = 0;
		
		try {
			connection = DAOUtilities.getConnection();
			
			/*
			 * // first check if feeding schedule exists in database
			 * 
			 * String sqlGetFeedingScheduleRecord =
			 * "SELECT * FROM feeding_schedules WHERE schedule_id = ?";
			 * 
			 * stmtGetFeedingScheduleRecord =
			 * connection.prepareStatement(sqlGetFeedingScheduleRecord);
			 * 
			 * stmtGetFeedingScheduleRecord.setLong(1,feedingSchedule.getSchedule_ID());
			 * 
			 * ResultSet rs = stmtGetFeedingScheduleRecord.executeQuery();
			 * 
			 * 
			 * if (!rs.next()) { saveFeedingSchedule(feedingSchedule); }
			 */
			
			
			String sql = "UPDATE animals SET feeding_schedule = ? WHERE animalid = ?";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, feedingSchedule.getSchedule_ID());
			stmt.setLong(2,animal.getAnimalID());

			success = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (stmtGetFeedingScheduleRecord != null) {
					stmtGetFeedingScheduleRecord.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (success == 0) {
				// then update didn't occur, throw an exception
				throw new Exception("Assign feeding schedule failed:" + animal + ", " + feedingSchedule);
			}
		}

	}

	@Override
	public void unassignFeedingSchedule(Animal animal) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;
		
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "UPDATE animals SET feeding_schedule = NULL WHERE animalid = ?";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1,animal.getAnimalID());
	

			success = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (success == 0) {
				// then update didn't occur, throw an exception
				throw new Exception("Unassign feeding schedule failed:" + animal);
			}
		}

	}

}
