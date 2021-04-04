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

public class EmpruntListServlet extends HttpServlet {

	LivreService livreService = LivreServiceImpl.getInstance();
	MembreService membreService = MembreServiceImpl.getInstance();
	EmpruntService empruntService = EmpruntServiceImpl.getInstance();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
		try{
			String show = request.getParameter("show");
			if(show!=null && show.equals("all")){
				request.setAttribute("emprunts", empruntService.getList());
			}
			else{
				request.setAttribute("emprunts", empruntService.getListCurrent());
			}
			
		}
		catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible d'afficher les emprunts en cours");
		}
		
		dispatcher.forward(request, response);
	}
}
