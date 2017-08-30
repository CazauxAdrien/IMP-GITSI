/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Asterisk.ProcessConfFile;
import Asterisk.UpdateConf;
import Gestion.PropertyManagement;
import static Servlets.ModifConf.VUE;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;

/**
 *
 * @author R&D
 */
@WebServlet("/dialplan")
public class Dialplan extends HttpServlet {

    public static final String VUE = "/Dialplan.jsp";
    List<String> listConfi;
    List<Pair<String, String>> listLines = new LinkedList<Pair<String, String>>();
    String parameter;
    Map<String, Map<String, List<Pair<String, String>>>> listDeroul = new HashMap<String, Map<String, List<Pair<String, String>>>>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            List<Pair<String, String>> list = new LinkedList<Pair<String, String>>();
            ProcessConfFile conf= new ProcessConfFile();
            try {
                list = conf.GetConfFile("admin.conf");

            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ModifConf.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (((String) session.getAttribute("admin")).equals("true")) {
                request.setAttribute("admin", "true");
            } else if (((String) session.getAttribute("sousAdmin"))!=null) {
                
                request.setAttribute("admin", "false");
                request.setAttribute("sousAdmin", "true");
            }
            else{
                request.setAttribute("admin", "false");
            }
            
            for(int r=0;r<list.size();r++){
                    if(list.get(r).getValue().contains("ExtList")){
                        listConfi= new LinkedList(Arrays.asList(list.get(r).getValue().split("=")[1].split(",")));
                    }
                }
            ProcessConfFile pConf = new ProcessConfFile();
            String parameter = request.getParameter("conf");
            setParameter(parameter);
            try {
                listLines = pConf.GetConfFile(parameter);
                listDeroul = pConf.GetConfExtenstions(parameter);

            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("listDeroul", listDeroul);
            request.setAttribute("listConf", listConfi);

            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        }
        else{
            RequestDispatcher dispatcher ;
            dispatcher = request.getRequestDispatcher("/Connexion.jsp");
            dispatcher.forward(request, response);
        }
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(VUE);
        int size0 = Integer.parseInt((String) request.getParameter("size0"));
        int size1 = Integer.parseInt((String) request.getParameter("size1"));
        int size2 = Integer.parseInt((String) request.getParameter("size2"));

        String action = (String) request.getParameter("action");
        String categoryCopied = (String) request.getParameter("categoryCopied");
        String numberCopied = (String) request.getParameter("numberCopied");
        String categoryPaste = (String) request.getParameter("categoryPaste");
        String numberCreated = (String) request.getParameter("numberCreated");
        String categoryCreated = (String) request.getParameter("categoryCreated");
        String numberCreated1 = (String) request.getParameter("numberCreated1");

        List<String> list = new LinkedList<String>();
        List<Pair<String, String>> finale = new LinkedList<Pair<String, String>>();
        List<Pair<String, String>> newCopy = new LinkedList<Pair<String, String>>();
        List<Pair<String, String>> afterCopy = new LinkedList<Pair<String, String>>();

        ProcessConfFile process = new ProcessConfFile();
        UpdateConf manager = new UpdateConf();
        
        if (action.equals("Copier")) {
            String cate = "";
            String number = "";
            String trueCat = "";
            List<Pair<String, String>> copy = listDeroul.get((String) request.getParameter(categoryCopied) + "=" + categoryCopied).get(numberCopied);
            if (!categoryCreated.equals("Créer Categorie")) {
                trueCat = categoryCreated;
                number = numberCreated1;
                try {
                    manager.UpdateConfig("NewCat", parameter, true, categoryCreated, "", "", null);
                    manager.UpdateConfig("Append", parameter, true, categoryCreated, "test", "test", null);
                    listLines = process.GetConfFile(parameter);

                } catch (AuthenticationFailedException ex) {
                    Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TimeoutException ex) {
                    Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int r = 0; r < listLines.size(); r++) {
                    if (listLines.get(r).getValue().contains(categoryCreated)) {
                        cate = listLines.get(r).getKey().split("-")[1];
                    }
                }
            } else {
                trueCat = (String) request.getParameter(categoryPaste) + "=" + categoryPaste;
                number = numberCreated;
                cate = trueCat.split("-")[1].split("=")[0];
            }

            for (int o = 0; o < copy.size(); o++) {

                String cat = copy.get(o).getKey().split("-")[1];
                String newcat = copy.get(o).getKey().replaceFirst(cat, cate);
                String exten = copy.get(o).getValue().split("=", 2)[0];

                if (exten.contains("exten")) {
                    String num = copy.get(o).getValue().split("=", 3)[1].split(",")[0];

                    String newval = copy.get(o).getValue().replaceAll(num, number);
                    Pair<String, String> pair = new Pair<String, String>(newcat, newval);
                    newCopy.add(pair);
                } else {

                    Pair<String, String> pair = new Pair<String, String>(newcat, copy.get(o).getValue());
                    newCopy.add(pair);
                }
            }

            int p = 0;
            while (!(listLines.get(p).toString()).contains(trueCat)) {
                p = p + 1;
            }
            while (p < listLines.size() && !listLines.get(p).toString().contains("category")) {

                p = p + 1;
            }

            afterCopy.addAll(listLines.subList(0, p + 1));
            afterCopy.addAll(newCopy);
            afterCopy.addAll(listLines.subList(p + 1, listLines.size()));

            try {

                process.process(afterCopy, listLines, parameter);
                manager.UpdateConfig("Delete", parameter, true, categoryCreated, "test", "test", null);

            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            }
            dispatcher = request.getRequestDispatcher("/Accueil.jsp");
            dispatcher.forward(request, response);

        } else if (action.equals("Appliquer")) {
            int v = 0;
            for (int i = 0; i < size0; i++) {
                if (request.getParameter("cat" + i) != null) {
                    list.add((String) request.getParameter("categ" + i) + "=" + (String) request.getParameter("cat" + i));

                    List<String> unsorted = new LinkedList<String>();

                    for (int j = 0; j < size1+1; j++) {

                        for (int k = 0; k < size2; k++) {

                            if (((String) request.getParameter("ext" + i + "|" + j + "|" + k)) != null) {
                                
                                unsorted.add((String) request.getParameter("exte" + i + "|" + j + "|" + k) + "=" + (String) request.getParameter("ext" + i + "|" + j + "|" + k));
                            }

                            int a = 0;
                            while (v < 200) {
                                a = a + 1;

                                if (((String) request.getParameter("ext" + i + "|" + j + "|" + k + "|" + a)) != null) {
                                    unsorted.add((String) request.getParameter("exte" + i + "|" + j + "|" + k + "|" + a) + "=" + (String) request.getParameter("ext" + i + "|" + j + "|" + k + "|" + a));
                                } else {
                                    v = v + 1;
                                }
                            }
                            v = 0;

                        }
                        

                    }
                    Collections.sort(unsorted);
                    list.addAll(unsorted);
                }

            }
            int k=0;
            List<String> unsorted = new LinkedList<String>();
            while(k<50){
                
                if((String) request.getParameter("AnewCat"+k)!=null){
                    unsorted.add((String) request.getParameter("AnewCat"+k)+"="+(String) request.getParameter("newCat"+k));
                    int e=0;
                    while(e<50){
                        if((String) request.getParameter("extenewCat"+k+"|"+e)!=null){
                            unsorted.add((String) request.getParameter("extenewCat"+k+"|"+e)+"="+(String) request.getParameter("extnewCat"+k+"|"+e));  
                        }
                        e=e+1;
                    }
                }
                k=k+1;
            }
            if(unsorted.size()>0){
                list.addAll(unsorted);
            }
            
            
            for (int m = 0; m < list.size(); m++) {
                
                
                String key = list.get(m).split("=")[0];
                String val = list.get(m).split("=", 2)[1];
                Pair<String, String> paire = new Pair<String, String>(key, val);
                finale.add(paire);

            }
           

            try {
                process.process(finale, listLines, parameter);

            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Dialplan.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(session.getAttribute("admin") != null && session.getAttribute("admin").equals("true")){
            dispatcher = request.getRequestDispatcher("/Accueil.jsp");
            }
            else if(session.getAttribute("sousAdmin")!=null){
               dispatcher = request.getRequestDispatcher("/Accueil_2.jsp"); 
            }
            else{
                dispatcher = request.getRequestDispatcher("/Accueil_1.jsp"); 
            }
            dispatcher.forward(request, response);
        }

    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
