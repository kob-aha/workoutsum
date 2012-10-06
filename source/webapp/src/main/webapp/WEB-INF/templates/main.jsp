<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
    <title>
        <tiles:getAsString name="title" />
    </title>
    <!-- Google Font and style definitions -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=PT+Sans:regular,bold">
    <link rel="stylesheet" type="text/css" href="<c:url value="/styles/workoutsum.css"/>" />
</head>
<body>
<div id="header">
	<div id="headerTitle"><tiles:insertAttribute name="header" /></div>
</div>
<div id="menu">
	<tiles:insertAttribute name="menu" />
</div>
<div id="content">
	<td><tiles:insertAttribute name="body" />
</div>
<div id="footer">
	<tiles:insertAttribute name="footer" />
</div>
</body>
</html>