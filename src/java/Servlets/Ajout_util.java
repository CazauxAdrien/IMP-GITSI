/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Asterisk.BDD;
import Asterisk.UpdateConf;
import Gestion.PropertyManagement;
import static Servlets.Connexion.VUE;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;

/**
 *
 * @author R&D
 */
@WebServlet("/util")
public class Ajout_util extends HttpServlet{
    public static final String VUE        = "/Ajout_util.jsp";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (((String) session.getAttribute("admin")).equals("true")) {
            request.setAttribute("admin", "true");
        } else if (((String) session.getAttribute("sousAdmin"))!=null) {

            request.setAttribute("admin", "false");
            request.setAttribute("sousAdmin", "true");
        }
        else{
            request.setAttribute("admin", "false");
        }
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String login = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String full = request.getParameter("fullname");
        String permit = request.getParameter("permit");
        String loginsup = request.getParameter("idsup");
        String pwdsup = request.getParameter("pwdsup");
        String sousAdmin = request.getParameter("sousAdmin");
        
        List<String> listConf = new LinkedList(Arrays.asList(PropertyManagement.reader("AdminConfList").split(",")));
        
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher( VUE );
        UpdateConf manager = new UpdateConf();
        String action = (String) request.getParameter("action");
        String act = (String) request.getParameter("act");
        
        if(act!=null && act.equals("Ajouter") && sousAdmin.equals("Non")){
            BDD log = new BDD();
            boolean success=false;
            try {
                success=log.AddUser(login, pwd);

                manager.UpdateConfig("NewCat", "users.conf", true, login, "", "",null);
                manager.UpdateConfig("Append", "users.conf", true,login , "type", "friend",null);
                manager.UpdateConfig("Append", "users.conf", true,login , "secret", pwd,null);
                manager.UpdateConfig("Append", "users.conf", true,login , "fullname", full,null);


            } catch (IllegalStateException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(act!=null && act.equals("Ajouter") && sousAdmin.equals("Oui")){
            BDD log = new BDD();
            boolean success=false;
            try {
                success=log.AddUser(login, pwd);

                manager.UpdateConfig("NewCat", "admin.conf", true, login, "", "",null);
                manager.UpdateConfig("Append", "admin.conf", true,login , "secret", pwd,null);


            } catch (IllegalStateException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(action!=null && action.equals("Supprimer")){
            try {
                for(int i=0;i<listConf.size();i++){
                   manager.UpdateConfig("DelCat", listConf.get(i), true, loginsup, loginsup, "",null); 
                }
                
            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Ajout_util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(session.getAttribute("admin") != null && session.getAttribute("admin").equals("true")){
            dispatcher = request.getRequestDispatcher("/Accueil.jsp");
        }
        else{
           dispatcher = request.getRequestDispatcher("/Accueil_2.jsp"); 
        }
        
        dispatcher.forward(request, response);
    }
}
