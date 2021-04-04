package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;

public class EmpruntAddServlet extends HttpServlet{

	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
	MembreService membreService = MembreServiceImpl.getInstance();
	LivreService livreService = LivreServiceImpl.getInstance();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
		try{
			request.setAttribute("membres_dispo", membreService.getListMembreEmpruntPossible());
			request.setAttribute("livres_dispo", livreService.getListDispo());
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible d'afficher les membres et livres disponibles");
		}
		dispatcher.forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idLivre = Integer.valueOf(request.getParameter("idLivre"));
		int idMembre = Integer.valueOf(request.getParameter("idMembre"));

		try{
			empruntService.create(idMembre, idLivre, LocalDate.now());
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible de créer le nouvel emprunt");
		}
		//this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(request, response);
		response.sendRedirect("emprunt_list");
	}
}
