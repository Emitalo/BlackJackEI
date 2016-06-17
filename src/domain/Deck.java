package domain;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	private static Deck instance = null;
	private ArrayList<Card> cards;
	
	public static Deck getInstance(){
		
		if(instance == null){
			instance = new Deck();
		}
		
		return instance;
	}
	
	private Deck(){
		this.cards = new ArrayList<Card>();
		this.initCards();
	}
	
	private void initCards(){
		this.createCards("Copas");
		this.createCards("Ouros");
		this.createCards("Espadas");
		this.createCards("Paus");
	}

	private void createCards(String suit){
		Card prototype = new Card(suit, "A");
		this.cards.add(prototype);
		this.cards.add(prototype.clone("J"));
		this.cards.add(prototype.clone("Q"));
		this.cards.add(prototype.clone("K"));
		
		for(int i=2; i <= 10; i++){
			this.cards.add(prototype.clone(Integer.toString(i)));
		}
	}
	
	public Card getTopCard(){
		Card card = this.cards.remove(0);
		return card;
	}
	
	public Card getRandCard(){
		
		Random random = new Random();
		int randIndex = random.nextInt(this.cards.size());
		Card card = this.cards.remove(randIndex);
		
		return card;
	}
	
	public ArrayList<Card> getCards(){
		return this.cards;
	}
	
	public void collectCards(ArrayList<Card> cards){
		System.out.print("Recolhendo cartas...");
		this.cards.addAll(cards);
	}
}
