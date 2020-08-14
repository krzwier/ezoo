
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

		<h1>
			eZoo <small>Feeding Schedules</small>
		</h1>
		<hr class="paw-primary">
		<table
			class="table table-striped table-hover table-responsive ezoo-datatable">
			<thead>
				<tr>
					<th class="text-center">Schedule ID</th>
					<th class="text-center">Feeding Time</th>
					<th class="text-center">Recurrence</th>
					<th class="text-center">Food</th>
					<th class="text-center">Notes</th>
					<th class="text-center">Currently Assigned To:</th>
					<th class="text-center">Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="feedingSchedule" items="${feedingSchedules}">
					<tr>
						<td><fmt:formatNumber value="${feedingSchedule.key.schedule_ID}" /></td>
						<td><c:out value="${feedingSchedule.key.feeding_time}" /></td>
						<td><c:out value="${feedingSchedule.key.recurrence}" /></td>
						<td><c:out value="${feedingSchedule.key.food}" /></td>
						<td><c:out value="${feedingSchedule.key.notes}" /></td>
						<td>
							<table align="center">
								<c:forEach var="animal" items="${feedingSchedule.value}">
								<tr style="background-color: transparent;">
									<td style="padding: 0;"><c:out value="${animal.name} (${animal.taxSpecies})" /></td>
								</tr>
								</c:forEach>
							</table>
						</td>
						<td>
							<form name="deleteFeedingScheduleForm" action="feedingSchedules" method='post'>
								<input type="hidden" name="schedule_id" value="${feedingSchedule.key.schedule_ID}" />
								<button type="submit" class="btn btn-primary">Delete</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
</header>


<!-- Footer -->
<jsp:include page="footer.jsp" />
