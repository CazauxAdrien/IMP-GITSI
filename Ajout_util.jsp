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
                                function post(val) {
                                    document.getElementById("action").value = val;
                                }
                                function poste(val) {
                                    document.getElementById("act").value = val;
                                }
                            </script>
                            <%String admin = (String) request.getAttribute("admin");
                            String ssadmin = (String) request.getAttribute("sousAdmin");
                                if (admin.equals("true")) {%>
                            <div id="logogipsi" style="padding-left: 30px; padding-top: 5px;"><a href="/GASTON/accueil" title="Accueil"><img src="graph/mini_home.png" alt="Accueil" style="width: 40px;"/></a>   
                            </div>
                            <%}else if (admin.equals("false") && ssadmin!=null) {%>
                            <div id="logogipsi" style="padding-left: 30px; padding-top: 5px;"><a href="/GASTON/accueil_2" title="Accueil"><img src="graph/mini_home.png" alt="Accueil" style="width: 40px;"/></a>   
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

                        

                        <div id="corps">

                            <div align="center">
                                
                                <h1>Ajouter un utilisateur</h1>
                                
                                <form class="form-horizontal" role="form" action="util" method="post" >
                                    <div>
                                        
                                        <div class="panel-body" id="Ajout">

                                            <div class="form-group">
                                                
                                                <br><label for="inputEmail3" class="col-sm-3 control-label">Identifiant</label>
                                                    
                                                    <div class="col-sm-9">
                                                        <input type="Identifiant" class="form-control" name="id" placeholder="Identifiant" required="">
                                                    </div>
                                            </div>
                                            
                                            <div class="form-group">
                                                
                                                <label for="inputPassword3" class="col-sm-3 control-label">Password</label>
                                                
                                                <div class="col-sm-9">
                                                    <input type="password" class="form-control" name="pwd" placeholder="Password" required="">
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">
                                                
                                                <label for="inputPassword3" class="col-sm-3 control-label">Fullname</label>
                                                
                                                <div class="col-sm-9">
                                                    <input type="Idenfifiant" class="form-control" name="fullname" placeholder="Fullname" required="">
                                                </div>
                                            </div>
                                            
                                            <div class="form-group">
                                                
                                                <label for="inputPassword3" class="col-sm-3 control-label">Permit</label>
                                                
                                                <div class="col-sm-9">
                                                    <input type="Idenfifiant" class="form-control" name="permit" placeholder="Permit" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                
                                                <label for="inputPassword3" class="col-sm-3 control-label">Opérateur</label>
                                                
                                                <div class="col-sm-9">
                                                    <br>
                                                    <select id="from1" name="sousAdmin">
                                                        <option>Oui</option>
                                                        <option>Non</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <input type="hidden" id="act" name="act" value=""></input>
                                            <input type="submit" name="Ajout" value="Ajout" onClick="poste('Ajouter');"/>
                                        </div>


                                    </div >

                                    
                                </form>

                            </div>
                            <div align="center">
                                
                                <h1>Supprimer un utilisateur</h1>
                                
                                <form class="form-horizontal" role="form" action="util" method="post" >
                                    <div>
                                        <input type="hidden" id="action" name="action" value=""></input>
                                        <div class="panel-body" id="Ajout">

                                            <div class="form-group">
                                                
                                                <br><label for="inputEmail3" class="col-sm-3 control-label">Identifiant</label>
                                                    
                                                    <div class="col-sm-9">
                                                        <input type="Identifiant" class="form-control" name="idsup" placeholder="Identifiant" required="">
                                                    </div>
                                            </div>
                                            
                                            <div class="form-group">
                                                
                                                <label for="inputPassword3" class="col-sm-3 control-label">Password</label>
                                                
                                                <div class="col-sm-9">
                                                    <input type="password" class="form-control" name="pwdsup" placeholder="Password" required="">
                                                </div>
                                            </div>
                                            
                                            
                                            
                                            <input type="submit" name="Ajout" value="Supprimer" onClick="post('Supprimer');"/>
                                        </div>

                                        
                                    </div >


                                </form>

                            </div>

                        </div>
                    </div>



                    <!--FERMETURE CONTENEUR-->


                </body>
                </html>