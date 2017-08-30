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
            .col-lg-20 { height: 250px; }
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
                </div>
                <body>

                    <div id="conteneur">



                        <!--CONTENU-->	



                        <div id="corps" align="center">

                            <div class="row">
                                <div class="col-md-6">

                                    <div class="Interface_connexion"><a href="/GASTON/modifconf">
                                            <img src="./graph/conf1.png" />

                                        </a></div>

                                    <a href="/GASTON/modifconf">
                                        <h2>Configurer Asterisk</h2>
                                    </a>


                                    <div class="Interface_connexion"><a href="/GASTON/dialplan">
                                            <img src="./graph/config2.png" width="216px" height="181px">
                                        </a></div>
                                    <a href="/GASTON/dialplan">	
                                        <h2 >Configurer le Dialplan</h2>
                                    </a>
                                </div>




                                <div class="row">
                                    <div class="col-md-6">

                                        
                                        
                                        <div class="Interface_connexion"><a href="/GASTON/util">
                                                <img src="./graph/accueil/10.png" width="216px" height="181px">
                                            </a></div>

                                        <a href="/GASTON/util">
                                            <h2>Gérer les utilisateurs</h2>
                                        </a>
                                        
                                    </div>
                                </div>	

                            </div>





                        </div>



                        <!--FERMETURE CONTENEUR-->


                </body>
                </html>