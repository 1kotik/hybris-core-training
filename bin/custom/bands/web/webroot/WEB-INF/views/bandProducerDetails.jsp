<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<title>Producer Details</title>
<body>
<h1>Producer ${producer.firstName} ${producer.lastName}</h1>
<h2>Contact info: email - ${band.producer.email}, phone - ${band.producer.phone}</h2>
<h2>Description</h2>
<p>${producer.description}</p>
<p>Managed groups</p>
<c:forEach var="band" items="${producer.bands}">
    <p>${band.name}<p>
</c:forEach>

<a href="../bands">Back to Band List</a>
</body>
</html>
