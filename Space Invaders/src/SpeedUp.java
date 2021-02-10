import java.util.Random;

import javax.swing.ImageIcon;

//Class containing the power up that speeds up your ship.

public class SpeedUp extends Character{
	
	public SpeedUp(){
		
		//Power Up image.
		ImageIcon speedUpImage = new ImageIcon("src/sprites/boost2.png");
		setImage(speedUpImage.getImage());
		
		//Set up a random variable to create a random x coordinate to place the bomb at.
		Random generator = new Random();
		
		setx(generator.nextInt(Constants.SCREEN_WIDTH));
		sety(Constants.BOOST_STARTy);
		
	}
}
