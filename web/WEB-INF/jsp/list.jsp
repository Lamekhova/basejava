<%@ page import="com.urise.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="bootstrap-3.3.2-dist/css/bootstrap.css" rel="stylesheet">
    <link href="css/jumbotron-narrow.css" rel="stylesheet">
    <link href="css/table-style.css" rel="stylesheet">
    <title>Список резюме</title>
</head>
<body>
<div class="container">
    <jsp:include page="fragments/header.jsp"/>
    <div class="jumbotron" style="padding-left: 0px; padding-right: 0px">
        <h1>База данных резюме</h1>
        <table class="simple-little-table" cellspacing='0'>
            <tr>
                <th>Имя</th>
                <th>Email</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.urise.model.Resume"/>
                <tr>
                    <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td>${resume.getContact(ContactType.EMAIL)}</td>
                    </td>
                    <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="./img/delete.png"></a></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="./img/pencil.png"></a></td>
                </tr>
            </c:forEach>
        </table>
        <p><a class="btn btn-lg btn-success" href="resume?action=add" role="button">Новое резюме</a></p>
    </div>

    <div class="row marketing">
        <div class="col-lg-6">
            <h4>Добавить резюме</h4>
            <p>Вы можете создать новое резюме и сохранить его в базу данных</p>

            <h4>Редактировать резюме</h4>
            <p>Вы можете редактировать существующие данные в резюме и дополнять разделы "Опыт работы" и "Образование" новыми данными</p>
        </div>

        <div class="col-lg-6">
            <h4>Просмотреть резюме</h4>
            <p>Вы можете просмотреть полное резюме нажав на него</p>

            <h4>Удалить резюме</h4>
            <p>Вы можете удалить резюме из базы данных нажав на соответсвующий ярлык</p>
        </div>
    </div>

    <jsp:include page="fragments/footer.jsp"/>
</div> <!-- /container -->
</body>
</html>
