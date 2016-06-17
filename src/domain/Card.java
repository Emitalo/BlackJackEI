package domain;

public class Card {
	
	private String suit;
	private String value;
	
	public Card(String suit, String value){
		setSuit(suit);
		setValue(value);
	}
	
	private Card(Card card){
		setSuit(card.getSuit());
		setValue(card.getValue());
	}

	public Card clone(String value){
		Card card = new Card(this);
		card.setValue(value);
		
		return card;
	}

	public String getSuit() {
		return suit;
	}
	
	private void setSuit(String suit) {
		this.suit = suit;
	}
	
	public String getValue() {
		return value;
	}
	
	private void setValue(String value) {
		this.value = value;
	}
	
	public Integer getRealValue(){
		
		Integer realValue = 0;
		String value = this.getValue();
		
		if(value == "J" || value == "Q" || value == "K"){
			realValue = 10;
		}
		else if(value == "A"){
			realValue = 1;
		}
		else{
			realValue = new Integer(value);
		}
		
		return realValue;
	}
	
	@Override
	public String toString(){
		return this.value + " de " + this.suit;
	}
}
