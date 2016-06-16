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
        AgentController gameTable1, gameTable2, player1, player2, player3, player4, player5, player6, player7,
        				player8, player9, player10;
        try {
            gameTable1 = cc.createNewAgent("Table1", "GameTable", args);
            gameTable1.start();
            
//            gameTable2 = cc.createNewAgent("Table2", "GameTable", args);
//            gameTable2.start();
//            
            player1 = cc.createNewAgent("Player1", "Player", args);
            player1.start();
            
            player2 = cc.createNewAgent("Player2", "Player", args);
            player2.start();
            
//            player3 = cc.createNewAgent("Player3", "Player", args);
//            player3.start();
//            
//            player4 = cc.createNewAgent("Player4", "Player", args);
//            player4.start();
//            
//            player5 = cc.createNewAgent("Player5", "Player", args);
//            player5.start();
//            
//            player6 = cc.createNewAgent("Player6", "Player", args);
//            player6.start();
//            
//            player7 = cc.createNewAgent("Player7", "Player", args);
//            player7.start();
//            
//            player8 = cc.createNewAgent("Player8", "Player", args);
//            player8.start();
//            
//            
//            player9 = cc.createNewAgent("Player9", "Player", args);
//            player9.start();
//            
//            
//            player10 = cc.createNewAgent("Player10", "Player", args);
//            player10.start();
            
        } catch (StaleProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
