import java.util.ArrayList;
import java.io.ObjectOutputStream;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Player extends Agent {

	private static final long serialVersionUID = -1729142182071611776L;

	private PlayerUI playerUI;
	private ArrayList<ACLMessage> seenReplies = new ArrayList<ACLMessage>();
	private AID[] tables;
	private AID bestTable = null;
	private ArrayList<ACLMessage> proposeReplies = new ArrayList<ACLMessage>();

	public String playerName;
	
	@Override
	protected void setup(){
		playerUI = new PlayerUI(this);
		playerUI.showGui();
	}

	private class JoinTableBehaviour extends OneShotBehaviour{

		private static final long serialVersionUID = 6983571473571663071L;

		@Override
		public void action() {
			AID[] tables = Player.this.getTables(myAgent);
						
			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
			for (int i = 0; i < tables.length; ++i) {
				cfp.addReceiver(tables[i]);
			} 
			cfp.setContent("Procurando mesa");
			cfp.setConversationId("join-table");
			cfp.setReplyWith("Resposta da mesa");
			myAgent.send(cfp);
			MessageTemplate mt = MessageTemplate.MatchConversationId("join-table");
			
			Player.this.addBehaviour(new CheckTablesReplies(Player.this, 2000, mt));	
		}
	}
	
	private class CheckTablesReplies extends TickerBehaviour {
		
		private MessageTemplate mt;
		
		public CheckTablesReplies(Agent agent, long period, MessageTemplate mt){
			super(agent, period);
			this.mt = mt;
		}

		private static final long serialVersionUID = 5073894079459687671L;
			
		private boolean allTablesChecked(){
			return Player.this.seenReplies.size() >= Player.this.tables.length;
		}
		
		@Override
		protected void onTick() {
			ACLMessage reply = myAgent.receive(this.mt);
			if(reply != null){
				Player.this.seenReplies.add(reply);
				if (reply.getPerformative() == ACLMessage.PROPOSE) {
					
					System.out.println("Player " + Player.this.getAID().getName() + " recebeu uma proposta da mesa " + reply.getSender().getName());
					
					String tablePlayersQuantity = reply.getContent();
					Player.this.proposeReplies.add(reply);
					
					// If all tables proposes was already seen, try to join the best table 
					if(this.allTablesChecked()){
						
						AID tableToJoin = Player.this.bestTable;
						String playersQuantity = "0"; // The best table quantity of players is 3
						// If the best table is not available, try to join any one
						if(tableToJoin == null){
							// Get the first table propose
							ACLMessage firstReply = Player.this.proposeReplies.get(0);
							System.out.println(firstReply.getPerformative());
							tableToJoin = firstReply.getSender();
							playersQuantity = firstReply.getContent();
						}
						
						// Accept the propose to join the table
						playerUI.update("Mesa encontrada com " + playersQuantity + " jogadores.");
						playerUI.update("Tentando entrar na mesa " + tableToJoin.getName());
						ACLMessage joinTableRequest = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
						joinTableRequest.addReceiver(tableToJoin);
						joinTableRequest.setContent(Player.this.playerName);
						joinTableRequest.setConversationId("join-table");
						myAgent.send(joinTableRequest);
						
						// Stop ticker to stop looking for tables
						this.done();
					}
				}
				else if(reply.getPerformative() == ACLMessage.INFORM){
					playerUI.update(reply.getContent(), true);
				}
				else if(reply.getPerformative() == ACLMessage.INFORM_REF){
					
					playerUI.update(reply.getContent());

				}
				else{
					// In this case the propose was refused
					playerUI.update("Nenhuma mesa disponível encontrada.");
				}
			}
			else{
				block();
			}
		}
		
	}
	
	public AID[] getTables(Agent myAgent){
		
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("blackjack-game");
		template.addServices(sd);
		DFAgentDescription[] result;
		try {
			result = DFService.search(myAgent, template);
			playerUI.update("Pegando as mesas existentes:");
			
			tables = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				tables[i] = result[i].getName();
				System.out.println(tables[i].getName());
			}

		} 
		catch (FIPAException e) {

		} 

		return tables;
	}
	
	public void joinTable(String playerName){
		this.playerName = playerName;
        this.addBehaviour( new JoinTableBehaviour());
	}
}
