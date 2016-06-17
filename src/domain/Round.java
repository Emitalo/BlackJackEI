package domain;

import agent.GameTable;

public class Round {

	private GameTable gameTable;
	public String winner;
	
	public Round(GameTable gameTable) {
		
		this.gameTable = gameTable;

	}
	
	public void endCurrentRound(){
		
		int tablePoints = this.gameTable.getPoints();
		int playerPoints = this.gameTable.getPlayerPoints();

		if(tablePoints == 21 && playerPoints == 21){
			this.winner = "Deu empate";
		}
		else if(tablePoints > 21 && playerPoints > 21){
			this.winner = "Ninguem ganhou";
		}
		else if ((tablePoints == 21 || tablePoints < 21)  && playerPoints > 21){
			this.winner = "Mesa ganhou";
		}
		else if((playerPoints == 21 || playerPoints < 21) && tablePoints > 21){
			this.winner = "Jogador ganhou";
		}
		else if(tablePoints > 21 && playerPoints > 21){
			if(tablePoints > playerPoints){
				this.winner = "Mesa ganhou";
			}
			else{
				this.winner = "Jogador ganhou";
			}
					
				
		}
		else{
			this.winner = "";
		}
	}
	
	public void startRound(){
		this.gameTable.points = 0;
		this.gameTable.playerPoints = 0;
		this.gameTable.firstRound = true;
	}
}
