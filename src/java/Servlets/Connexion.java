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
import Asterisk.ProcessConfFile;
import Gestion.PropertyManagement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

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
        ProcessConfFile process = new ProcessConfFile();
        
        boolean success=false;
        boolean user=false;
        boolean adm=false;
        boolean ssadm=false;
            try {
                List<Pair<String,String>> line = process.GetConfFile("users.conf");
                for(int o=0;o<line.size();o++){
                    if(line.get(o).getValue().contains(login)){
                        user=true;
                    }
                }
                List<Pair<String,String>> linez = process.GetConfFile("admin.conf");
                for(int o=0;o<linez.size()-1;o++){
                    if(linez.get(o).getValue().contains(login) && linez.get(o+1).getValue().contains(pwd)){
                        ssadm=true;
                    }
                }
                List<Pair<String,String>> lines = process.GetConfFile("manager.conf");
                for(int o=0;o<lines.size()-1;o++){
                    if(lines.get(o).getValue().contains(login) && lines.get(o+1).getValue().contains(pwd)){
                        adm=true;
                    }
                }
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
            
		if( PropertyManagement.reader("pwd").equals(pwd) && PropertyManagement.reader("id").equals(login) && !adm){
                    session.setAttribute("id", login);
                    session.setAttribute("pwd",log.getPwd());
                    session.setAttribute("admin","true");
                    dispatcher = request.getRequestDispatcher("/properties.jsp");
                }
                else if(adm){
                    session.setAttribute("id", login);
                    session.setAttribute("pwd",log.getPwd());
                    session.setAttribute("admin","true");
                    dispatcher = request.getRequestDispatcher("/Accueil.jsp");
                }
                else if(ssadm){
                    session.setAttribute("id", login);
                    session.setAttribute("pwd",log.getPwd());
                    session.setAttribute("admin","false");
                    session.setAttribute("sousAdmin","true");
                    dispatcher = request.getRequestDispatcher("/Accueil_2.jsp");
                }
                else if(success && log.getPwd().equals(pwd) && user){
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
	

