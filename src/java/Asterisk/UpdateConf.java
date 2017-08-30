package Asterisk;

import Gestion.PropertyManagement;
import java.io.IOException;
import java.util.Map;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.UpdateConfigAction;
import org.asteriskjava.manager.event.ManagerEvent;

public class UpdateConf implements ManagerEventListener {

    private ManagerConnection managerConnection;

    public UpdateConf() throws IOException {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(
                PropertyManagement.reader("ip"), PropertyManagement.reader("id"), PropertyManagement.reader("pwd"));

        this.managerConnection = factory.createManagerConnection();
    }

    @SuppressWarnings("rawtypes")
    public void UpdateConfig(String action, String filename, boolean reload, String cat, String var, String value, String oldvalue) throws IOException, AuthenticationFailedException,
            TimeoutException, InterruptedException {
        
        String originateResponse;
        Map<String, Object> response;

        managerConnection.login();

        UpdateConfigAction changes = new UpdateConfigAction(filename, filename, reload);
        
        if (action.equals("Delete")) {
            
            changes.addCommand(action, cat, var, value, value);
            System.out.println(managerConnection.sendAction(changes).getResponse());
            
        } else if (action.equals("Update")) {
            
            changes.addCommand(action, cat, var, value, oldvalue);
            System.out.println(managerConnection.sendAction(changes).getResponse());
            
        } else {
            
            changes.addCommand(action, cat, var, value, "");
            System.out.println(managerConnection.sendAction(changes).getResponse());
        }

        managerConnection.logoff();
    }

    public void onManagerEvent(ManagerEvent event) {
    }

}
