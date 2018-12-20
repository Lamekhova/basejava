<%@ page import="com.urise.model.ContactType" %>
<%@ page import="com.urise.model.SectionType" %>
<%@ page import="com.urise.model.ListSection" %>
<%@ page import="com.urise.model.ExperienceSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=50 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.model.Section"/>
            <h2><a>${type.name()}</a></h2>
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
                        <dl>
                            <dt>Название организации:</dt>
                            <dd><input type="text" name='${type}' size=52 value="${org.name.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт организации:</dt>
                            <dd><input type="text" name='${type}url' size=52 value="${org.name.url}"></dd>
                            </dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px; margin-bottom: 10px">
                            <c:forEach var="pos" items="${org.positionsList}">
                                <jsp:useBean id="pos" type="com.urise.model.Position"/>
                                <dl>
                                    <dt>Дата начала:</dt>
                                    <dd>
                                        <input type="month" name="${type}${counter.index}startDate" size=10
                                               value="<%=pos.getStartDate()%>">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Дата окончания:</dt>
                                    <dd>
                                        <input type="month" name="${type}${counter.index}endDate" size=10
                                               value="<%=pos.getEndDate()%>">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${type}${counter.index}title' size=75
                                               value="${pos.title}">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${type}${counter.index}description" rows=4
                                                  cols=77>${pos.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

