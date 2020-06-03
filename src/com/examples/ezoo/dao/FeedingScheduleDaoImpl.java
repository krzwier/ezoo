package com.examples.ezoo.dao;

import java.sql.Connection;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFeedingSchedule(FeedingSchedule feedingScheduleToDelete) throws Exception {
		// TODO Auto-generated method stub
		
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
