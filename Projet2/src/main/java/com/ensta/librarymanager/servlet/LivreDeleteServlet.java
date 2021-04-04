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

public class LivreDeleteServlet extends HttpServlet{
	LivreService livreService = LivreServiceImpl.getInstance();
	int id;

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
		this.id = Integer.valueOf(request.getParameter("id"));
		try{
			Livre livre = livreService.getById(this.id);
			request.setAttribute("livre", livre);
		} catch(ServiceException e){
			System.out.println(e.getMessage());
		}
		


		dispatcher.forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			livreService.delete(this.id);
		} catch (ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible de supprimer le livre");
		}
		response.sendRedirect("/TP3Ensta/livre_list");
	}
}

