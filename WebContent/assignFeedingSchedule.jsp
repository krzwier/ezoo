
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
			eZoo <small>Assign Feeding Schedule to Animal</small>
		</h1>

		<form name="assignFeedingScheduleForm" action="assignFeedingSchedule"
			method='post'>
			<fieldset>
				<legend>Select feeding schedule:</legend>
				<table
					class="table table-striped table-hover table-responsive ezoo-datatable">
					<thead>
						<tr>
							<th class="text-center">Animal ID</th>
							<th class="text-center">Name</th>
							<th class="text-center">Genus</th>
							<th class="text-center">Species</th>
							<th class="text-center">Height(ft)</th>
							<th class="text-center">Weight(lbs)</th>
							<th class="text-center">Type</th>
							<th class="text-center">Health Status</th>
							<th class="text-center">Select</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="animal" items="${animals}"
							varStatus="animalStatus">
							<tr>
								<td><c:out value="${animal.animalID}" /></td>
								<td><c:out value="${animal.name}" /></td>

								<td><c:out value="${animal.taxGenus}" /></td>
								<td><c:out value="${animal.taxSpecies}" /></td>

								<td><fmt:formatNumber value="${animal.height}" /></td>
								<td><fmt:formatNumber value="${animal.weight}" /></td>

								<td><c:out value="${animal.type}" /></td>
								<td><c:out value="${animal.healthStatus}" /></td>
								<td><input type="radio" name="animalRadioButton"
									value="${animalStatus.count}">
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
			<fieldset>
				<legend>Select feeding schedule:</legend>


				<table
					class="table table-striped table-hover table-responsive ezoo-datatable">
					<thead>
						<tr>
							<th class="text-center">Schedule ID</th>
							<th class="text-center">Feeding Time</th>
							<th class="text-center">Recurrence</th>
							<th class="text-center">Food</th>
							<th class="text-center">Notes</th>
							<th class="text-center">Select</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="feedingSchedule" items="${feedingSchedules}"
							varStatus="feedingScheduleStatus">
							<tr>
								<td><fmt:formatNumber
										value="${feedingSchedule.schedule_ID}" /></td>
								<td><c:out value="${feedingSchedule.feeding_time}" /></td>
								<td><c:out value="${feedingSchedule.recurrence}" /></td>
								<td><c:out value="${feedingSchedule.food}" /></td>
								<td><c:out value="${feedingSchedule.notes}" /></td>
								<td><input type="radio" name="feedingScheduleRadioButton"
									value="${feedingScheduleStatus.count}">
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
			<div class="form-group">
				<c:set var="animalRowNo" value="${6}" />
				<input type="hidden" name="chosen_animalID"
					value="${sessionScope.animals[animalRowNo].animalID}" />
				<c:set var="feedingScheduleRowNo" value="${1}" />
				<input type="hidden" name="chosen_scheduleID"
					value="${sessionScope.feedingSchedules[feedingScheduleRowNo].schedule_ID}" />

				<button type="submit" class="btn btn-primary">Assign</button>

			</div>
		</form>
	</div>
</header>

<!-- Footer -->
<jsp:include page="footer.jsp" />
