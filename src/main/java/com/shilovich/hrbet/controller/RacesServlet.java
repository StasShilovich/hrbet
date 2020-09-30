package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class RacesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RaceService raceService = serviceFactory.getRaceService();
            List<Race> races = raceService.showAll();

            req.setAttribute("races", races);
            req.setAttribute("str", "312312312e1232312");
            getServletContext().getRequestDispatcher("/WEB-INF/pages/races.jsp").forward(req, resp);

        } catch (ServiceException e) {
            // TODO: 30.09.2020 Error page
        }
    }

}
