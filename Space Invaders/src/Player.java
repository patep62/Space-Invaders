import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

//The player class.

public class Player extends Character{
	
	
	public Player() {
		
		//Player or spaceship image.
		ImageIcon playerIMG = new ImageIcon("src/sprites/ship4.png");
		setImage(playerIMG.getImage());
		
		setx(Constants.PLAYER_STARTx);
		sety(Constants.PLAYER_STARTy);
		
		this.visible = true;
		
	}
	
	//Method that moves the player based on the key the user presses.
	//The key press will change dx.
	
	public void move() {
		
		x = x + dx;
		
		//This checks to make sure the player does not run off the screen. If they try to, they will be halted at the edge.
		if(x <= 2) {
			
			x = 2;
			
		}
		
		if (x >= 500) {
			
			x = 500;
			
		}
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) {
			
			//Check to see if the player is speed boosted by the power up.
			//If the player is boosted, we want to make sure they stay boosted after another key press.
			
			if(dx == 10 || dx == -10) {
				dx = -10;
			}
			else {
				dx = -5;
			}
		}
		
		if(key == KeyEvent.VK_RIGHT) {
			
			if(dx == -10 || dx == 10) {
				dx = 10;
			}
			else {
				dx = 5;
			}
		}
	}
}
