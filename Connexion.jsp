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


                        </div>

                    </div>
                </div>
                <body>

                    <div id="conteneur">



                        <!--CONTENU-->	



                        <div id="corps">
                            <div>
                                <div class="row">
                                    <div class="col-lg-5">
                                        
                                        <img src="./graph//accueil/logo.png"  width="157px" height="68px"/>
                                        
                                        <form class="form-horizontal" role="form" action="connexion" method="post">

                                            <div class="panel-body">

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

                                            </div>


                                            <div class="col-lg-5">
                                                <br></br>
                                                
                                                <input type="submit" name="Connexion" value="CONNEXION" />	

                                        </form>
                                    </div>

                                </div>
                                <div class="col-sm-2"></div>

                                <div class="col-sm-5" align="right">

                                    <div class="Interface_connexion"><a href="#">
                                            <img src="./graph/tel_ip_2008.jpg" align="right" width="800px" height="900px"/>
                                        </a></div>
                                </div>

                            </div>



                        </div>
                    </div>





                    </div>



                    <!--FERMETURE CONTENEUR-->


                </body>
                </html>