package com.examples.ezoo.dao;

import java.util.List;

import com.examples.ezoo.model.Animal;

/**
 * Main interface used to execute CRUD methods on Animal class
 * @author anon
 *
 */
public interface AnimalDAO {

	/**
	 * Used to retrieve a list of all Animals 
	 * @return
	 */
	List<Animal> getAllAnimals();

	/**
	 * Used to persist the animal to the datastore
	 * @param animalToSave
	 */
	void saveAnimal(Animal animalToSave) throws Exception;

	/**
	 * Used to retrieve specific animal record by animal ID
	 * @param animalID
	 * @return
	 */
	Animal getAnimal(long animalID);
	
	/**
	 * Used to retrieve all animals with a given feeding schedule
	 * @param scheduleID
	 * @return
	 */
	List<Animal> getAnimalsWithSchedule(long scheduleID);

	/**
	 * Used to change field values for an already existing animal
	 * @param animal
	 * @throws Exception
	 */
	void editAnimal(Animal animal) throws Exception;
}
