<%@page import="java.lang.reflect.Array"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="javafx.util.Pair"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr-fr" lang="fr-fr" dir="ltr">

    <head>
        <!-- BALISES META-->
        <title>Gipsi Web</title>



        <meta content="Gipsi Web" name="keywords" /> 
        <meta content="Lapinrouge" name="author"/>
        <meta content="text/html; charset=UTF-8" http-equiv="content-type"/>



        <!--FEUILLE DE STYLE-->
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
        <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.css" type="text/css"/>

        <style type="text/css">
            .col-lg-20 { height: 180px; }

        </style>
        <!--FAVICON-->
        <link rel="icon" type="image/gif" href="favicon.gif"/>

        <!--POLICES-->

        <link href='http://fonts.googleapis.com/css?family=Play' rel='stylesheet' type='text/css'>
            <link href='https://fonts.googleapis.com/css?family=Roboto:400,700,900' rel='stylesheet' type='text/css'>	







                </head>
                <div class="col-lg-20">
                    <div id="bgmenu">

                        <div id="nav">
                            <div id="logogipsi"><img src="graph/gipsi.png" alt="Gipsi" style="width: 165px;"/></div>

                            <div id="horloge"></div>

                            <script type="text/javascript">
                                window.onload = function () {
                                    horloge('horloge');
                                };

                                function horloge(el) {
                                    if (typeof el == "string") {
                                        el = document.getElementById(el);
                                    }
                                    function actualiser() {
                                        var date = new Date();
                                        var str = date.getHours();
                                        str += ':' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
                                        el.innerHTML = str;
                                    }
                                    actualiser();
                                    setInterval(actualiser, 60000);

                                }
                            </script>
                            <%String admin = (String) request.getAttribute("admin");
                                if (admin.equals("true")) {%>
                            <div id="logogipsi" style="padding-left: 30px; padding-top: 5px;"><a href="/GASTON/accueil" title="Accueil"><img src="graph/mini_home.png" alt="Accueil" style="width: 40px;"/></a>   
                            </div>
                            <%} else {%>
                            <div id="logogipsi" style="padding-left: 30px; padding-top: 5px;"><a href="/GASTON/accueil_1" title="Accueil"><img src="graph/mini_home.png" alt="Accueil" style="width: 40px;"/></a>   
                            </div>
                            <%}%>
                            <div id="logogipsi2" style="float: right; padding-right: 30px; padding-top: 10px;"><a href="/GASTON/connexion" title="Déconnexion"><img src="graph/mini_deconnexion.png"  style="width: 30px;"/></a>     

                            </div>

                        </div>
                    </div>
                    <body>

                        <div id="conteneur">



                            <!--CONTENU-->	
                            <script type="text/javascript">

                                
                                var count = 1;
                                function addExt(divName,next,line) {
                                    var newdiv = document.createElement('li');
                                    newdiv.innerHTML = " <a id='line" + count + "' ><input type='hidden' id='"+next+"|"+count+"' name='exte"+next+"|"+count+"' value='"+line+"' ><input id='"+next+"|"+count+"' type ='text' name='ext"+next+"|"+count+"' value='' )\"" + " size='100px'><input id='removeExt' type='button'  value='x' onClick=" + "\"removeConf('line" + count + "')\"" + ";></a>";
                                    document.getElementById(divName).appendChild(newdiv);
                                    count++;

                                }
                                
                                var countcat = 1;
                                function addCat(divName,name,key,key2) {
                                    var newdiv = document.createElement('li');
                                    
                                    newdiv.innerHTML = " <a id='"+name + countcat + "' ><input type='hidden' id='A"+name+countcat+"' name='A"+name+countcat+"' value='"+key+countcat+"'><input id='"+name + countcat + "' type ='text' name='"+name + countcat + "' value='' )\"" + " size='30px'><input id='removeCat' type='button'  value='x' onClick=" + "\"removeConf('"+name + countcat + "')\"" + "; ></a>\n\
                            <ul>\n\
<li id='num"+name+countcat+"'>\n\
<a id='Num'>\n\
<input id='cate' name='num"+name+countcat+"' value='Ajout exten' size='10px' >\n\
</a>\n\
<ul>\n\
<div id='dynamicExtCat"+countcat+"'>\n\
</div>\n\
<li>\n\
<a id='line'>\n\
<input id='adde' type='button' value='Add another line' onClick=" + "\"addExt('dynamicExtCat"+countcat+"','"+name+countcat+"','"+key2+countcat+"')\"" + ";>\n\
</input>\n\
</a>\n\
</li>\n\
</ul>\n\
</li>\n\
</ul>\n\
";
       
                                    document.getElementById(divName).appendChild(newdiv);
                                    counter++;

                                }
                                function removeConf(divName) {
                                    var a = document.getElementById(divName);
                                    a.remove();
                                }
                                function post(val) {
                                    document.getElementById("action").value = val;
                                }

                            </script>

                            <%
                                String parameter = request.getParameter("conf");

                                Map<String, Map<String, List<Pair<String, String>>>> listDeroul = (Map<String, Map<String, List<Pair<String, String>>>>) request.getAttribute("listDeroul");
                                LinkedList<String> listConf = (LinkedList<String>) request.getAttribute("listConf");
                                Set listKeys = listDeroul.keySet();
                                int plus = listKeys.size();
                                int plus1 = 0;
                                int plus2 = 0;
                                int cat = 0;

                            %>
                            <div id="corps">

                                <div clas="row">
                                    <div id="corps" align="center" col-lg-12>
                                        
                                        <div id="menu">
                                            
                                            <ul id="onglets">
                                                
                                                <% if (parameter == null) {%>
                                                <li class="active"><a href="dialplan">Accueil</a></li>    
                                                    <%}

                                                        if (parameter != null) {%>
                                                <li><a href="dialplan">Accueil</a></li>
                                                
                                                    <%}%>
                                                    <% for (int i = 0; i < listConf.size(); i++) {

                                                            if (listConf.get(i).equals(parameter)) {%>
                                                <li class="active"><a href="dialplan?conf=<%out.print(listConf.get(i));%>"><%out.print(listConf.get(i));%></a></li>
                                                
                                                    <%} else {%>
                                                <li><a href="dialplan?conf=<%out.print(listConf.get(i));%>"><%out.print(listConf.get(i));%></a></li>
                                                    <%}%>

                                                <%}%>
                                            </ul>

                                        </div>

                                    </div>
                                </div>     
                                            
                                <%if(parameter!=null){%>
                                <div class="row">
                                    <div id='confForm' col-lg-12>
                                    </div>
                                </div>
                                
                                <div clas="row">
                                    
                                    <form action="dialplan" method="post">

                                        <input type="hidden" id="action" name="action" value=""></input>

                                        <div id="corps" align="center" ol-lg-7>
                                            
                                            <hh>Copier une configuration</hh>
                                            
                                            <br></br>
                                            
                                            <div id="Copy">
                                                
                                                <br></br>
                                                
                                                <%for (Iterator l = listKeys.iterator(); l.hasNext();) {
                                                            String key = (String) l.next();%>
                                                            
                                                            <input type="hidden" name="<%out.print(key.split("=")[1]);%>" value="<%out.print(key.split("=")[0]);%>"></input>
                                                    
                                                    <%}%>
                                                    
                                                <select id="from1" name="categoryCopied">
                                                    
                                                    <option>Sélectionner categorie</option>
                                                    <%for (Iterator l = listKeys.iterator(); l.hasNext();) {
                                                            String key = (String) l.next();%>
                                                            
                                                    <option><%out.print(key.split("=")[1]);%></option>
                                                    
                                                    <%}%>
                                                    
                                                </select>

                                                <br></br>
                                                
                                                <select id="from2" name="numberCopied">
                                                    
                                                    <option>Sélectionner numero</option>
                                                    
                                                    <%for (Iterator l = listKeys.iterator(); l.hasNext();) {
                                                            String key = (String) l.next();
                                                            Map<String, List<Pair<String, String>>> val = (Map<String, List<Pair<String, String>>>) listDeroul.get(key);
                                                            Set listNum = val.keySet();
                                                            for (Iterator q = listNum.iterator(); q.hasNext();) {
                                                                String key1 = (String) q.next();%>
                                                                
                                                    <option><%out.print(key1);%></option>
                                                    
                                                        <%}%>
                                                    <%}%>
                                                    
                                                </select>
                                                    
                                                <br></br>
                                                
                                                <select id="from1" name="categoryPaste">
                                                    
                                                    <option>Sélectionner categorie</option>
                                                    
                                                    <%for (Iterator l = listKeys.iterator(); l.hasNext();) {
                                                            String key = (String) l.next();%>
                                                            
                                                            <option><%out.print(key.split("=")[1]);%></option>
                                                    
                                                    <%}%>
                                                </select>
                                                
                                                <br></br>
                                                
                                                <input type="text" name="numberCreated" value="Créer Numéro"></input>
                                                
                                                <br></br>
                                                
                                                <hhh>Ou</hhh>
                                                
                                                <br></br>
                                                
                                                <input type="text" name="categoryCreated" value="Créer Categorie"></input>
                                                
                                                <input type="text" name="numberCreated1" value="Créer Numéro"></input>
                                                
                                                <br></br>
                                            </div>
                                                
                                            <input type="submit" name="Modifier" value="Copier" onClick="post('Copier');"/>
                                            
                                            <bottom margin-top="200px"></bottom>  
                                            
                                        </div>
                                                
                                        
                                                    
                                            <input type="submit" name="Modifier" value="Appliquer" onClick="post('Appliquer');"/>
                                            <br></br>
                                            <ul id="menuE">

                                                <li><a>Categories</a>
                                                    <ul id="categories">
                                                        <div id="dynamicCat">
                                                        <%
                                                            plus2 = 0;
                                                            for (Iterator l = listKeys.iterator(); l.hasNext();) {
                                                                int num = 0;
                                                                String key = (String) l.next();
                                                                Map<String, List<Pair<String, String>>> val = (Map<String, List<Pair<String, String>>>) listDeroul.get(key);
                                                                Set listNum = val.keySet();
                                                                if(val.size()>plus1){
                                                                    plus1 = listNum.size();
                                                                }%>

                                                        
                                                        <li id="cate<%out.print(cat);%>"><a><input type="hidden" id="cate" name="categ<%out.print(cat);%>" value="<%out.print(key.split("=")[0]);%>" ><input id="cate" name="cat<%out.print(cat);%>" value="<%out.print(key.split("=")[1]);%>" size="30px"><input id='removeExt' type='button'  value='x' onClick="removeConf('cate<%out.print(cat);%>');"></a>
                                                            <ul>
                                                                <div id="dynamicnum<%out.print(cat);%>">
                                                                <%   for (Iterator q = listNum.iterator(); q.hasNext();) {
                                                                        int exten = 0;
                                                                        String key1 = (String) q.next();
                                                                        List<Pair<String, String>> val1 = (List<Pair<String, String>>) val.get(key1);
                                                                        if (val1.size() > plus2) {
                                                                            plus2 = val1.size();
                                                                        }%>

                                                                
                                                                <li id="num<%out.print(cat);%>|<%out.print(num);%>"><a id="Num" ><input id="cate" disabled="disabled" name="num<%out.print(cat);%>|<%out.print(num);%>" value="<%out.print(key1);%>" size="10px"><input id='removeExt' type='button'  value='x' onClick="removeConf('num<%out.print(cat);%>|<%out.print(num);%>');"></a>
                                                                    <ul> 
                                                                        <div id="dynamicExt<%out.print(cat);%>|<%out.print(num);%>">
                                                                        <%for (int w = 0; w < val1.size(); w++) {%>
                                                                        
                                                                        <li id="ext<%out.print(cat);%>|<%out.print(num);%>|<%out.print(exten);%>"><a id ="line" ><input type="hidden" id="cate" name="exte<%out.print(cat);%>|<%out.print(num);%>|<%out.print(exten);%>" value="<%out.print(val1.get(w).getKey());%>" ><input id="cate" name="ext<%out.print(cat);%>|<%out.print(num);%>|<%out.print(exten);%>" value="<%out.print(val1.get(w).getValue());%>" style="width:90%;"><input id='removeExt' type='button'  value='x' onClick="removeConf('ext<%out.print(cat);%>|<%out.print(num);%>|<%out.print(exten);%>');"></a></li>
                                                                                
                                                                            <%exten = exten + 1;
                                                                        }%>
                                                                        </div>
                                                                        <li><a id ="line"><input id='adde' type="button" value="Add another line" onClick="addExt('dynamicExt<%out.print(cat);%>|<%out.print(num);%>','<%out.print(cat);%>|<%out.print(num);%>|<%out.print(exten-1);%>','<%out.print(val1.get(exten-1).getKey());%>')" size="700px"></input></a></li>
                                                                    </ul>
                                                                </li>
                                                                <%num = num + 1;
                                                                    }%>
                                                                    
                                                                    <li id="num<%out.print(cat);%>|<%out.print(num);%>"><a id="Num" ><input id="cate" name="num<%out.print(cat);%>|<%out.print(num);%>" value="Ajout exten" size="10px"></a>
                                                                    <ul>
                                                                        <div id="dynamicExt<%out.print(cat);%>|<%out.print(num);%>">
                                                                        </div>
                                                                        <li><a id ="line"><input id='adde' type="button" value="Add another line" onClick="addExt('dynamicExt<%out.print(cat);%>|<%out.print(num);%>','<%out.print(cat);%>|<%out.print(num);%>','line-<%out.print(key.split("=")[0].split("-")[1]);%>')" size="700px"></input></a></li>

                                                                    </ul>     
                                                                </li>
                                                                
                                                                </div>
                                                                
                                                                
                                                            </ul>  
                                                        </li>
                                                            
                                                        <%cat = cat + 1;
                                                       }%>     
                                                        </div>
                                                        
                                                       <li ><a><input id='adde' type="button" value="Add another line" onClick="addCat('dynamicCat','newCat','category-added','line-added')" ></input></a></li>
                                                       
                                                        <input type="hidden" name="size0" value="<%out.print(plus);%>"></input>
                                                        <input type="hidden" name="size1" value="<%out.print(plus1);%>"></input>
                                                        <input type="hidden" name="size2" value="<%out.print(plus2);%>"></input>


                                                    </ul>
                                                </li> 

                                            </ul>

                                    </form>


                                </div>
                                <%}
                                else{%>
                                <div class="row">
                                    <div id='confForm' col-lg-12>
                                    </div
                                </div>
                                <div align="center">
                                    <hf >Sélectionnez un fichier de configuration</hf>
                                </div>
                                
                                <%}%>
                            </div>

                            <%for (int v = 0; v < (plus + plus1 + plus2); v++) {%>
                            <br></br>

                            <%}%>
                        </div>

                        <!--FERMETURE CONTENEUR-->


                    </body>
                    </html>