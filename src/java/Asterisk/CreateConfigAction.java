package Asterisk;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.asteriskjava.manager.action.AbstractManagerAction;

/**
 *
 * @author R&D
 */
public class CreateConfigAction extends AbstractManagerAction{
    
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8549430926113509640L;
	protected String filename;

    
    public CreateConfigAction(String filename){
        this.filename=filename;
    }
    
    @Override
    public String getAction() {
        return "createconfig";
    }
    
 
    public void setfilename(String filename){
        this.filename=filename;
    }
    
    public String getfilename(){
        return filename;
    }
    
}
