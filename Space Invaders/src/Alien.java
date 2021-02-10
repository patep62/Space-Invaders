import javax.swing.ImageIcon;

//Alien class. Each alien is an object of this class.

public class Alien extends Character{
	
	//This constructor takes an xy coordinate and creates an alien at that location.
	
	public Alien(int x, int y) {
		
		//The alien image
		ImageIcon playerIMG = new ImageIcon("src/sprites/alien3.png");
		setImage(playerIMG.getImage());
		
		setx(x);
		sety(y);
		
		this.visible = true;
			
	}
	
}
