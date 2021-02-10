import javax.swing.ImageIcon;

//Class that creates the bomb object the aliens will randomly drop on the player.

public class Bomb extends Character{
	
	//Takes the alien's x and y coordinates and drops a bomb from that location.
	//Very similar to the shoot class.
	
	public Bomb(int x, int y){
		
		//Bomb image.
		ImageIcon bombIMG = new ImageIcon("src/sprites/bullet9.png");
		setImage(bombIMG.getImage());
		
		//It just so works out that the bombs drop nicely from the alien sprite so no offset is needed.
		setx(x);
		sety(y);
		
	}
}
