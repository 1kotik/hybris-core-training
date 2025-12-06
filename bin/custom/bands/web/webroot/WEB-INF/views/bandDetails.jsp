<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<title>Band Details</title>
<body>
<h1>Band Details</h1>
Band Details for ${band.name}
<p>${band.description}</p>
<p>Music type:</p>
<ul>
    <c:forEach var="genre" items="${band.genres}">
        <li>${genre}</li>
    </c:forEach>
</ul>
<c:if test="${not empty band.members}">
    <p>Members:</p>
    <ul>
        <c:forEach var="member" items="${band.members}">
            <li>${member.name}:
                ${member.skillLevel} (<c:forEach var="instrument" items="${member.instrumentsExperience.entrySet()}" varStatus="status">
                    ${instrument.key}: ${instrument.value} years${not status.last ? ', ' : ''}
                </c:forEach>)
            </li>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${not empty band.manager.id}">
    <p>Manager: ${band.manager.name}</p>
</c:if>
<c:if test="${not empty band.tours}">
    <p>Tour History:</p>
    <ul>
        <c:forEach var="tour" items="${band.tours}">
            <li><a href="../concerttours/${tour.id}">${tour.tourName}</a> (number of
                concerts: ${tour.numberOfConcerts})
            </li>
        </c:forEach>
    </ul>
</c:if>
<a href="../bands">Back to Band List</a>
</body>
</html>