import java.util.ArrayList;

public class Game {
	private int[][] board = new int[10][10];
	private static final int WHITE = 1, BLACK = -1;
	
	public Game() {
		board[4][4] = WHITE;
		board[4][5] = BLACK;
		board[5][4] = BLACK;
		board[5][5] = WHITE;
	}
	
	public Game(int[][] state){
		this.board = state;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public void move(int x, int y, int player) {
		flip(x, y, player);
		set(x, y, player);
	}
	
	public void print(){
		System.out.print("  0 1 2 3 4 5 6 7 8 9\n");
		for(int i = 0; i < 10; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < 10; j++) {
				if (board[i][j] == 1) 
					System.out.print("W");
				else if (board[i][j] == -1) 
					System.out.print("B");
				else
					System.out.print(".");
				System.out.print(" ");
			}
			System.out.print("\n");
		}
		System.out.print("\nWhites score: " + score(1));
		System.out.print("\nBlacks score: " + score(-1));
		System.out.println("\n----------------");

	}
	public void print(int player){
		System.out.print("  0 1 2 3 4 5 6 7 8 9\n");
		for(int i = 0; i < 10; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < 10; j++) {
				if (board[i][j] == 1) 
					System.out.print("W");
				else if (board[i][j] == -1) 
					System.out.print("B");
				else if (legalMove(i,j,player))
					System.out.print("x");
				else
					System.out.print(".");
				System.out.print(" ");
			}
			System.out.print("\n");
		}
		System.out.print("\nWhites score: " + score(1));
		System.out.print("\nBlacks score: " + score(-1));
		System.out.println("\n----------------");

	}
	
	
	public boolean legalMove(int x, int y, int player) {
		if (x < 0 || x > 9){
			return false;
		}
		if (y < 0 || y > 9){
			return false;
		}
		if (board[x][y] != 0){
			return false;
		}
		
		for(int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				boolean neighbour = false;
				int dist = 1;
				int xtemp = x + dist*i;
				int ytemp = y + dist*j;
				boolean insideBoard = (xtemp >= 0 && xtemp <= 9 && ytemp >= 0 && ytemp <= 9);
				while (insideBoard && board[xtemp][ytemp] == -1*player){
					neighbour = true;
					dist++;
					xtemp = x + dist*i;
					ytemp = y + dist*j;
					insideBoard = (xtemp >= 0 && xtemp <= 9 && ytemp >= 0 && ytemp <= 9);
				}
				if (neighbour && insideBoard && board[xtemp][ytemp] == player){
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<int[]> legalMoves(int player){
		ArrayList<int[]> moves = new ArrayList<>();
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				if (legalMove(i, j, player)){
					int[] move = {i,j};
					moves.add(move);
				}
			}
		}
		return moves;
	}
	
	public int score(int player){
		int score = 0;
		for (int i = 0; i<10; i++) {
			for (int j = 0; j < 10; j++) {
				if (board[i][j] == player){
					score++;
				}
			}
		}
		
		return score;
	}
	
	
	private void set(int x, int y, int player) {
		board[x][y] = player;
	}
	
	private void flip(int x, int y, int player){
		
		for(int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				boolean neighbour = false;
				int dist = 1;
				int xtemp = x + dist*i;
				int ytemp = y + dist*j;
				boolean insideBoard = (xtemp >= 0 && xtemp <= 9 && ytemp >= 0 && ytemp <= 9);
				while (insideBoard && board[xtemp][ytemp] == -1*player){
					neighbour = true;
					dist++;
					xtemp = x + dist*i;
					ytemp = y + dist*j;
					insideBoard = (xtemp >= 0 && xtemp <= 9 && ytemp >= 0 && ytemp <= 9);
				}
				if (neighbour && insideBoard && board[xtemp][ytemp] == player){
					for (int k = 1; k < dist; k++) {
						set(x+k*i, y+k*j, player);
					}
				}
			}
		}
	}
	
}
