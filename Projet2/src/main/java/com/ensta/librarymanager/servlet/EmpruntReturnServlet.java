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

public class EmpruntReturnServlet extends HttpServlet {

	EmpruntService empruntService = EmpruntServiceImpl.getInstance();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
		try{
			request.setAttribute("emprunts_cur", empruntService.getListCurrent());
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible d'afficher les emprunts en cours");
		}
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idEmprunt = Integer.valueOf(request.getParameter("id"));

		try{
			empruntService.returnBook(idEmprunt);
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			throw new ServletException("Impossible de retourner l'emprunt");
		}
		
		response.sendRedirect("emprunt_list");
	}
}
