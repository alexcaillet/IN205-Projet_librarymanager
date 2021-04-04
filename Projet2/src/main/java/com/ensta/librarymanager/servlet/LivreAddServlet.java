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

public class LivreAddServlet extends HttpServlet{

	LivreService livreService = LivreServiceImpl.getInstance();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
		dispatcher.forward(request, response);	
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String isbn = request.getParameter("isbn");
		int id;
		try{
			id = livreService.create(titre, auteur, isbn);
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible de créer le nouveau livre");
		}
		response.sendRedirect("/TP3Ensta/livre_details?id="+id);
	}
	
}
