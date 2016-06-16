

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/* 
 * Cyclic behaviour - To wait a player join the table
 * Cyclic behaviour - To wait the cards
 * Cyclic behaviour - To wait raise 
 * 
 */
public class GameTable extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5076427523762979450L;
	public final static int MAX_PLAYERS = 1;

	private ArrayList<AID> players = new ArrayList<AID>();
	private GameTableUI tableUI;
	
	@Override
	protected void setup() {
		this.makeTableAvailable();
		addBehaviour(new JoinPlayersBehaviour());
		this.tableUI = new GameTableUI(this);
	}
	
	private class JoinPlayersBehaviour extends CyclicBehaviour{

		private static final long serialVersionUID = 8928783821228708384L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				ACLMessage reply = msg.createReply();
				boolean tableAvailable = GameTable.this.canRegisterPlayer();
				if (tableAvailable) {
					reply.setPerformative(ACLMessage.PROPOSE);
					GameTable.this.addBehaviour(new AcceptPlayersBehaviour());
					reply.setContent(Integer.toString(GameTable.this.players.size()));
				}
				else {
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("Mesa cheia");
				}
				myAgent.send(reply);
			}
			else {
				this.block();
			}
		}

		private class AcceptPlayersBehaviour extends CyclicBehaviour{

			@Override
			public void action() {

				MessageTemplate acceptProposalTemplate = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
				ACLMessage message = myAgent.receive(acceptProposalTemplate);
				if(message != null){
					ACLMessage reply = message.createReply();
					AID playerToJoin = message.getSender();
					String player = message.getContent();
					if(GameTable.this.canRegisterPlayer()){
						System.out.println("Mesa disponível, pode entrar jovem...");
						GameTable.this.players.add(playerToJoin);
						reply.setPerformative(ACLMessage.INFORM);
						reply.setContent("Seja bem-vindo! Você entrou na nossa mesa!");
						GameTable.this.tableUI.showCard("Jogando com " + player, "4 de Paus");
					}else{
						System.out.println("Demorou demais! Mesa ta cheia, jovem...");
						reply.setPerformative(ACLMessage.INFORM_REF);
						reply.setContent("Infelizmente nossa mesa já encheu.");
					}
					reply.setConversationId("join-table");
					myAgent.send(reply);
				}
				else{
					this.block();
				}
				
			}
		}
		
	}
	
	private void makeTableAvailable(){
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("blackjack-game");
		sd.setName("BlackJack");
		dfd.addServices(sd);	
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}	
	}
	
	private boolean canRegisterPlayer(){
		
		int playersOnTable = this.players.size();
		boolean available;
		if(playersOnTable < MAX_PLAYERS){
			available = true;
		}
		else{
			available = false;
		}

		return available;
	}
	
}
