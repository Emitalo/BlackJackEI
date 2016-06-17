package domain;

public class Test {

	public static void main(String[] args) {
		
		Deck deck = Deck.getInstance();
		
		while(!deck.getCards().isEmpty()){
			System.out.println(deck.getTopCard());
			deck = Deck.getInstance();
		}
	}

}
