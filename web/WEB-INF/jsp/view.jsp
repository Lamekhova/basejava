<%@ page import="com.urise.model.TextSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.resumeContact}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.resumeSection}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.model.SectionType, com.urise.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.model.Section"/>
            <tr>
                <td colspan="2"><h2><a name="type.name">${type.name()}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <%=((TextSection) section).getSectionBody()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='QUALIFICATION' || type=='ACHIEVEMENT'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getContent()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((ExperienceSection) section).getExperienceEntries()%>">
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty org.name.url}">
                                        <h3>${org.name.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${org.name.url}">${org.name.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${org.positionsList}">
                            <jsp:useBean id="position" type="com.urise.model.Position"/>
                            <tr>
                                <td><b>${position.startDate}</b> - <b>${position.endDate}</b>
                                    <b>${position.title}</b><br>
                                        ${position.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
