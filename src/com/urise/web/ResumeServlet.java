package com.urise.web;

import com.urise.Config;
import com.urise.model.*;
import com.urise.storage.Storage;
import com.urise.utill.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (isEmptyData(uuid)) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getResumeContact().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (isEmptyData(value) && values.length < 2) {
                resume.getResumeSection().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        resume.setSection(type, new ListSection(value.split("[\\r\\n]+")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<ExperienceEntry> experienceEntries = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!isEmptyData(name)) {
                                List<Position> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!isEmptyData(titles[j])) {
                                        positions.add(new Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                experienceEntries.add(new ExperienceEntry(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.addSection(type, new ExperienceSection(experienceEntries));
                        break;
                }
            }
        }
        if (uuid == null || uuid.length() == 0) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection();
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            if (section == null) {
                                section = new ListSection(Collections.emptyList());
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            ExperienceSection experienceSection = (ExperienceSection) resume.getSection(type);
                            List<ExperienceEntry> emptyExpEntry = new ArrayList<>();
                            emptyExpEntry.add(ExperienceEntry.EMPTY);

                            if (experienceSection != null) {
                                for (ExperienceEntry experienceEntry : experienceSection.getExperienceEntries()) {
                                    List<Position> emptyPosition = new ArrayList<>();
                                    emptyPosition.add(Position.EMPTY);
                                    emptyPosition.addAll(experienceEntry.getPositionsList());
                                    emptyExpEntry.add(new ExperienceEntry(experienceEntry.getName(), emptyPosition));
                                }
                            }
                            section = new ExperienceSection(emptyExpEntry);
                            break;
                    }
                    resume.addSection(type, section);
                }
                break;
            case "view":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = Resume.EMPTY_RESUME;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(request, response);
    }

    private static boolean isEmptyData(String str) {
        return str == null || str.trim().length() == 0;
    }
}
