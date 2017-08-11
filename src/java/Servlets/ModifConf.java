/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Asterisk.ProcessConfFile;
import Asterisk.CreateConf;
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
import javax.servlet.http.HttpSession;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;

/**
 *
 * @author R&D
 */
@WebServlet("/modifconf")
public class ModifConf extends HttpServlet {

    public static final String VUE = "/ModifConf.jsp";
    List<Pair<String, String>> listLines = new LinkedList<Pair<String, String>>();
    List<String> listConf;
    String parameter;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
      
        
        
        if (session.getAttribute("admin") != null) {
            if (((String) session.getAttribute("admin")).equals("true")) {
                listConf = new LinkedList(Arrays.asList(PropertyManagement.reader("AdminConfList").split(",")));
                request.setAttribute("admin", "true");
            } else {
                listConf = new LinkedList(Arrays.asList(PropertyManagement.reader("NonAdminList").split(",")));
                request.setAttribute("admin", "false");
            }

            ProcessConfFile pConf = new ProcessConfFile();
            String parameter = request.getParameter("conf");
            setParameter(parameter);
            if(parameter!=null){
                List<String> listAide= Useless.filereader(parameter);
                request.setAttribute("listAide", listAide);
                
                
                
            }
            
            try {
                listLines = pConf.GetConfFile(parameter);
            } catch (AuthenticationFailedException e) {
            } catch (TimeoutException e) {
            } catch (InterruptedException e) {
            }

            request.setAttribute("listLines", listLines);
            request.setAttribute("listConf", listConf);
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        }
        else{
            RequestDispatcher dispatcher ;
            dispatcher = request.getRequestDispatcher("/Connexion.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Pair<String, String>> listRes = new LinkedList<Pair<String, String>>();
        ProcessConfFile process = new ProcessConfFile();
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(VUE);

        if (parameter != null) {
            int res = Integer.parseInt((String) request.getParameter("value"));
            int k = 1;
            String num = Integer.toString(k);

            for (int i = 0; i < res; i++) {
                if (((String) request.getParameter("primarykey" + i)) != null && !((String) request.getParameter("primarykey" + i)).contains("category") ) {
                    Pair<String, String> paire = new Pair<String, String>((String) request.getParameter("primarykey" + i), ((String) request.getParameter("primaryval" + i)) +"="+ ((String) request.getParameter("primaryvalue" + i)));
                    listRes.add(paire);
                }
                else if(((String) request.getParameter("primarykey" + i)) != null && ((String) request.getParameter("primarykey" + i)).contains("category")){
                    Pair<String, String> paire = new Pair<String, String>((String) request.getParameter("primarykey" + i),(String) request.getParameter("primaryvalue" + i));
                    listRes.add(paire);
                }
                
            }

            int v = 0;
            while (v < 0) {
                
                if (request.getParameter("key" + num) == null) {
                    v = v + 1;
                }
                else{
                  Pair<String, String> paire = new Pair<String, String>((String) request.getParameter("key" + num), ((String) request.getParameter("value0" + num))+"="+((String) request.getParameter("value1" + num)));
                  listRes.add(paire);
                  k += 1; 
                  num = Integer.toString(k);
                }
                
                
            }
            int z=0;
            List<String> unsorted = new LinkedList<String>();
            while(z<50){
                
                if((String) request.getParameter("ANewCat"+z)!=null){
                    Pair<String, String> paire = new Pair<String,String>((String) request.getParameter("ANewCat"+z),(String) request.getParameter("NewCat"+z));
                    listRes.add(paire);
                    int e=0;
                    while(e<200){
                        if((String) request.getParameter("xteNewCat"+z+"|"+e)!=null){
                            Pair<String, String> pai =new Pair<String,String>((String) request.getParameter("xteNewCat"+z+"|"+e),(String) request.getParameter("xtNewCat"+z+"|"+e)+"="+(String) request.getParameter("xt1NewCat"+z+"|"+e));  
                            listRes.add(pai);
                        }
                        e=e+1;
                    }
                }
                z=z+1;
            } System.out.println(listRes);
            
            try {
                process.process(listRes, listLines, parameter);
            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            }
            dispatcher = request.getRequestDispatcher("/ModifConf.jsp");
            request.setAttribute("admin", "true");
            request.setAttribute("listLines", listLines);
            request.setAttribute("listConf", listConf);
            dispatcher.forward(request, response);
        } else {
            String newConf = (String) request.getParameter("newConf");
            CreateConf conf = new CreateConf();
            try {
                conf.create(newConf);

            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            }
            HttpSession session = request.getSession();
            if (((String) session.getAttribute("admin")).equals("true") && newConf!=null) {
                PropertyManagement.writer("AdminConfList", PropertyManagement.reader("AdminConfList") + "," + newConf);
            } else if (!((String) session.getAttribute("admin")).equals("true" ) && newConf!=null){
                PropertyManagement.writer("NonAdminList", PropertyManagement.reader("NonAdminList") + "," + newConf);
            }

            dispatcher.forward(request, response);
        }

    }

    public List<String> getListConf() {
        return listConf;
    }

    public void setListConf(List<String> listConf) {
        this.listConf = listConf;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
