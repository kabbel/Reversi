import java.util.ArrayList;

public class Tree_old {
	
	private int player;
	private int depth;
	private double tlim;
	private Node root;
	
	public Tree_old(Game game, int player, int depth, double tlim){
		this.player = player;
		this.depth = depth;
		this.tlim = tlim;
		root = new Node(game,player);
		
	}
	
	public int[] search(){
		
		int[] res = alphaBeta(root,depth,Integer.MIN_VALUE,Integer.MAX_VALUE);
		
		return res;
	}
	
	private int[] alphaBeta(Node state, int depth, int a, int b){
		
		int[] bestMove = null;
		int v = Integer.MIN_VALUE;
		double t0 = System.currentTimeMillis();
		
		for (int[] move : state.data.legalMoves(state.player)){
			Game nextState = copy(state.data);
			nextState.move(move[0], move[1], player);
			int temp = maxValue(new Node(nextState,-1*state.player),player,depth,a,b);
			if (temp > v){
				v = temp;
				bestMove = move;
			}
			double t = System.currentTimeMillis();
			if (t-t0 > tlim){
				break;
			}
		}
		
		return bestMove;
	}
	
	private int maxValue(Node state, int player, int depth, int a, int b) {
		if (depth != 0){
			state.addChildren();
		}
		
		if (state.data.legalMoves(state.player).isEmpty() || depth == 0){
			return state.data.score(player);
		}
		int v = Integer.MIN_VALUE;
		
		for (Node child : state.children){
			v = Math.max(v, minValue(child,player,depth-1,a,b)); 
			if (v >= b) return v;
			a = Math.max(a, v);
		}		
		return v;
	}
	
	private int minValue(Node state, int player, int depth, int a, int b){
		if (depth != 0){
			state.addChildren();
		}
		
		if (state.data.legalMoves(state.player).isEmpty() || depth == 0){
			return state.data.score(player);
		}
		int v = Integer.MAX_VALUE;
		
		for (Node child : state.children){
			v = Math.min(v, maxValue(child,player,depth-1,a,b)); 
			if (v <= a) return v;
			b = Math.min(b, v);
		}		
		return v;
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
	
	public static class Node {
		private int player;
		private Game data;
		private ArrayList<Node> children;
		
		public Node(Game game, int player){
			this.data = game;
			this.player = player;
			children = new ArrayList<>();
		}
		
		public void addChildren(){
			ArrayList<int[]> moves = data.legalMoves(player);
			for (int[] move : moves){
				Game state = copy(data);
				state.move(move[0], move[1], player);
				children.add(new Node(state,-1*player));
			}
		}
		
		public Game getGame() {
			return data;
		}
		
	}
	
	


}
