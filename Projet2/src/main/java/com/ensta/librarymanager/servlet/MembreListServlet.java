package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.modele.*;

public class MembreListServlet extends HttpServlet{

	MembreService membreService = MembreServiceImpl.getInstance();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
		try{
            request.setAttribute("membres", membreService.getList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        dispatcher.forward(request, response);	
	}
}
