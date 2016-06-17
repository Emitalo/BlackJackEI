package agent;


import java.util.ArrayList;

import domain.Card;
import domain.Deck;
import domain.Round;
import exception.OverTwentOneException;
import exception.TwentOneException;
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

	private static final long serialVersionUID = -5076427523762979450L;

	public final static int MAX_PLAYERS = 1;
	public static final String TABLE_TO_PLAYER_TURN = "table-to-player";
	public static final String GAME_TABLE_CARDS = "game-table-cards";
	public static final String OVER_21 = "Passou de 21!";
	public static final String WINNER_21 = "21! ganhou!";

	private AID player = null;

	private Round currentRound;
	public boolean firstRound = true;
	public Integer points = 0;
	public Integer playerPoints = 0;

	private ArrayList<Card> cards = new ArrayList<Card>();

	public boolean isTableTurn = true;
	
	@Override
	protected void setup() {
		this.makeTableAvailable();
		this.currentRound = new Round(this);
		this.addBehaviour(new JoinPlayersBehaviour());
		this.addBehaviour(new StartRoundBehaviour());
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
				
				System.out.println(GameTable.this.getName() + " player '" + GameTable.this.player == null + "'");
				
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
		try{
			Card card = this.getNewCard();
			this.cards.add(card);
			this.addBehaviour(new InitGame(card));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Card getNewCard() throws OverTwentOneException, TwentOneException{
		Deck deck = Deck.getInstance();
		Card card = deck.getRandCard();
		
		// Save the card to get it back to the pool later
		this.cards.add(card);
		
		System.out.println("Pontos até agora: " + this.points);

		this.points += card.getRealValue();
			
		if(this.points >= 21){
			this.currentRound.endCurrentRound();
			throw new OverTwentOneException(GameTable.OVER_21);
		}else if(this.points == 21){
			throw new TwentOneException(GameTable.WINNER_21);
		}

		return card;
	}
	 
	/**
	 *  The table game (dealer) initiates the game by showing it
	 *  	cards and passing the turn to the player 
	 */
	private class InitGame extends OneShotBehaviour{

		private static final long serialVersionUID = 1195651792447082421L; 
		
		private Card card;
		
		public InitGame(Card card){
			super();
			this.card = card;
		}
		
		@Override
		public void action() {
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
						
			message.setContent(this.card.toString());
			message.setConversationId(GameTable.TABLE_TO_PLAYER_TURN);
			message.addReceiver(GameTable.this.player);
			myAgent.send(message);
			System.out.println("Mesa passando a vez para o player.");
			
			GameTable.this.addBehaviour(new ShowCardAndPassBehaviour());
		}
		
	}

	private class ShowCardAndPassBehaviour extends CyclicBehaviour{

		private static final long serialVersionUID = 429285396743326047L;

		@Override
		public void action(){
			
			MessageTemplate turnTemplate = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId(Player.PLAYER_TO_TABLE_TURN)
			);
			
			ACLMessage message = myAgent.receive(turnTemplate);
			if(message != null){
				
				// If player standed, is the table turn
				GameTable.this.isTableTurn = true;
				
				System.out.println("A mesa " + GameTable.this.getName() + " recebeu o INFORM do player " + message.getSender());

				// Increment the player points
				GameTable.this.playerPoints +=  Integer.valueOf(message.getContent());
				
				while(GameTable.this.isTableTurn){
					System.out.println("Still table turn...");
					this.getAndSendCard(message);
				}
			}else{
				this.block();
			}
		}
		
		private void getAndSendCard(ACLMessage message){

			String content = "";
			String conversationId = "";
			boolean isToSend = false;

			try{
				Card card = GameTable.this.getNewCard();
				content = card.toString();
				conversationId = GameTable.GAME_TABLE_CARDS;
				isToSend = true;
			}catch(OverTwentOneException e){
				content = e.getMessage();
				conversationId = GameTable.OVER_21;
				System.out.println("Table over 21");
				GameTable.this.isTableTurn = false;
			}catch(TwentOneException e){
				content = e.getMessage();
				conversationId = GameTable.WINNER_21;
				System.out.println("Table won! 21 made!");
				GameTable.this.isTableTurn = false;
				isToSend = true;
			}
			
			if(GameTable.this.isTableTurn && isToSend){
				
				ACLMessage reply = message.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent(content);
				reply.setConversationId(conversationId);
				myAgent.send(reply);
			}
		}
		
	}
	
	private class StartRoundBehaviour extends CyclicBehaviour{

		private static final long serialVersionUID = 429285396743326047L;

		@Override
		public void action(){
			
			MessageTemplate turnTemplate = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("new-round")
			);
			
			ACLMessage message = myAgent.receive(turnTemplate);
			if(message != null){
				System.out.println("Starting new round..");
				
				Deck deck = Deck.getInstance();
				deck.collectCards(GameTable.this.cards);
				GameTable.this.cards.clear();
				GameTable.this.currentRound = new Round(GameTable.this);
				GameTable.this.currentRound.startRound();
				GameTable.this.startGame();
			}else{
				this.block();
			}
		}
		
	}
	
	public Integer getPoints() {
		return points;
	}

	public Integer getPlayerPoints() {
		return playerPoints;
	}
}
