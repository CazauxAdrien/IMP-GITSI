package Asterisk;

import Asterisk.CreateConfigAction;
import Gestion.PropertyManagement;
import java.io.IOException;
import java.util.Map;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.response.ManagerResponse;

public class CreateConf implements ManagerEventListener {

    private ManagerConnection managerConnection;

    public CreateConf() throws IOException {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(PropertyManagement.reader("ip"), PropertyManagement.reader("id"), PropertyManagement.reader("pwd"));

        this.managerConnection = factory.createManagerConnection();
    }

    @SuppressWarnings("rawtypes")
    public void create(String filename) throws IOException, AuthenticationFailedException,
            TimeoutException, InterruptedException {
        
        ManagerResponse originateResponse;
        Map<String, Object> response;

        // register for events
        // connect to Asterisk and log in
        managerConnection.login();

        // request channel state
        //managerConnection.sendAction(new GetConfigAction("users.conf"));
        managerConnection.sendAction(new CreateConfigAction(filename));
        // wait 10 seconds for events to come in

        // and finally log off and disconnect
        managerConnection.logoff();
    }

    public void onManagerEvent(ManagerEvent event) {
        // just print received events
        //System.out.println(event);
    }

}
