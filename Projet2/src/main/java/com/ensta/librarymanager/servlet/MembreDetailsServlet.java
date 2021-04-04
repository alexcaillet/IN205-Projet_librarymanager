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

public class MembreDetailsServlet extends HttpServlet{

	EmpruntService empruntService = EmpruntServiceImpl.getInstance();
	MembreService membreService = MembreServiceImpl.getInstance();
	Membre membre = new Membre();
	int id;

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
		this.id = Integer.valueOf(request.getParameter("id"));

		try{
			membre = membreService.getById(this.id);
			request.setAttribute("id", this.id);
			request.setAttribute("nom", membre.getNom());
			request.setAttribute("prenom", membre.getPrenom());
			request.setAttribute("adresse", membre.getAdresse());
			request.setAttribute("email", membre.getEmail());
			request.setAttribute("telephone", membre.getTelephone());
			request.setAttribute("abonnement", membre.getAbonnement());
			request.setAttribute("emprunts_membre", empruntService.getListCurrentByMembre(membre.getPrimaryKey()));
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible d'obtenir les détails du livre");
		}
		dispatcher.forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		Abonnement abonnement = Abonnement.valueOf(request.getParameter("abonnement"));

		try{
			Membre membre = membreService.getById(this.id);
			membre.setNom(nom);
			membre.setPrenom(prenom);
			membre.setAdresse(adresse);
			membre.setEmail(email);
			membre.setTelephone(telephone);
			membre.setAbonnement(abonnement);

			membreService.update(membre);
		} catch (ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible de mettre à jour le livre");
		}

		response.sendRedirect("/TP3Ensta/membre_details?id="+this.id);
	}
}
