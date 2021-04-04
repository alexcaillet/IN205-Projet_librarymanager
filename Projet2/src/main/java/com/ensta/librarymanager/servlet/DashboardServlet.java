package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.modele.*;

public class DashboardServlet extends HttpServlet {

	LivreService livreService = LivreServiceImpl.getInstance();
	MembreService membreService = MembreServiceImpl.getInstance();
	EmpruntService empruntService = EmpruntServiceImpl.getInstance();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
		//Ajout des effectifs des emprunts, livres et membres à la request
		try{
			request.setAttribute("livres_count", livreService.count());
			request.setAttribute("membres_count", membreService.count());
			request.setAttribute("emprunts_count", empruntService.count());
		} catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

		//Ajout de la liste des emprunts en cours à la request
		try{
			request.setAttribute("emprunts", empruntService.getListCurrent());
		}
		catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible d'afficher les emprunts en cours");
		}
		dispatcher.forward(request, response);	
	}
}
