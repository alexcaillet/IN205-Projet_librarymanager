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

public class MembreAddServlet extends HttpServlet{

	MembreService membreService = MembreServiceImpl.getInstance();
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
		dispatcher.forward(request, response);	
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		int id;
		try{
			id = membreService.create(nom, prenom, adresse, email, telephone);
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible de créer le nouveau membre");
		}
		response.sendRedirect("/TP3Ensta/membre_details?id="+id);
	}
}
