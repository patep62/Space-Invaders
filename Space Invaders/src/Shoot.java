import javax.swing.ImageIcon;

//Class controlling the firing mechanism from the player.

public class Shoot extends Character{
	
	//Takes in the player's current x and y coordinates and fires a bullet from that location.
	
	public Shoot(int x, int y) {
		
		//Bullet image
		ImageIcon shootIMG = new ImageIcon("src/sprites/bullet5.png");
		setImage(shootIMG.getImage());
		
		//These numbers are user to slightly offset the placement of the bullet so that it looks like it is coming out of the ship.
		setx(x + 16);
		sety(y - 8);
		
	}	
}
