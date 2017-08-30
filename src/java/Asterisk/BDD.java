package Asterisk;

import Gestion.PropertyManagement;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.DbDelAction;
import org.asteriskjava.manager.action.DbGetAction;
import org.asteriskjava.manager.action.DbPutAction;
import org.asteriskjava.manager.event.DbGetResponseEvent;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.response.ManagerResponse;

public class BDD implements ManagerEventListener {

    private ManagerConnection managerConnection;
    private String Id;
    private String pwd;
    private String function;

    public BDD() throws IOException {

        ManagerConnectionFactory factory = new ManagerConnectionFactory(
                PropertyManagement.reader("ip"), PropertyManagement.reader("id"), PropertyManagement.reader("pwd"));

        this.managerConnection = factory.createManagerConnection();
    }

    public boolean GetUser() throws IOException, AuthenticationFailedException,
            TimeoutException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        function = "GetUser";
        boolean a = false;
        // register for events
        managerConnection.addEventListener(this);
        // connect to Asterisk and log in
        managerConnection.login();

        String result = managerConnection.sendAction(new DbGetAction("User", Id)).getResponse();
        if (result.contains("Succes")) {
            a = true;
        }

        managerConnection.logoff();
        return a;
    }

    public boolean AddUser(String login, String pwd) throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {

        boolean a = false;
        managerConnection.addEventListener(this);
        managerConnection.login();
        String response = managerConnection.sendAction(new DbPutAction("User", login, pwd)).getResponse();
        
        if (response.contains("Succes")) {
            a = true;
        }

        managerConnection.logoff();
        return a;
    }

    public boolean DelUser(String login, String pwd) throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {

        boolean a = false;
        managerConnection.addEventListener(this);
        managerConnection.login();
        String response = managerConnection.sendAction(new DbDelAction("User", login)).getResponse();
        
        if (response.contains("Succes")) {
            a = true;
        }

        managerConnection.logoff();
        return a;
    }
    
    @Override
    public void onManagerEvent(ManagerEvent event) {
        // TODO Auto-generated method stub
        if (function.equals("GetUser")) {
            if (event.getClass().toString().contains("DbGetResponseEvent")) {
                DbGetResponseEvent b = (DbGetResponseEvent) event;
                //System.out.println(b.getVal());
                setPwd(b.getVal());
            }
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return Id;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pass) {
        pwd = pass;
    }

    public void setId(String id) {
        Id = id;
    }


}
