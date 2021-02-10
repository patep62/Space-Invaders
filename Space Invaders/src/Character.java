import java.awt.Image;

//A larger very useful class which all other entities in this game are created from.
//Contains are the basic information and methods required to use an entity.

public class Character {
	
	//The instance variables every object of this class will have.
	
	//An xy coordinate showing their position.
	public int x;
	public int y;
	
	//An image representing their character.
	Image image;
	
	//Visibility of the character, off by default.
	boolean visible = false;
	
	//The direction vector of the character. Not all characters may be moving so not all objects use this variable.
	int dx = 0;
	int dy = 0;
	
	//A state of being blow up. Only applies to player and aliens.
	boolean dying = false;
	
	
	public Character() {
		
		//Set visibility to true when creating an object of this class.
		visible = true;
		
	}
	
	public Image getImage() {
		
		return this.image;
		
	}
	
	public void setImage(Image sprite) {
		
		this.image = sprite;
		
	}
	
	public void setx(int X) {
		
		this.x = X;
		
	}
	
	public int getx() {
		
		return this.x;
		
	}
	
	public void sety(int Y) {
		
		this.y = Y;
		
	}
	
	public int gety() {
		
		return this.y;
		
	}
	
	public boolean isVisible() {
		
		return this.visible;
		
	}
	
	public void setVisible() {
		
		this.visible = true;
		
	}
	
	public void setInvisible() {
		
		this.visible = false;
		
	}
	
	public void setDying() {
		
		this.dying = true;
		
	}
	
	public boolean isDying() {
		
		return dying;
		
	}
	
}






