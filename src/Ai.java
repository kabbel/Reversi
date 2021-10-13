public class Ai {

//	private Game game = new Game();
	private int player;
	private int depth;
	private double tlim;
	
	public Ai(int player, int depth, double tlim) {
		this.player = player;
		this.depth = depth;
		this.tlim = tlim*1000;
	}
	
	public int[] move(Game game) {
		
		if (game.legalMoves(player).isEmpty())
			return null;
		else {
			Tree tree = new Tree(game,player,depth,tlim);
			return tree.search();
		}
	}
	

}
