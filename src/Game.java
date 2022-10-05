import java.util.Random;

public class Game {
	// #1, must declare players initially so that they will never be 
	// null
	private Player player1 = new GUIPlayer();
	private Player player2 = new ComputerPlayer();
	
	
	private Random die;
	private Spinner spinner;
	private final String LOSER_SPIN = "grunt";
	private final int LOSER_ROLL = 1;
	
	public Game(){
		//Player player1 = new GUIPlayer();
		//Player player2 = new ComputerPlayer();
		die = new Random();
		spinner = new Spinner();
	}
	
	/*
	 * Method will play one game between the players.
	 */
	public void playGame(){
		printStartGameMessage();
		Player whoseTurn = player1;
		while(!winner()){
			int roundScore = takeATurn(whoseTurn);
			whoseTurn.addToScore(roundScore);
			// Switch whose turn it is.
			if(whoseTurn == player1){
				whoseTurn = player2;
			}
			else{
				whoseTurn = player1;
			}
		}
		printEndGameMessage();
	}
	
	/*
	 * One player's turn.  Ends because
	 * - roll a 1
	 * - spin a "GRUNT"
	 * - or Player decides to stop
	 * 
	 * Return the number of points to add to the player
	 */
	public int takeATurn(Player whoseTurn){
		int roundScore = 0;
		//#4, never rechecks for a winner
		if (winner()) {
			printEndGameMessage();
		}
		boolean keepGoing = true;
		printStartRoundMessage(whoseTurn);
		while(keepGoing){
			// #3 the plus one will stop the die from rolling a 0, changing the possible numbers to 1-6
			int roll = die.nextInt(6) + 1;
			String spin = spinner.spin();
			System.out.println(roll+ " "+ spin);
			//#6 spin is a string so should be .equals
			//#7 of the roll is a one and the spin is a "grunt" the player should lose
			//all points and reset their score 
			if(roll == LOSER_ROLL){
				System.out.println("Lose a turn.");
				if (losePoints(spin)) {
					System.out.println("Too bad!  Lose all your points.");
					whoseTurn.resetScore();
				}
				return 0;
			}
			else if (losePoints(spin)) {
				System.out.println("Too bad!  Lose all your points.");
				whoseTurn.resetScore();
				return 0;
			}
			else{
				roundScore = roundScore + roll;
				System.out.println("Round total is currently: "+roundScore);
				keepGoing = whoseTurn.rollAgain(roundScore);
			}
		}
		return roundScore;
	}
	
	public boolean losePoints(String spin) {
		if (spin.equals(LOSER_SPIN.toUpperCase())){
			return true;
		}
		return false;
	}

	// True if one of the players has won the game.
	public boolean winner(){
		return player1.hasWon() || player2.hasWon();
	}
	
	/* 
	 * These methods are for printing messages to the console to follow the game.
	 */
	public void printStartRoundMessage(Player whoseTurn){
		System.out.println("New Round!  "+ whoseTurn.getName()+" 's turn."); 
		System.out.println(player1);
		System.out.println(player2);
	}
	
	public void printEndGameMessage(){
		if(player1.hasWon()){
			System.out.println("Winner is "+player1.getName());
		}
		else{
			System.out.println("Winner is "+player2.getName());
		}
	}
	
	public void printStartGameMessage(){
		System.out.println("Let's Play Pig!");
	}
}
