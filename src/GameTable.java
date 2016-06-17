

import java.util.ArrayList;

import domain.Card;
import domain.Deck;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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

	private AID player = null;
	
	@Override
	protected void setup() {
		this.makeTableAvailable();
		addBehaviour(new JoinPlayersBehaviour());
	}
	
	private class JoinPlayersBehaviour extends CyclicBehaviour{

		private static final long serialVersionUID = 8928783821228708384L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				ACLMessage reply = msg.createReply();
				boolean tableAvailable = GameTable.this.player == null;
				if (tableAvailable) {
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent("Mesa encontrada");
					GameTable.this.addBehaviour(new AcceptPlayersBehaviour());
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
					
					if(GameTable.this.player == null){
						System.out.println("Mesa disponível, pode entrar jovem...");
						GameTable.this.player = playerToJoin;
						reply.setPerformative(ACLMessage.INFORM);
						reply.setContent("Seja bem-vindo! Você entrou na nossa mesa!");
						GameTable.this.startGame();
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
	
	public void startGame(){
		// Get a card on deck
		Deck deck = Deck.getInstance();
		Card card = deck.getTopCard();
		this.addBehaviour(new ShowCardAndPassBehaviour(card));
	}

	private class ShowCardAndPassBehaviour extends OneShotBehaviour{

		private Card card;
		public ShowCardAndPassBehaviour(Card card) {
			super();
			this.card = card;
		}

		@Override
		public void action() {
//			MessageTemplate informTemplate = MessageTemplate.MatchConversationId();
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			
			message.setContent("Cartas da mesa: \n " + card.toString() + "\n Sua vez.");
			message.setConversationId("your-turn");
			message.addReceiver(GameTable.this.player);
			myAgent.send(message);
		}
		
	}
	
}
