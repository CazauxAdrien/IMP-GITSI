/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asterisk;

/**
 *
 * @author R&D
 */
import Gestion.PropertyManagement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javafx.util.Pair;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.GetConfigAction;
import org.asteriskjava.manager.action.StatusAction;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.response.ManagerResponse;

public class ProcessConfFile implements ManagerEventListener {

    private ManagerConnection managerConnection;

    public ProcessConfFile() throws IOException {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(PropertyManagement.reader("ip"), PropertyManagement.reader("id"), PropertyManagement.reader("pwd"));

        this.managerConnection = factory.createManagerConnection();
    }

    @SuppressWarnings("rawtypes")
    public List<Pair<String, String>> GetConfFile(String file) throws IOException, AuthenticationFailedException,
            TimeoutException, InterruptedException {

        Map<String, Object> response;

        managerConnection.login();

        response = managerConnection.sendAction(new GetConfigAction(file)).getAttributes();

        TreeMap<String, Object> treeresponse = new TreeMap<String, Object>(response);
        Set treekeys = treeresponse.keySet();
        List<Pair<String, String>> lines = new LinkedList<Pair<String, String>>();
        List<Pair<String, String>> categories = new LinkedList<Pair<String, String>>();
        List<Pair<String, String>> result = new LinkedList<Pair<String, String>>();
        
        int k = 0;
        String cat = "000000";
        
        for (Iterator i = treekeys.iterator(); i.hasNext();) {

            String key = (String) i.next();
            String val = (String) treeresponse.get(key);
            if (key.contains("line-")) {
                
                Pair<String, String> paire = new Pair<String, String>(key, val);
                lines.add(paire);
                //System.out.println(key + "=" + value);
            } else if (key.contains("category")) {
                
                Pair<String, String> paire = new Pair<String, String>(key, val);
                categories.add(paire);
            }
        }
        
        if (!categories.isEmpty()) {
            
            result.add(categories.get(k));
            
            for (int i = 0; i < lines.size(); i++) {
                
                String[] numLine = (lines.get(i).getKey().split("-"));
                String num = numLine[1];
                
                if (num.equals(cat)) {
                    
                    result.add(lines.get(i));
                } else {
                    
                    result.add(categories.get(k + 1));
                    k += 1;
                    cat = num;
                    result.add(lines.get(i));
                }
            }
        }
        
        managerConnection.logoff();

        return result;
    }

    public void onManagerEvent(ManagerEvent event) {
        // just print received events
        //System.out.println(event);
    }

    public void process(List<Pair<String, String>> newConf, List<Pair<String, String>> originalConf, String filename) throws IOException, AuthenticationFailedException, TimeoutException, InterruptedException {

        int l = 0;
        int v=0;
        UpdateConf manager = new UpdateConf();
        int diff = newConf.size() - originalConf.size();
        Map<String, String> categories = new HashMap<String, String>();
        Map<String, String> oldcategories = new HashMap<String, String>();
        
        String currentCategory = "";
        String oldCategory = "";
        String currentVar = "";
        String currentVal = "";
        String newkey;
        String newvalue;
        String oldkey;
        String oldvalue;
        String oldVal = "";
        String oldVar = "";

        for (int i = 0, k = 0; i < newConf.size() && k < originalConf.size(); i++, k++) {
            v=v+1;
            newkey = newConf.get(i).getKey();
            newvalue = newConf.get(i).getValue();
            oldkey = originalConf.get(k).getKey();
            oldvalue = originalConf.get(k).getValue();
            
            if (newvalue!=null && newvalue.contains("=")) {
                
                currentVar = newvalue.split("=")[0];
                currentVal = newvalue.split("=",2)[1];
            }
            if (oldvalue.contains("=")) {
                
                oldVar = oldvalue.split("=")[0];
                oldVal = oldvalue.split("=",2)[1];
            }

            if (newvalue!=null && newkey.contains("category")) {
                
                categories.put(newkey, newvalue);
                currentCategory = newvalue;
            }
            if (oldkey.contains("category")) {
                
                oldcategories.put(oldkey, oldvalue);
                oldCategory = oldvalue;
            }
            
            if (newvalue!=null && newkey.contains(oldkey)) {

                if (!(newvalue.equals(oldvalue)) && !newkey.contains("category")) {
                    
                    manager.UpdateConfig("Update", filename, true, currentCategory, currentVar, currentVal, oldVal);
                } else if (!(newvalue.equals(oldvalue)) && newkey.contains("category")) {
                    
                    manager.UpdateConfig("RenameCat", filename, true, oldCategory, oldCategory, currentCategory, null);
                }
            } else {
                l=l-1;
                i = i - 1;
                if (oldkey.contains("category")) {
                    
                    manager.UpdateConfig("DelCat", filename, true, oldCategory, oldCategory, null, null);
                } else {
                    
                    manager.UpdateConfig("Delete", filename, true, oldCategory, oldVar, oldVal, null);
                }
            }
            l = l + 1;

        }

        for (int m = l; m < newConf.size(); m++) {
            
            newkey = newConf.get(m).getKey();
            newvalue = newConf.get(m).getValue();
            
            if(newvalue!=null){
                
                if (newkey.contains("category")) {

                    manager.UpdateConfig("NewCat", filename, true, newvalue, "", "", null);
                    currentCategory = newvalue;
                    categories.put(newkey, newvalue);
                } else {

                    Set setKeys = categories.keySet();

                    for (Iterator i = setKeys.iterator(); i.hasNext();) {
                        String key = (String) i.next();
                        String val = (String) categories.get(key);
                        
                        if (newvalue.contains("=") && newkey.split("-")[1].contains(key.split("-")[1])) {
                            manager.UpdateConfig("Append", filename, true, val, newvalue.split("=")[0], newvalue.split("=")[1], null);
                        } else {
                        }
                    }
                }
            }
        }
        
        
        for (int m = v; m < originalConf.size(); m++) {
            System.out.println(originalConf.get(m));
            oldkey = originalConf.get(m).getKey();
            oldvalue = originalConf.get(m).getValue();

            if (oldkey.contains("category")) {
                
                oldcategories.put(oldkey, oldvalue);
                oldCategory = oldvalue;
                manager.UpdateConfig("DelCat", filename, true, oldCategory, oldCategory, null, null);
                
            } else if (oldvalue.contains("=")) {
                
                oldVar = oldvalue.split("=")[0];
                oldVal = oldvalue.split("=")[1];
                manager.UpdateConfig("Delete", filename, true, oldCategory, oldVar, oldVal, null);
            }
        }

    }

