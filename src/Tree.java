
public class Tree {
	
	private int player;
	private int depth;
	private double tlim;
	private double t0;
	private Game state;
	
	public Tree(Game game, int player, int depth, double tlim){
		this.player = player;
		this.depth = depth;
		this.tlim = tlim;
		this.state = game;
		
	}
	
	public int[] search(){
		
		t0 = System.currentTimeMillis();
		return maxValue(state,player,depth,Integer.MIN_VALUE,Integer.MAX_VALUE).getMove();
	}
	
	
	
	private Pair maxValue(Game state, int player, int depth, int a, int b) {
		
		if (state.legalMoves(player).isEmpty() || depth == 0){
			return new Pair(state.score(player),null);
		}
		int v = Integer.MIN_VALUE;
		int[] bestMove = null;
		
		for (int[] move : state.legalMoves(player)){
			Game nextState = copy(state);
			nextState.move(move[0], move[1], player);
			int temp = minValue(nextState,-1*player,depth-1,a,b).getValue();
			if (temp > v){
				v = temp;
				bestMove = move;
			} 
			if (v >= b) return new Pair(v,bestMove);
			a = Math.max(a, v);
			
			double t = System.currentTimeMillis();
			if (t-t0 > tlim){
				break;
			}
		}		
		return new Pair(v,bestMove);
	}
	
	private Pair minValue(Game state, int player, int depth, int a, int b){
		
		if (state.legalMoves(player).isEmpty() || depth == 0){
			return new Pair(state.score(player),null);
		}
		int v = Integer.MAX_VALUE;
		int[] bestMove = null;
		
		for (int[] move : state.legalMoves(player)){
			Game nextState = copy(state);
			nextState.move(move[0], move[1], player);
			int temp = maxValue(nextState,-1*player,depth-1,a,b).getValue();
			if (temp < v){
				v = temp;
				bestMove = move;
			} 
			if (v <= a) return new Pair(v,bestMove);
			b = Math.min(b, v);
			
			double t = System.currentTimeMillis();
			if (t-t0 > tlim){
				break;
			}
		}		
		return new Pair(v,bestMove);
	}
	
	private static Game copy(Game game){
		int[][] copyBoard = new int[10][10];
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				copyBoard[i][j] = game.getBoard()[i][j];
			}
		}
		return new Game(copyBoard);
	}
	
	public static class Pair {
		private int value;
		private int[] move;
		
		public Pair(int value, int[] move){
			this.value = value;
			this.move = move;
		}
		public int getValue(){
			return value;
		}
		public int[] getMove(){
			return move;
		}
		
	}
	
}
