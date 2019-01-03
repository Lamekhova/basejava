<%@ page import="com.urise.model.ContactType" %>
<%@ page import="com.urise.model.SectionType" %>
<%@ page import="com.urise.model.ListSection" %>
<%@ page import="com.urise.model.ExperienceSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="bootstrap-3.3.2-dist/css/bootstrap.css" rel="stylesheet">
    <link href="css/jumbotron-narrow.css" rel="stylesheet">
    <jsp:useBean id="resume" type="com.urise.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>

<div class="container">
    <jsp:include page="fragments/header.jsp"/>
    <div class="jumbotron">
        <form method="post" action="resume" enctype="application/x-www-form-urlencoded" id="my_form">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <div class="row">
                <div class="col-sm-4" align="left">
                    <h6>ФИО</h6>
                </div>
                <div class="col-sm-8">
                    <input type="text" name="fullName" size=50 value="${resume.fullName}">
                </div>
            </div>
            <h2>Контакты</h2>
            <%--<div class="container">--%>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <div class="row">
                    <div class="col-sm-4" align="left">
                        <h6>${type.title}</h6>
                    </div>
                    <div class="col-sm-8">
                        <input type="text" name="${type.name()}" size=50 value="${resume.getContact(type)}">
                    </div>
                </div>
            </c:forEach>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.urise.model.Section"/>
                <h2>${type.getTitle()}</h2>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                        <input type='text' name='${type}' size=75 value='<%=section%>'>
                    </c:when>
                    <c:when test="${type=='QUALIFICATION' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=77
                              rows=5><%=String.join("\n", ((ListSection) section).getContent())%></textarea>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((ExperienceSection) section).getExperienceEntries()%>"
                                   varStatus="counter">
                            <div class="row">
                                <div class="col-sm-4" align="left">
                                    <h6>Название организации:</h6>
                                </div>
                                <div class="col-sm-8">
                                    <input type="text" name='${type}' size=52 value="${org.name.name}">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-4" align="left">
                                    <h6>Сайт организации:</h6>
                                </div>
                                <div class="col-sm-8">
                                    <input type="text" name='${type}url' size=52 value="${org.name.url}">
                                </div>
                            </div>
                            <c:forEach var="pos" items="${org.positionsList}">
                                <jsp:useBean id="pos" type="com.urise.model.Position"/>
                                <div class="row">
                                    <div class="col-sm-4" align="left">
                                        <h6>Дата начала:</h6>
                                    </div>
                                    <div class="col-sm-8" style="padding-bottom:7px" align="left">
                                        <input type="month" name="${type}${counter.index}startDate"
                                               value="<%=pos.getStartDate()%>">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4" align="left">
                                        <h6>Дата окончания:</h6>
                                    </div>
                                    <div class="col-sm-8" style="padding-bottom:7px" align="left">
                                        <input type="month" name="${type}${counter.index}endDate"
                                               value="<%=pos.getEndDate()%>">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4" align="left">
                                        <h6>Должность:</h6>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name='${type}${counter.index}title' size=52
                                               value="${pos.title}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4" align="left">
                                        <h6>Описание:</h6>
                                    </div>
                                    <div class="col-sm-8" style="padding-bottom:7px">
                                            <textarea name="${type}${counter.index}description" rows=4
                                                      cols=54>${pos.description}</textarea></dd>
                                    </div>
                                </div>
                                <hr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
        </form>
        <a class="btn btn-lg btn-success" onclick="document.getElementById('my_form').submit(); return false;"
           role="button">Сохранить</a>
        <a class="btn btn-lg btn-success" onclick="window.history.back()" role="button">Отменить</a></p>
    </div>
    <jsp:include page="fragments/footer.jsp"/>
</div> <!-- /container -->
</body>
</html>

