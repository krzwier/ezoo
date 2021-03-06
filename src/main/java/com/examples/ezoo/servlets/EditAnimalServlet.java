package com.examples.ezoo.servlets;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.examples.ezoo.dao.AnimalDAO;
import com.examples.ezoo.dao.DAOUtilities;
import com.examples.ezoo.model.Animal;

/**
 * Servlet implementation class AddAnimalServlet
 */
@WebServlet("/editAnimal")
public class EditAnimalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5581926865528809209L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Grab a list of Animals from the Database
		AnimalDAO dao = DAOUtilities.getAnimalDao();
		List<Animal> animals = dao.getAllAnimals();

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("animals", animals);

		request.getRequestDispatcher("editAnimal.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Parameters
		//We MUST convert to a Long since parameters are always Strings
		long id = Long.parseLong(request.getParameter("id"));

		String name = request.getParameter("name");

		String kingdom = request.getParameter("kingdom");
		String phylum = request.getParameter("phylum");
		String clazz = request.getParameter("clazz");
		String order = request.getParameter("order");
		String family = request.getParameter("family");
		String genus = request.getParameter("genus");
		String species = request.getParameter("species");
		String type = request.getParameter("type");
		String healthStatus = request.getParameter("healthStatus");


		//Since request parameters are ALWAYS Strings we will convert them to Double
		double height = Double.parseDouble(request.getParameter("height"));
		double weight = Double.parseDouble(request.getParameter("weight"));

		//Create an Animal object from the parameters
		Animal animalToEdit = new Animal(
				id, 
				name, 
				kingdom,
				phylum,
				clazz,
				order,
				family,
				genus,
				species,
				height,
				weight,
				type,
				healthStatus);

		//Call DAO method
		AnimalDAO dao = DAOUtilities.getAnimalDao();
		try {
			dao.editAnimal(animalToEdit);
			request.getSession().setAttribute("message", "Animal successfully edited");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("animalCare");

		}catch (Exception e){
			e.printStackTrace();

			//change the message
			request.getSession().setAttribute("message", "There was a problem editing the animal at this time");
			request.getSession().setAttribute("messageClass", "alert-danger");

			request.getRequestDispatcher("editAnimal.jsp").forward(request, response);

		}
	}

}
