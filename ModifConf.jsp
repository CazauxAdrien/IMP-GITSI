<%@page import="java.util.LinkedList"%>
<%@page import="javafx.util.Pair"%>
<%@page import="java.util.List"%>
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
                <div>
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
                </div>
                <body>
                    <%
                        String parameter = request.getParameter("conf");

                        List<Pair<String, String>> listLines = (List<Pair<String, String>>) request.getAttribute("listLines");
                        LinkedList<String> listConf = (LinkedList<String>) request.getAttribute("listConf");
                        LinkedList<String> listAide = (LinkedList<String>) request.getAttribute("listAide");

                    %>



                    <div id="conteneur">

                        <div clas="row">
                            <div id="corps" align="center" col-lg-12>
                                <div id="menu">

                                    <ul id="onglets">

                                        <% if (parameter == null) {%>
                                        <li class="active"><a href="modifconf">Accueil</a></li>    
                                            <%}

                                                if (parameter != null) {%>
                                        <li><a href="modifconf">Accueil</a></li>
                                            <%}%>

                                        <% for (int i = 0; i < listConf.size(); i++) {

                                                if (listConf.get(i).equals(parameter)) {%>
                                        <li class="active"><a href="modifconf?conf=<%out.print(listConf.get(i));%>"><%out.print(listConf.get(i));%></a></li>

                                        <%} else {%>
                                        <li><a href="modifconf?conf=<%out.print(listConf.get(i));%>"><%out.print(listConf.get(i));%></a></li>
                                            <%}%>

                                        <%}%>





                                    </ul>

                                </div>

                            </div>
                        </div>

                        <div class="row">
                            <div id='confForm' col-lg-12>
                                <% if (parameter != null && listAide.size()!=0) { 
                                    List<String> doublon = new LinkedList<String>();
                                %>
                                <datalist id="suggest" >
                                         <%for(int o=0;o<listAide.size();o++){
                                         if(listAide.get(o).contains(":")){
                                             String l=listAide.get(o).substring(0, listAide.get(o).lastIndexOf(":"));
                                            if(l.contains("default") && l.contains(":")){
                                            %>
                                            <option value="<%out.println(l.substring(0, l.lastIndexOf(":")));%>">
                                            <%}else{%>
                                            <option value="<%out.println(l);%>">
                                         <%}}else{%>
                                            <option value="<%out.println(listAide.get(o));%>">
                                         <%}    
                                        }
                                        for(int r=0;r<listLines.size();r++){
                                            if(!doublon.contains(listLines.get(r).getValue())){
                                                doublon.add(listLines.get(r).getValue());

                                                %><option value="<%out.print(listLines.get(r).getValue());%>">
                                            <%}
                                        }%>
                                
                                     
                                  </datalist>
                                <%}%>        
                            </div>
                        </div>

                        <div class="row" >

                            <% if (parameter != null) {%>
                            <div id='confForm' col-lg-12 >
                                <form method="post" action="modifconf">
                                    <div id='conf' >

                                        <div id="dynamicInput" >
                                            <%if (listLines != null && listLines.size() != 0) {
                                                    int numcat=0;
                                                    int lastcat=0;
                                                    for (int i = 0; i < listLines.size(); i++) {

                                                        if (listLines.get(i).getKey().contains("category")) {
                                                            if(i!=0){%>
                                                            <div id="categor<%out.print(numcat);%>"> 
                                                            </div>
                                                            
                                                            <div id="plus<%out.print(numcat-1);%>"><special><br><br><a style="cursor:pointer;" onClick="addInput('categor<%out.print(numcat);%>','line-<%out.print(listLines.get(lastcat).getKey().split("-")[1]);%>');"><input id='add' type="button" align="center" onClick="addInput('categor<%out.print(numcat);%>','line-<%out.print(listLines.get(lastcat).getKey().split("-")[1]);%>');"/>Ajouter une ligne</a></<br></special>
                                                                                </div>
                                                            
                                                            
                                                            <div class="row" name="linebreak" id="linebreak<%out.print(numcat-1);%>">
                                                                    <div id='confForm' col-lg-12>
                                                                        
                                                                    </div>
                                                                </div>
                                                            </div> 
                                                            </x>
                                                            <%}%>
                                                            
                                                            <x id="category<%out.print(numcat);%>"> 
                                                              
                                                                <special id='title<%out.print(i);%>'> <br><input id='deroul' type="button" name="0"  onClick="afficheMenu('<%out.print(numcat);%>');rotateBy180Deg(this);">&emsp;<input type ="hidden" name="primarykey<%out.print(i);%>" value="<%out.print(listLines.get(i).getKey());%>" size="25">     <input id='<%out.print(i);%>' type='text' name='primaryvalue<%out.print(i);%>' value='<%out.print(listLines.get(i).getValue());%>' size="25"> &emsp; <input id="remove" type='button' onClick="removeInput('category<%out.print(numcat);%>')" ></special>
                                                            
                                                            
                                                            <div class="row" >
                                                                <div id='confForm' col-lg-12>
                                                                </div>
                                                            </div>    
                                                            <div id="lines<%out.print(numcat);%>">
                                                                            
                                                            <% numcat=numcat+1;
                                                            lastcat=i;
                                                            } else {%>
                                                            <special id='<%out.print(i);%>' > <br><%out.print(listLines.get(i).getValue().split("=")[0]);%><input type ="hidden" name="primarykey<%out.print(i);%>" value="<%out.print(listLines.get(i).getKey());%>" size="25">  : <br> <input id='<%out.print(i);%>' type='hidden' name='primaryval<%out.print(i);%>' value='<%out.print(listLines.get(i).getValue().split("=",2)[0]);%>'  > <input id='<%out.print(i);%>'  type='text' name='primaryvalue<%out.print(i);%>' value='<%out.print(listLines.get(i).getValue().split("=",2)[1]);%>' style="display:table-cell;width:90.5%"> &nbsp; <input type='image' src="./graph/pictos/croix_rouge.png" onClick="removeInput('<%out.print(i);%>')" style="width:5.5%; line-height:30px"></special>
                                                            <%}
                                                            if(i==listLines.size()-1){%>
                                                            <div id="categor<%out.print(numcat);%>"> 
                                                            </div>
                                                            
                                                            <div id="plus<%out.print(numcat-1);%>"> 
                                                            <special ><br><br><a style="cursor:pointer;" onClick="addInput('categor<%out.print(numcat);%>','line-<%out.print(listLines.get(lastcat).getKey().split("-")[1]);%>');"><input id='add' type="button" align="center" onClick="addInput('categor<%out.print(numcat);%>','line-<%out.print(listLines.get(lastcat).getKey().split("-")[1]);%>');"/>Ajouter une ligne</a></<br></special>
                                                                            </div>
                                                            </div> 
                                                            </x>                
                                                            <%}%>

                                                    <%}%>
                                                   
                                                    </div>
                                                    <div class="row" name="linebreak" id="linebreak<%out.print(numcat-1);%>">
                                                        <div id='confForm' col-lg-12>

                                                        </div>
                                                    </div> 
                                                    
                                                    <div id="AddedCat">
                                                        
                                                    </div>
                                                        <special id="categoryadd"><br><br><a onClick="addCat('AddedCat','NewCat','category-added','line-added');" style="cursor:pointer;"><input id='add' type="button"   align="center" onClick="addCat('AddedCat','NewCat','category-added','line-added');"/>Ajouter une catégorie</a></<br></special>

                                            <%}%>
                                            

                                        
                                            
                                        <div class="row" >
                                            <div id='confForm' col-lg-12>
                                                <special><input type ="hidden" name="value" value="<%out.print(listLines.size());%>"</special>
                                            </div>
                                        </div>    
                                            <div align="center">
                                            
                                                                                            
                                        
                                                                                                
                                        </div>
                                    </div>
                                        <div align="center" >
                                             
                                        <a><input type="submit" name="ModifConfig" value="Appliquer" style="font-size:30px; height:40px;"/></a>
                                        </div>
                                </form>   
                                
                            <%} else {%>
                                <div align="center">
                                    <br><hf>Sélectionnez une fichier de configuration à modifier.</hf></br>
                                    
                                    <form method="post" action='modifconf'>
                                        
                                        <br><hf>Ou créez un fichier de configuration:</hf></br>
                                        
                                        <br>&emsp;&emsp;<input type="text" name="newConf">          <input type ="submit" id="add" value="">
                                    </form>
                                </div>
                            </div>
                            <%}%>
                        </div>
                            
                                        
                                    <script type="text/javascript">

                                       var counter=1;
                                        function addInput(divName,num) {
                                            var newdiv = document.createElement('div');
                                            newdiv.innerHTML = " <special id='added" + counter + "'><br><input type='hidden' name='key" + counter + "' value='"+num+"' ><input name='value0" + counter + "' list='suggest' type='text' size='25'><input name='value1" + counter + "' list='suggest' type='text' size='25' style='display:table-cell;width:90.5%'>" + "     &nbsp<input id='remove' type='button'  onClick=" + "\"removeInput('added" + counter + "')\"" + "; style='width:7.5%;line-height:30px;'></special>";
                                            document.getElementById(divName).appendChild(newdiv);
                                           counter++;

                                        }
                                        var counte = 1;
                                        function addExt(divName,next,line) {
                                            var newdiv = document.createElement('div');
                                            newdiv.innerHTML = " <special id='line" + counte + "'><br><input type='hidden' id='"+next+"|"+counte+"' name='xte"+next+"|"+counte+"' value='"+line+"' ><input id='"+next+"|"+counte+"' type ='text' name='xt"+next+"|"+counte+"' value='' list='suggest' type='text' size='25'><input id='"+next+"|"+counte+"' type ='text' name='xt1"+next+"|"+counte+"' value='' list='suggest' type='text' size='25' style='display:table-cell;width:90.5%'>" + "&nbsp<input id='remove' type='button'  onClick=" + "\"removeInput('line" + counte + "')\"" + "; style='width:7.5%;line-height:30px;'></special>";
                                            document.getElementById(divName).appendChild(newdiv);
                                            counte++;

                                        }
                                        var count=1;
                                        function addCat(divName,name,key,key2) {
                                            var newdiv = document.createElement('div');
                                            newdiv.innerHTML = " <x id='"+name + count + "'><special id='titleAdd"+name + count + "'><br><input id='deroul' name='0' type='button' onClick=" + "\"afficheMenuAdded('"+count+"');rotateBy180Deg(this);\"" + ";>&nbsp&nbsp&nbsp&nbsp&nbsp<input type='hidden' name='A"+name + count + "' value='"+key+count+"' ><input id='"+name + count + "' type ='text' name='"+name + count + "' value='' list='suggest' type='text'  style='display:table-cell;' size='25'>" + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input id='remove' type='button'  onClick=" + "\"removeInput('"+name + count + "')\"" + "; style='width:5.5%;'></special>\n\
                                            <div id='plusa"+count+"'>\n\
                                                <div id='dynamicExtCat"+count+"'>\n\
                                                </div>\n\
                                            <special ><br><br><a style='cursor:pointer;' onClick=" + "\"addExt('dynamicExtCat"+count+"','"+name+count+"','"+key2+count+"')\"" + "><input id='add' type='button'  align='center' onClick=" + "\"addExt('dynamicExtCat"+count+"','"+name+count+"','"+key2+count+"')\"" + ";>Ajouter une ligne</a></<br></special>\n\
                                                <div class='row' name='linebreak'>\n\
                                                <div id='confForm' col-lg-12>\n\
                                                </div>\n\
                                                </div>\n\
                                            </div></x>";
                                            document.getElementById(divName).appendChild(newdiv);
                                           count++;

                                        }
                                        function removeInput(divName) {
                                            var a = document.getElementById(divName);
                                            a.remove();
                                        }
                                        function afficheMenu(obj){
	
                                                
                                                var sousMenu   = document.getElementById("lines"+obj);
                                                var sousMenu1   = document.getElementById("plus"+obj);
                                                var sousMenu2   = document.getElementById("linebreak"+obj);
                                                
                                                if(sousMenu.style.display === "block"){
                                                        sousMenu.style.display = "none";
                                                        sousMenu1.style.display = "none";
                                                        sousMenu2.style.display = "none";
                                                }
                                                else{
                                                        sousMenu.style.display = "block";
                                                        sousMenu1.style.display = "block";
                                                        sousMenu2.style.display = "block";
                                                }

                                        }
                                        function afficheMenuAdded(obj){
	
                                                
                                                var sousMenu1   = document.getElementById("plusa"+obj);
                                                
                                                if(sousMenu1.style.display === "block"){
                                                        sousMenu1.style.display = "none";
                                                }
                                                else{
                                                        sousMenu1.style.display = "block";
                                                }

                                        }
                                        
                                        function rotateBy180Deg(ele){
                                            
                                            if(ele.getAttribute("name")==180){
                                                ele.style.webkitTransform="rotate(0deg)";
                                                ele.setAttribute("name",0);
                                            }
                                            else{
                                                ele.style.webkitTransform="rotate(180deg)";
                                                ele.setAttribute("name",180);
                                            }
                                        }

                                    </script>

                    </div>               
                </div>

            </body>
        </html>