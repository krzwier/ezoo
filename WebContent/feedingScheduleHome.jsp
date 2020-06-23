	<!-- Header -->
	<jsp:include page="header.jsp" />
	
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!-- 	Just some stuff you need -->
	<header>
	  <div class="container">

	<c:choose>
	<c:when test="${not empty message }">
	  <p class="alert ${messageClass}">${message }</p>
	<%
	  session.setAttribute("message", null);
	  session.setAttribute("messageClass", null);
	%>
	</c:when>
	</c:choose>
 
		<h1>eZoo <small>Feeding Schedules</small></h1>
		<hr class="paw-primary">
		<table class="table table-striped table-hover table-responsive ezoo-datatable">
			<thead>
				<tr>
					<th class="text-center">Feeding Time</th>
					<th class="text-center">Recurrence</th>
					<th class="text-center">Food</th>
					<th class="text-center">Notes</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="feedingSchedule" items="${feedingSchedules}">
					<tr>
						<td><c:out value="${feedingSchedule.feeding_time}" /></td>
						<td><c:out value="${feedingSchedule.recurrence}" /></td>
						<td><c:out value="${feedingSchedule.food}" /></td>
						<td><c:out value="${feedingSchedule.notes}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	

	  </div>
	</header>
	
	<section>
	  <div class="container">
	    
	    <h1><small>Cool Facts</small></h1>
		<h4>Largest Animal (lbs):
		  <small>
		    <c:if test="${not empty largestAnimal}">
		      ${largestAnimal.name}, at ${largestAnimal.weight } lbs
		    </c:if>
		  </small>
		</h4>
		<h4>Longest Named Animal:
		  <small>
		    <c:if test="${not empty longestNamedAnimal}">
		      ${longestNamedAnimal.name }
		    </c:if>
		  </small>
		</h4>
	    
	  </div>
	</section>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />
