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

public class LivreDetailsServlet extends HttpServlet{

	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
	LivreService livreService = LivreServiceImpl.getInstance();
	Livre livre = new Livre();
	int id;

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");

		this.id = Integer.valueOf(request.getParameter("id"));

		try{
			livre = livreService.getById(this.id);
			request.setAttribute("id", this.id);
			request.setAttribute("titre", livre.getTitre());
			request.setAttribute("auteur", livre.getAuteur());
			request.setAttribute("isbn", livre.getISBN());
			request.setAttribute("emprunts_livre", empruntService.getListCurrentByLivre(livre.getPrimaryKey()));
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible d'obtenir les détails du livre");
		}
		dispatcher.forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String isbn = request.getParameter("isbn");

		try{
			Livre livre = livreService.getById(this.id);
			livre.setAuteur(auteur);
			livre.setTitre(titre);
			livre.setISBN(isbn);
			livreService.update(livre);
		} catch (ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible de mettre à jour le livre");
		}

		response.sendRedirect("/TP3Ensta/livre_details?id="+this.id);
	}
}
