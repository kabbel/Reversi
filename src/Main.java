import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		//aiVsAi();
		play(8);
		//randomTest();
	}
	
	public static void play(int depth){
		Game game = new Game();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Do you wanna play as white or black? [1/-1] : ");
		int player = sc.nextInt();
		System.out.print("Set time limit for AI (seconds) : ");
		double tlim = sc.nextDouble();
		System.out.println("Your possible moves will be marked by an 'x', W and B represents white and black pieces respectively.");
		Ai ai = new Ai(-1*player,depth,tlim);
		int aiPlayer = -1*player;
		if (player == -1)
			game.print(-1);
		else 
			game.print();
		while (true) { 
		
			if (player == -1) {
				if (game.legalMoves(player).isEmpty()) {
					break;
				}
				System.out.print("Enter x-coordinate: (0-9) ");
				int x = sc.nextInt();
				System.out.print("Enter y-coordinate: (0-9) ");
				int y = sc.nextInt();
			
				if (game.legalMove(x, y, player)){
					game.move(x, y, player);
					game.print();
					// ai moves
					if (game.legalMoves(aiPlayer).isEmpty()) {
						break;
					}
					int[] action = ai.move(game);
					game.move(action[0], action[1], aiPlayer);
					System.out.println("White played [" + action[0] + " " + action[1] + "]");
					game.print(player);
				} else {
					System.out.print("Invalid move, try again.");
				}
			} else {
				//ai moves
				if (game.legalMoves(aiPlayer).isEmpty()) {
					break;
				}
				int[] action = ai.move(game);
				game.move(action[0], action[1], aiPlayer);
				System.out.println("Black played [" + action[0] + " " + action[1] + "]");
				game.print(player);
				//human move
				if (game.legalMoves(player).isEmpty()) {
					break;
				}
				while (true) {
					System.out.print("Enter x-coordinate: (0-9) ");
					int x = sc.nextInt();
					System.out.print("Enter y-coordinate: (0-9) ");
					int y = sc.nextInt();
			
					if (game.legalMove(x, y, player)){
						game.move(x, y, player);
						game.print();
						break;
					} else {
						System.out.print("Invalid move, try again.");
					}
				}
			}
			
			
		}	
		sc.close();
		int playerScore = game.score(player);
		int aiScore = game.score(aiPlayer);
		if (playerScore > aiScore){
			if (player == 1) {
				System.out.println("Game is over, white won with the score " + playerScore + "-" + aiScore);;
			} else {
				System.out.println("Game is over, black won with the score " + playerScore + "-" + aiScore);;
			}
		} else if (playerScore < aiScore) {
			if (player == 1) {
				System.out.println("Game is over, white lost with the score " + playerScore + "-" + aiScore);;
			} else {
				System.out.println("Game is over, black lost with the score " + playerScore + "-" + aiScore);;
			}
		} else {
			System.out.print("Game is over, it is a draw " + playerScore + "-" + aiScore);
		}
	}
	
	public static void aiVsAi(){
		//Ai Vs Ai black -1 white 1
		Game game = new Game();
		Ai ai1 = new Ai(-1,4,1);
		Ai ai2 = new Ai(1,2,1);
		int i = 0;
		double t0 = System.currentTimeMillis();
		while (true) {
			if (game.legalMoves(-1).isEmpty()) {
				break;
			}
			int[] action1 = ai1.move(game);
			game.move(action1[0], action1[1], -1);
			i++;
			if (game.legalMoves(1).isEmpty()) {
				break;
			}
			int[] action2 = ai2.move(game);
			game.move(action2[0], action2[1], 1);
			
			i++;
			if (i%10 == 0) {
				System.out.println("Game state after " +  i + " moves.");
				game.print();
			}
		}
		double t = System.currentTimeMillis();
		int score1 = game.score(-1);
		int score2 = game.score(1);
		System.out.println("Game is over, final score (b-w) " + score1 + "-" + score2);
		game.print();
		double s = (t-t0)/1000;
		System.out.println("Total elapsed time: " + s + " seconds for " + i + " moves." );
	}

	public static void random(){
		//Random moves (black, -1) against ai (white, 1)
				Game g = new Game();
				Ai ai = new Ai(1,4,5);
				Random rand = new Random();
				
				int i = 0;
				while (true) {
					if (g.legalMoves(-1).isEmpty()) {
						break;
					}
					ArrayList<int[]> m  = g.legalMoves(-1);
					int r = rand.nextInt(m.size());
					g.move(m.get(r)[0], m.get(r)[1], -1);
					
					if (g.legalMoves(1).isEmpty()) {
						break;
					}
					int[] action = ai.move(g);
					g.move(action[0], action[1], 1);
					
					i++;
					if (i%10 == 0) {
						System.out.println("Game state after " +  2*i + " moves.");
						g.print();
					}
				}
				g.print();
				int s1 = g.score(-1);
				int s2 = g.score(1);
				System.out.println("Game is over, final score (b-w) " + s1 + "-" + s2);
				
		
	}
	
	public static void randomTest(){
		Ai ai = new Ai(1,4,1);
		Random rand = new Random(5);
		int win = 0;
		double t0 = System.currentTimeMillis();
		for (int j = 0; j < 20; j++ ){
			Game g = new Game();
			while (true) {
				if (g.legalMoves(-1).isEmpty()) {
					break;
				}
				ArrayList<int[]> m  = g.legalMoves(-1);
				int r = rand.nextInt(m.size());
				g.move(m.get(r)[0], m.get(r)[1], -1);
				
				if (g.legalMoves(1).isEmpty()) {
					break;
				}
				int[] action = ai.move(g);
				g.move(action[0], action[1], 1);
				
			}
			if (g.score(1) > g.score(-1)) {
				win++;
			} else {
				g.print();
			}
		}
		double t = System.currentTimeMillis();
		System.out.println("The Ai won " + win + " games out of 20 against random moves.");
		double s = (t-t0)/1000;
		System.out.println("Total elapsed time: " + s);

	}
}
