package domain;

public class Test {

	public static void main(String[] args) {
		
		Deck deck = Deck.getInstance();
		
		while(!deck.getCards().isEmpty()){
			System.out.println(deck.getRandCard());
			deck = Deck.getInstance();
		}
	}

}
