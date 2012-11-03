<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
    <head>
        <title>
            <%--<tiles:getAsString name="title" />--%>
            <h1>Test</h1>
        </title>
        <!-- Google Font and style definitions -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=PT+Sans:regular,bold">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/workoutsum.css"/>" />

        <style type="text/css">
            body {
                font-family: Verdana,  Helvetica, sans-serif;
                font-size: 14px;
                background: #FFFFEE;
            }
            .vertical_menu {
                float: left;
                width: 90px;
                border: 1px dotted blue;
                background-color: #EEFFFF;
                padding: 5px;
                margin: 5px;
            }
            .vertical_menu li {
                list-style: none;
                margin: 5px;
            }
            .vertical_menu li a {
                text-decoration: none;
                color: black;
            }
            .vertical_menu li a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <nav>
            <%--<ul class="vertical_menu">--%>
                <%--<li class="i_home"><a href="<c:url value="/"/>">Home</a></li>--%>
                <%--<li class="i_about"><a href="<c:url value="/info/about.html"/>">About</a></li>--%>
            <%--</ul>--%>
            <tiles:insertAttribute name="menu"/>
        </nav>
        <div>
            <h1>Home</h1>

            <p>This is a simple tiles example.</p>

            <p>The main body that you are reading is in <i>/WEB-INF/jsp/index.jsp</i> and the request for
                <i>/index.html</i> is rendered using the <i>index</i> Tiles definition located in
                <i>/WEB-INF/tiles-defs/templates.xml</i>.</p>
        </div>

    </body>
</html>