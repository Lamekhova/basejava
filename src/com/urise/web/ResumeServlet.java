package com.urise.web;

import com.urise.Config;
import com.urise.model.Resume;
import com.urise.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        SqlStorage sqlStorage = Config.get().getStorage();
        List<Resume> allResumes = sqlStorage.getAllSorted();
        String prefix = "<html>\n" +
                "<head>\n" +
                "<title>Таблица</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"1\">";
        response.getWriter().write(prefix);
        for (Resume resume : allResumes) {
            response.getWriter().write(
                    "<tr>\n" +
                            "<td>" + resume.getUuid() + "</td>" +
                            "<td>" + resume.getFullName() + "</td>\n" +
                            "</tr>");
        }
//        String resumeTemplate = "<tr>\n" +
//                "<td>%</td><td>%s</td>\n" +
//                "</tr>";
//        for (Resume resume : allResumes) {
//            response.getWriter().write(String.format(resumeTemplate, resume.getUuid(), resume.getFullName()));
//        }
        String postfix = "</table>\n" +
                "</body>\n" +
                "</html>";
        response.getWriter().write(postfix);
    }
}
