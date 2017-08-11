/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Asterisk.BDD;
import Asterisk.UpdateConf;
import static Servlets.Connexion.VUE;
import Gestion.PropertyManagement;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
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
@WebServlet("/properties")
public class Properties extends HttpServlet{
    public static final String VUE = "/properties.jsp";
   List<String> listConfAdmin;
   List<String> listConfUsers;
   List<String> listConfExt;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
    listConfAdmin = new LinkedList(Arrays.asList(PropertyManagement.reader("AdminConfList").split(",")));
    listConfUsers = new LinkedList(Arrays.asList(PropertyManagement.reader("NonAdminList").split(",")));
    listConfExt= new LinkedList(Arrays.asList(PropertyManagement.reader("ExtList").split(",")));
        
        request.setAttribute("id",PropertyManagement.reader("id"));
        request.setAttribute("pwd",PropertyManagement.reader("pwd"));
        request.setAttribute("ip",PropertyManagement.reader("ip"));
        request.setAttribute("permit",PropertyManagement.reader("permit"));
        request.setAttribute("listConfAdmin",listConfAdmin);
        request.setAttribute("listConfUsers",listConfUsers);
        request.setAttribute("listConfExt",listConfExt);
        
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String ip = request.getParameter("IP");
        String permit = request.getParameter("permit");
        PropertyManagement prop = new PropertyManagement();
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher( VUE );
        UpdateConf manager = new UpdateConf();
        String listConfRes="";
        String listConfExtRes="";
        
        
        for(int i =0;i<listConfUsers.size();i++){
            
            if(((String) request.getParameter("Users"+i))!=null){
                listConfRes=listConfRes+((String) request.getParameter("Users"+i))+",";  
                
            }
        }
        
        for(int i =0;i<listConfExt.size();i++){
            
            if(((String) request.getParameter("Ext"+i))!=null){
                listConfExtRes=listConfExtRes+((String) request.getParameter("Ext"+i))+",";  
                
            }
        }
        
        int k=1;
        String num=Integer.toString(k);
        int v=0;
        while(v<30){
            
            System.out.println((String) request.getParameter("Added"+k));
            if(request.getParameter("Added"+k)==null){
                v=v+1;
            }
            else{
                listConfRes=listConfRes+((String) request.getParameter("Added"+k))+",";
                
            }
            k=k+1;
            num=Integer.toString(k);
        }
        
        int a=1;
        String nume=Integer.toString(a);
        int z=0;
        while(z<30){
            
            System.out.println((String) request.getParameter("AddedExt"+a));
            if(request.getParameter("Added"+a)==null){
                z=z+1;
            }
            else{
                listConfExtRes=listConfExtRes+((String) request.getParameter("AddedExt"+a))+",";
                
            }
            a=a+1;
            nume=Integer.toString(a);
        }
        
        try {
            BDD bdd = new BDD();
            bdd.AddUser(login, pwd);
            manager.UpdateConfig("Update", "manager.conf", false, PropertyManagement.reader("id"), "secret", pwd,PropertyManagement.reader("pwd"));
            PropertyManagement.writer("pwd",pwd);
            manager.UpdateConfig("Update", "manager.conf", false, PropertyManagement.reader("id"), "permit", permit,PropertyManagement.reader("permit"));
            PropertyManagement.writer("permit",permit);
            manager.UpdateConfig("RenameCat", "manager.conf", true, PropertyManagement.reader("id"), PropertyManagement.reader("id"), login,null);
             PropertyManagement.writer("id",login);
             PropertyManagement.writer("ip",ip);
             PropertyManagement.writer("NonAdminList",listConfRes);
             PropertyManagement.writer("ExtList",listConfExtRes);
             
        } catch (AuthenticationFailedException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        dispatcher = request.getRequestDispatcher("/Connexion.jsp");
        dispatcher.forward(request, response);
    }
}
