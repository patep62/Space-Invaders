import javax.swing.JFrame;

//The main class for Space Invaders.

public class Space_Invaders extends JFrame {
	
	public Space_Invaders() {
		
		initBoard();
		
	}
	
	public void initBoard() {
		
		this.add(new Board());
		this.setTitle("Space Invaders");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args) {
		
		new Space_Invaders();
		
	}
}
