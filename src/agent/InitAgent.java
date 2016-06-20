package agent;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


public class InitAgent extends Agent {

	private static final long serialVersionUID = 2963709068114684396L;

	protected void setup(){
		
		 // Get a hold on JADE runtime 
        Runtime rt = Runtime.instance(); 
        
        // Create a default profile    
        Profile p = new ProfileImpl();     
        
        // Create a new non-main container, connecting to the default 
        // main container (i.e. on this host, port 1099) 
        ContainerController cc = rt.createAgentContainer(p); 
        
        // Create a new agent, a DummyAgent 
        // and pass it a reference to an Object  
        Object args[] = new Object[1];
        AgentController gameTable1, gameTable2, gameTable3, player1, player2, player3;
        
        try {
            gameTable1 = cc.createNewAgent("Table1", "agent.GameTable", args);
            gameTable1.start();
            
            gameTable2 = cc.createNewAgent("Table2", "agent.GameTable", args);
            gameTable2.start();

//            gameTable3 = cc.createNewAgent("Table3", "agent.GameTable", args);
//            gameTable3.start();
            
            player1 = cc.createNewAgent("Player1", "agent.Player", args);
            player1.start();

            player2 = cc.createNewAgent("Player2", "agent.Player", args);
            player2.start();
            
            player3 = cc.createNewAgent("Player3", "agent.Player", args);
            player3.start();
            
        } catch (StaleProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
