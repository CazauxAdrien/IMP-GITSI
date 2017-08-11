/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author R&D
 */
public class Useless {
    
    
    
    
    public static List<String> filereader(String confname){
			
        Useless us = new Useless();
        System.out.println(confname);
        String filename = us.getClass().getResource("Result.txt").getPath();
        int m=0;
        List<String> listlines=new LinkedList<String>();
        try
        {
            File f = new File (filename);
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);

            try
            {
                String line = br.readLine();

                while (line != null)
                {



                    if(line.contains("####") && line.contains(confname)){
                        line = br.readLine();
                        while(line!=null && !line.contains("####")){
                                line=br.readLine();
                                if(line!=null && !line.isEmpty() && !line.contains("######") ){
                                        listlines.add(line);
                                }

                        }
                    }
                    else{
                        line = br.readLine();
                    }


                }
                br.close();
                fr.close();
            }
            catch (IOException exception)
            {
                System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }
        return listlines;
    }
    
    
    
}
