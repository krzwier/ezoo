package com.examples.ezoo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


@WebServlet(description = "This servlet displays the table of feeding schedules", urlPatterns = { "/feedingSchedules" })
public class FeedingScheduleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Grab a list of Feeding Schedules from the Database
		FeedingScheduleDAO dao = DAOUtilities.getFeedingScheduleDao();
		List<FeedingSchedule> feedingSchedules = dao.getAllFeedingSchedules();
		AnimalDAO animalDao = DAOUtilities.getAnimalDao();
		
		
		Map<FeedingSchedule, List<Animal>> mapFeedingSchedulesToAnimals = new HashMap<>();
		
		for (FeedingSchedule fs : feedingSchedules) {
			mapFeedingSchedulesToAnimals.put(fs, animalDao.getAnimalsWithSchedule(fs.getSchedule_ID()));
		}

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("feedingSchedules", mapFeedingSchedulesToAnimals);
		
		
		request.getRequestDispatcher("feedingScheduleHome.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		FeedingScheduleDAO dao = DAOUtilities.getFeedingScheduleDao();
		long schedule_id = Long.parseLong((String) req.getParameter("schedule_id"));
		FeedingSchedule fs = dao.getFeedingSchedule(schedule_id);
		try {
			dao.deleteFeedingSchedule(fs);
			req.getSession().setAttribute("message", "Feeding schedule " + schedule_id + " has been deleted.");
			req.getSession().setAttribute("messageClass", "alert-success");
			resp.sendRedirect("feedingSchedules");
		} catch (Exception e) {
			
			e.printStackTrace();
			
			req.getSession().setAttribute("message", "There was a problem deleting the feeding schedule.");
			req.getSession().setAttribute("messageClass", "alert-danger");

			req.getRequestDispatcher("feedingScheduleHome.jsp").forward(req, resp);
		}
		
	}

	

}