    public Map<String,Map<String,List<Pair<String,String>>>> GetConfExtenstions(String file) throws IOException, AuthenticationFailedException,
            TimeoutException, InterruptedException {

        Map<String, Object> response;

        managerConnection.login();

        response = managerConnection.sendAction(new GetConfigAction(file)).getAttributes();

        TreeMap<String, Object> treeresponse = new TreeMap<String, Object>(response);
        Set treekeys = treeresponse.keySet();
        
        List<Pair<String, String>> lines = new LinkedList<Pair<String, String>>();
        List<Pair<String, String>> categories = new LinkedList<Pair<String, String>>();
        List<Pair<String, String>> result = new LinkedList<Pair<String, String>>();
        
        int k = 0;
        String cat = "000000";
        
        for (Iterator i = treekeys.iterator(); i.hasNext();) {

            String key = (String) i.next();
            String val = (String) treeresponse.get(key);
            
            if (key.contains("line-")) {
                Pair<String, String> paire = new Pair<String, String>(key, val);
                lines.add(paire);
                
            } else if (key.contains("category")) {
                
                Pair<String, String> paire = new Pair<String, String>(key, val);
                categories.add(paire);
            }
        }
        
        if (!categories.isEmpty()) {
            
            result.add(categories.get(k));
            
            for (int i = 0; i < lines.size(); i++) {
                
                String[] numLine = (lines.get(i).getKey().split("-"));
                String num = numLine[1];
                
                if (num.equals(cat)) {
                    
                    result.add(lines.get(i));
                    
                } else {
                    
                    result.add(categories.get(k + 1));
                    k += 1;
                    cat = num;
                    result.add(lines.get(i));
                }
            }
        }

        managerConnection.logoff();

        Map<String,Map<String,List<Pair<String,String>>>> END = new HashMap<String, Map<String, List<Pair<String, String>>>>();

        for (int v = 0; v < result.size(); v++) {
            
            if (result.get(v).getKey().contains("category")) {
                int m = v + 1;

                Map<String, List<Pair<String, String>>> num = new HashMap<String, List<Pair<String, String>>>();
                String number = "";

                while (m < result.size() && !result.get(m).getKey().contains("category")) {
                    List<Pair<String, String>> h = new LinkedList<Pair<String, String>>();

                    if (result.get(m).getValue().startsWith("exten")) {
                        
                        if (!num.containsKey(getNum(result.get(m).getValue()))) {
                            
                            h.add(result.get(m));
                            number = getNum(result.get(m).getValue());
                            num.put(number, h);
                            
                        } else {
                            
                            num.get(number).add(result.get(m));
                            
                        }
                        
                    } else if (result.get(m).getValue().startsWith("same")) {
                        
                        num.get(number).add(result.get(m));
                        
                    }
                    else if (result.get(m).getValue().startsWith("include")){
                        if (!num.containsKey("include")){
                            h.add(result.get(m));
                            num.put("include", h);
                        }
                        else{
                            num.get("include").add(result.get(m));
                        }
                        
                    }
                    m = m + 1;
                }
                END.put(result.get(v).toString(), num);

            }

        }
        
        
        TreeMap<String, Map<String, List<Pair<String, String>>>> Fin = new TreeMap<String, Map<String, List<Pair<String, String>>>>(END);
        System.out.println(Fin);
        return Fin;
    }

    public String getNum(String a) {
        
        if (a.contains("=")) {
            
            String res = a.split("=")[1].split(",")[0];
            return res;
            
        }
        return a;
    }
}
