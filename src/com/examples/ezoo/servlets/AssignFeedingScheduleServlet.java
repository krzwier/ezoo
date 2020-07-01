package com.examples.ezoo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.examples.ezoo.dao.AnimalDAO;
import com.examples.ezoo.dao.DAOUtilities;
import com.examples.ezoo.dao.FeedingScheduleDAO;
import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.FeedingSchedule;

/**
 * Servlet implementation class AnimalCareServlet
 */
@WebServlet(description = "This servlet allows user to choose animal in order to assign feeding schedule", urlPatterns = {
		"/assignFeedingSchedule" })
public class AssignFeedingScheduleServlet extends HttpServlet {

	private static final long serialVersionUID = -2312192977171757638L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Grab a list of Animals from the Database
		AnimalDAO dao = DAOUtilities.getAnimalDao();
		List<Animal> animals = dao.getAllAnimals();

		// Grab a list of feeding schedules from the database
		FeedingScheduleDAO fsDao = DAOUtilities.getFeedingScheduleDao();
		List<FeedingSchedule> feedingSchedules = fsDao.getAllFeedingSchedules();

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("animals", animals);
		request.getSession().setAttribute("feedingSchedules", feedingSchedules);

		request.getRequestDispatcher("assignFeedingSchedule.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long animalID = Long.parseLong(request.getParameter("chosen_animalID"));


		/*String name = request.getParameter("animals[animalRowNo].name");

		String kingdom = request.getParameter("animals[animalRowNo].kingdom");
		String phylum = request.getParameter("animals[animalRowNo].phylum");
		String clazz = request.getParameter("animals[animalRowNo].clazz");
		String order = request.getParameter("animals[animalRowNo].rder");
		String family = request.getParameter("animals[animalRowNo].family");
		String genus = request.getParameter("animals[animalRowNo].genus");
		String species = request.getParameter("animals[animalRowNo].species");
		String type = request.getParameter("animals[animalRowNo].type");
		String healthStatus = request.getParameter("animals[animalRowNo].healthStatus");

		// Since request parameters are ALWAYS Strings we will convert them to Double
		double height = Double.parseDouble(request.getParameter("animals[animalRowNo].height"));
		double weight = Double.parseDouble(request.getParameter("animals[animalRowNo].weight"));

		// Create an Animal object from the parameters
		Animal animalToAssign = new Animal(animalID, name, kingdom, phylum, clazz, order, family, genus, species,
				height, weight, type, healthStatus);*/

		long feedingScheduleID = Long
				.parseLong(request.getParameter("chosen_scheduleID"));
		
		AnimalDAO animalDAO = DAOUtilities.getAnimalDao();
		FeedingScheduleDAO fsDAO = DAOUtilities.getFeedingScheduleDao();
		try {
			Animal a = animalDAO.getAnimal(animalID);
			FeedingSchedule fs = fsDAO.getFeedingSchedule(feedingScheduleID);
			fsDAO.assignFeedingSchedule(a, fs);
			request.getSession().setAttribute("message", "Feeding schedule successfully assigned");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("animalCare");
		} catch (Exception e) {

			e.printStackTrace();

			// change the message
			request.getSession().setAttribute("message", "There was a problem assigning the feeding schedule to the animal");
			request.getSession().setAttribute("messageClass", "alert-danger");

			request.getRequestDispatcher("assignFeedingSchedule.jsp").forward(request, response);
		}

	}

}
