package Servlets;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;

import Asterisk.BDD;
import Gestion.PropertyManagement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
 
@WebServlet("/connexion")
public class Connexion extends HttpServlet{

	
	public static final String VUE        = "/Connexion.jsp";
	private static final long serialVersionUID = -8548853358170235716L;

	
	public Connexion() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();   
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher( VUE );
        
        BDD log = new BDD();
        log.setId(login);
        
        boolean success=false;
            try {
                success = log.GetUser();
            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
		if( PropertyManagement.reader("pwd").equals(pwd) && PropertyManagement.reader("id").equals(login)){
                    session.setAttribute("id", login);
                    session.setAttribute("pwd",log.getPwd());
                    session.setAttribute("admin","true");
                    dispatcher = request.getRequestDispatcher("/Accueil.jsp");
                }
                
                else if(success && log.getPwd().equals(pwd)){
                        session.setAttribute("id", login);
                        session.setAttribute("pwd",log.getPwd());
                        session.setAttribute("admin","false");
			dispatcher = request.getRequestDispatcher("/Accueil_1.jsp");
                     
                }else{
                    
			dispatcher = request.getRequestDispatcher("/Connexion.jsp");
		}
		
        dispatcher.forward(request, response);
	}
         
}
	

