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

				String sql = "DELETE FROM feeding_schedules WHERE " + 
						"schedule_id = ? AND " + 
						"feeding_time = ? AND " + 
						"recurrence = ? AND " + 
						"food = ? AND " +
						"notes IS NULL";
				// + "notes = ? ";

				stmt = connection.prepareStatement(sql);
				stmt.setLong(1, feedingScheduleToDelete.getSchedule_ID());
				stmt.setString(2, feedingScheduleToDelete.getFeeding_time());
				stmt.setString(3, feedingScheduleToDelete.getRecurrence());
				stmt.setString(4, feedingScheduleToDelete.getFood());
				// stmt.setString(5, feedingScheduleToDelete.getNotes());
			}

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
	public FeedingSchedule getFeedingSchedule(Animal animal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignFeedingSchedule(Animal animal, FeedingSchedule feedingSchedule) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void unassignFeedingSchedule(Animal animal) throws Exception {
		// TODO Auto-generated method stub

	}

}
