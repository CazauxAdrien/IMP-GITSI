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

        <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.css" type="text/css"/>
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
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
                            <div id="logogipsi" style="padding-left: 30px; padding-top: 5px;"><a href="/GASTON/accueil" title="Accueil"><img src="graph/mini_home.png" alt="Accueil" style="width: 40px;"/></a>   

                            </div>
                            <div id="logogipsi2" style="float: right; padding-right: 30px; padding-top: 10px;"><a href="/GASTON/connexion" title="Déconnexion"><img src="graph/mini_deconnexion.png"  style="width: 30px;"/></a>     

                            </div>

                        </div>
                    </div>
                    <body id="prop">
                        <%
                            String id = (String) request.getAttribute("id");
                            String pwd = (String) request.getAttribute("pwd");
                            String ip = (String) request.getAttribute("ip");
                            String permit = (String) request.getAttribute("permit");
                            LinkedList<String> listConfAdmin = (LinkedList<String>) request.getAttribute("listConfAdmin");
                            LinkedList<String> listConfssAdmin = (LinkedList<String>) request.getAttribute("listConfssAdmin");
                            LinkedList<String> listConfUsers = (LinkedList<String>) request.getAttribute("listConfUsers");
                            LinkedList<String> listConfExt = (LinkedList<String>) request.getAttribute("listConfExt");
                        %>
                        <div id="conteneur">



                            <!--CONTENU-->	
                            <script type="text/javascript">

                                var counter = 1;
                                function addConf(divName, val) {
                                    var newdiv = document.createElement('li');
                                    newdiv.innerHTML = " <input id='Added" + counter + "' type ='text' name='Added" +divName+counter + "' value='" + val + "' onclick=" + "\"removeConf('Added" + divName+counter + "')\"" + " >";
                                    document.getElementById(divName).appendChild(newdiv);
                                    counter++;

                                }
                                function removeConf(divName) {
                                    var a = document.getElementById(divName);
                                    a.remove();
                                }

                            </script>


                            <div id="corps">

                                <div align="center">
                                    <h1>Modifier les propriétés</h1>
                                    <form class="form-horizontal" role="form" action="properties" method="post" >
                                        <div>


                                            <div class="panel-body" id="Ajout">

                                                <div class="form-group">
                                                    <br><label for="inputEmail3" class="col-sm-3 control-label">
                                                            Identifiant administrateur</label>
                                                        <div class="col-sm-9">
                                                            <input type="Identifiant" class="form-control" name="id" value=<%out.print(id);%> required="">
                                                        </div>
                                                </div>
                                                        
                                                <div class="form-group">
                                                    <label for="inputPassword3" class="col-sm-3 control-label">
                                                        Password administrateur</label>
                                                    <div class="col-sm-9">
                                                        <input type="password" class="form-control" name="pwd" value=<%out.print(pwd);%> required="">
                                                    </div>
                                                </div>
                                                    
                                                <div class="form-group">
                                                    <label for="inputPassword3" class="col-sm-3 control-label">
                                                        IP</label>
                                                    <div class="col-sm-9">
                                                        <input type="Identifiant" class="form-control" name="IP" value=<%out.print(ip);%> required="">
                                                    </div>
                                                </div>
                                                    
                                                <div class="form-group">
                                                    <label for="inputPassword3" class="col-sm-3 control-label">
                                                        Permit</label>
                                                    <div class="col-sm-9">
                                                        <input type="Identifiant" class="form-control" name="permit" value=<%out.print(permit);%> required="">
                                                    </div>
                                                </div>
                                                    
                                                <br></br>
                                                
                                                <div>
                                                    <ul id="menu-demo2">
                                                        
                                                        <li><a href="#">Fichiers de configuration</a>
                                                            <ul>
                                                                <% for (int i = 0; i < listConfAdmin.size(); i++) {%>
                                                                <li size="25px"><input  id='<%out.print(i);%>' type ="text"  name='Admin<%out.print(i);%>' value='<%out.print(listConfAdmin.get(i));%>' onclick="addConf('dynamic', '<%out.print(listConfAdmin.get(i));%>')"></li>
                                                                    <%}%>
                                                            </ul>
                                                        </li>
                                                            
                                                        <img src="graph/pictos/b3fleche_ferme.png"></img>
                                                        
                                                        <li><a href="#">Fichiers Accessibles</a>
                                                            <ul id="dynamic">
                                                                <% for (int i = 0; i < listConfUsers.size(); i++) {%>
                                                                <li size="25px"><input  id='Us<%out.print(i);%>' type ="text"  name='Users<%out.print(i);%>' value='<%out.print(listConfUsers.get(i));%>' onclick="removeConf('Us<%out.print(i);%>')"></li>
                                                                    <%}%>
                                                            </ul>
                                                        </li>
                                                            
                                                        <li><a href="#">Fichiers de configuration</a>
                                                            <ul>
                                                                <% for (int i = 0; i < listConfAdmin.size(); i++) {%>
                                                                <li size="25px"><input   id='<%out.print(i);%>' type ="text"  name='Admin<%out.print(i);%>' value='<%out.print(listConfAdmin.get(i));%>' onclick="addConf('ext', '<%out.print(listConfAdmin.get(i));%>')"></li>
                                                                    <%}%>
                                                            </ul>
                                                        </li>
                                                            
                                                        <img src="graph/pictos/b3fleche_ferme.png"></img>
                                                        
                                                        
                                                        <li><a href="#">Fichiers Dialplan</a>
                                                            <ul id="ext">
                                                                <% for (int i = 0; i < listConfExt.size(); i++) {%>
                                                                <li size="25px"><input  id='Ex<%out.print(i);%>' type ="text"  name='Ext<%out.print(i);%>' value='<%out.print(listConfExt.get(i));%>' onclick="removeConf('Ex<%out.print(i);%>')"></li>
                                                                    <%}%>
                                                            </ul>
                                                        </li>
                                                        
                                                        <li><a href="#">Fichiers de configuration</a>
                                                            <ul>
                                                                <% for (int i = 0; i < listConfAdmin.size(); i++) {%>
                                                                <li size="25px"><input  id='<%out.print(i);%>' type ="text"  name='Admin<%out.print(i);%>' value='<%out.print(listConfAdmin.get(i));%>' onclick="addConf('ss', '<%out.print(listConfAdmin.get(i));%>')"></li>
                                                                    <%}%>
                                                            </ul>
                                                        </li>
                                                            
                                                        <img src="graph/pictos/b3fleche_ferme.png"></img>
                                                        
                                                        
                                                        <li><a href="#">Fichiers Sous Admin</a>
                                                            <ul id="ss">
                                                                <% for (int i = 0; i < listConfssAdmin.size(); i++) {%>
                                                                <li><input size="25px" id='ssAdm<%out.print(i);%>' type ="text"  name='ssAdm<%out.print(i);%>' value='<%out.print(listConfssAdmin.get(i));%>' onclick="removeConf('ssAdm<%out.print(i);%>')"></li>
                                                                    <%}%>
                                                            </ul>
                                                        </li>
                                                        
                                                    </ul>
                                                </div>


                                                <br></br>

                                                <input type="submit" name="Modifier" value="Appliquer" />
                                                
                                            </div>


                                        </div >


                                    </form>

                                </div>


                            </div>
                        </div>



                        <!--FERMETURE CONTENEUR-->


                    </body>
                    </html>