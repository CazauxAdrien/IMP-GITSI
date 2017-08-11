/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Asterisk.BDD;
import Asterisk.UpdateConf;
import static Servlets.Connexion.VUE;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;

/**
 *
 * @author R&D
 */
@WebServlet("/ajout_util")
public class Ajout_util extends HttpServlet{
    public static final String VUE        = "/Ajout_util.jsp";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String login = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String full = request.getParameter("fullname");
        String permit = request.getParameter("permit");
        
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher( VUE );
        UpdateConf manager = new UpdateConf();
        
        BDD log = new BDD();
        boolean success=false;
        try {
            success=log.AddUser(login, pwd);
            
            manager.UpdateConfig("NewCat", "users.conf", true, login, "", "",null);
            manager.UpdateConfig("Append", "users.conf", true,login , "type", "friend",null);
            manager.UpdateConfig("Append", "users.conf", true,login , "host", "dynamic",null);
            manager.UpdateConfig("Append", "users.conf", true,login , "dtmfmode", "rfc2833",null);
            manager.UpdateConfig("Append", "users.conf", true,login , "disallow", "all",null);
            manager.UpdateConfig("Append", "users.conf", true,login , "allow", "alaw",null);
            manager.UpdateConfig("Append", "users.conf", true,login , "fullname", full+" <"+login+">",null);
            manager.UpdateConfig("Append", "users.conf", true,login , "username", login,null);
            manager.UpdateConfig("Append", "users.conf", true,login , "secret", pwd,null);
            manager.UpdateConfig("Append", "users.conf", true,login , "context", "from-internal-sip",null);
            
            manager.UpdateConfig("NewCat", "manager.conf", true, login, "", "",null);
            manager.UpdateConfig("Append", "manager.conf", true,login , "secret", pwd,null);
            manager.UpdateConfig("Append", "manager.conf", true,login , "permit", permit,null);
            manager.UpdateConfig("Append", "manager.conf", true,login , "read", "all",null);
            manager.UpdateConfig("Append", "manager.conf", true,login , "write", "all",null);
            
        } catch (IllegalStateException ex) {
            Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AuthenticationFailedException ex) {
            Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispatcher = request.getRequestDispatcher("/Accueil.jsp");
        dispatcher.forward(request, response);
    }
}
