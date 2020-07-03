package com.examples.ezoo.dao;

import java.util.List;

import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.FeedingSchedule;

/**
 * Main interface used to execute CRUD methods on FeedingSchedule class
 * @author Karen Zwier
 *
 */
public interface FeedingScheduleDAO {

	
	/**
	 * Used to persist the feeding schedule to the datastore
	 * @param feedingScheduleToSave
	 */
	void saveFeedingSchedule(FeedingSchedule feedingScheduleToSave) throws Exception;
	
	/**
	 * Used to delete a feeding schedule from the datastore
	 * @param feedingScheduleToDelete
	 */
	void deleteFeedingSchedule(FeedingSchedule feedingScheduleToDelete) throws Exception;
	
	/**
	 * Used to retrieve a list of all FeedingSchedules 
	 * @return
	 */
	List<FeedingSchedule> getAllFeedingSchedules();
	
	/*
	 * Used to retrieve a specific feeding schedule record by schedule id
	 */
	FeedingSchedule getFeedingSchedule(long schedule_id);
	
	/**
	 * Used to retrieve a single feeding schedule from the datastore
	 * for a given animal
	 * @throws Exception 
	 */
	FeedingSchedule getFeedingSchedule(Animal animal) throws Exception;
	
	/**
	 * Used to assign a feeding schedule to a particular animal
	 */
	void assignFeedingSchedule(Animal animal, FeedingSchedule feedingSchedule) throws Exception;
	
	/**
	 * Used to unassign a feeding schedule from a particular animal
	 */
	void unassignFeedingSchedule(Animal animal) throws Exception;
	
}
