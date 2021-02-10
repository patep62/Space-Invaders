import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

//This is the board class where I do most of the game logic.

public class Board extends JPanel{
	
	//All the instance variables we will need throughout this main game class.
	
	//Instantiating the objects we will need.
	private Player player;
	private Timer timer;	//For main game loop, action listener.
	private SpeedUp speedUp;
	
	//Lists containing all the objects that we will create of these types.
	//They all have a relatively small max, however when we exceed it, we will start overwriting the older objects.
	private Shoot[] bullets;
	private Alien[] aliens;
    private Bomb[] bombs;
    
    //Some integers we will need.
    private int alienDirection = 1;
    private int bullet_index = 0;
    private int bomb_index = 0;
    private int isDyingPlayer_delayTimer = 0;
	private int isDyingAlien_delayTimer = 0;
	private int aliens_killed = 0;
	
	//Finally, some boolean variables or flags that dictate the state of the game.
	private boolean gameOver = false;
	private boolean won = false;
	private boolean gameStarted = false;
    private boolean speedUpUsed = false;
    
    //Random object for all the random things we may need to do.
	private Random generator = new Random();

	public Board() {
		
		//Initialize the board.
		
		//I'm using a null layout just because the game is based off an x and y Cartesian plane,
		//Using a null layout means I am able to place things exactly without much worry.
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new TAdapter());		
		
		//Instantiating the game loop.
		timer = new Timer(Constants.DELAY, new GameCycle());
		timer.start();
		
		//Instantiating all the lists and player class.
		player = new Player();
		aliens = new Alien[35];
		bullets = new Shoot[15];
		bombs = new Bomb[20];
		
		//Initialize all the aliens.
		alienInit();
		
	}
	
	
	public void alienInit() {
		
		int index = 0;
		
		//Nested for loops that create an ixj grid of aliens.
		for(int i = 0; i < 5; i++) {
			
			for(int j = 0; j < 7; j++) {
				
				//Some modifiers to starting locations to adjust for the image size.
				Alien alien = new Alien(Constants.ALIEN_STARTx + j*65, Constants.ALIEN_STARTy + i*50);
				aliens[index] = alien;
				index += 1;
			}
		}		
	}
	
	public void drawStartScreen (Graphics g) {
		
		ImageIcon start = new ImageIcon("D:\\start1.png");
		g.drawImage(start.getImage(), 30, 50, this);	
		
	}
	public void drawGameOverScreen(Graphics g) {
		
		//Game over screen with 2 states/outcomes. A winning screen or loosing screen.
		
		if(won) {
			
			var won = new ImageIcon("D:\\end.png");
			g.drawImage(won.getImage(), 150, 50, this);
		}
		
		
		if(!won) {
			
			var lost = new ImageIcon("D:\\lost.png");
			g.drawImage(lost.getImage(), 0, 50, this);
			
		}
	}
	
	public void drawBackground(Graphics g) {
		
		ImageIcon bg = new ImageIcon("D:\\bg6.gif");
		g.drawImage(bg.getImage(), 0, 0, this);
		
	}
	
	public void drawPlayer(Graphics g) {
		
		//Only draw if player is set to visible.
		if(player.isVisible()) {
			
			g.drawImage(player.getImage(), player.getx(), player.gety(), this);
			
			if(player.isDying()) {
				
				//I use a delay timer here because I want the explosion to appear on the screen and stay for some time before disappearing.
				isDyingPlayer_delayTimer += 1;
			}   
		}
		
		if(isDyingPlayer_delayTimer == 30) {
			
			//Once the desired delay is reached, I make turn off the visibility of the player.
			player.setInvisible();
			
		}
	}
	
	public void drawAliens(Graphics g) {
		
		//Stop drawing the aliens if the game is over.
		if(!gameOver) {
			
			//Iterate through all the aliens in the list of aliens to move them.
			for(Alien alien : aliens) {
				
				//Only move the alien if it is still alive (visible).
				if(alien.isVisible()) {
					
					g.drawImage(alien.getImage(), alien.getx(), alien.gety(), this);
					
					if(alien.isDying()) {
						
						//Another delay timer just so the explosion is able to be seen.
						isDyingAlien_delayTimer += 1;
						
					}
				}
				
				if(isDyingAlien_delayTimer == 20) {
					
					alien.setInvisible();
					
					//Reset the delay timer for the next alien.
					isDyingAlien_delayTimer = 0;
					
				}
			}
		}
	}
	
	public void drawShot(Graphics g) {
		
		//Iterate through the list of current bullets to move them across the screen.
		for(Shoot bullet : bullets) {
			
			//Sometimes java will try to access the bullet list before any bullets are created resuting in an error.
			try {
				
				if(bullet.isVisible()) {
					g.drawImage(bullet.getImage(), bullet.getx(), bullet.gety(), this);
				}	
			}
			
			catch(Exception e) {
				
				break;

			}
		}
	}
	
	public void drawBombs(Graphics g) {
		
		//Make sure the bombs don't appear in the end game screen.
		if(!gameOver) {
			
			//Iterate through each bomb in the list of current bombs.
			for(Bomb bomb : bombs) {
				
				try {
					
					if(bomb.isVisible()) {
						
						g.drawImage(bomb.getImage(), bomb.getx(), bomb.gety(), this);
						
					}	
				}
				
				catch(Exception e) {
					
					break;
					
				}
			}
		}
	}
	
	public void drawSpeedUp(Graphics g) {
		
		try {
			
			if(speedUp.isVisible()) {
				g.drawImage(speedUp.getImage(), speedUp.getx(), speedUp.gety(), this);
			}
		}
		
		catch(Exception e) {}
		
	}
	
	
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
	}
	
	public void doDrawing(Graphics g) {
		
        drawBackground(g);
        
        //Wait to draw the game stuff until the user starts the game.
        if(gameStarted) {
			drawPlayer(g);
			drawShot(g);
			drawAliens(g);
			drawBombs(g);
			drawSpeedUp(g);
			
			if(gameOver) {
				drawGameOverScreen(g);
			}
        }
        
        else {
        	
        	drawStartScreen(g);
        }
        
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void update(){
		
		//The update method where I do all the checking and moving of stuff across the screen.
		//I have put almost everything in a try/catch to prevent runtime errors that sometimes occur when java tries the access certain objects before they are created.
		
		try {
			
			//If the player is not dying, move the player in the direction that the player chooses.
			if(!player.isDying()) {
				
				player.move();
			}
			
			//Get the current x location of the player, as well as Pxr, which is the other end of the player image.
			//Pxr This is used for hit boxes for the bombs.
			
			int Px = player.getx();
			int Pxr = Px + Constants.PLAYER_WIDTH;
			
			//If the speed boost has been places, this will access its current location.
			int speedUpx = speedUp.getx();
			
			//This is a common check I will do throughout this method to see if the player hit box has been hit.
			//In this case I check to see if the player has gotten a boost.
			
			if(speedUp.isVisible() && Pxr>=speedUpx && Px<=speedUpx+Constants.BOOST_WIDTH)  {
				
				//Double the players speed and set the boost invisible.
				player.dx *= 2;
				speedUp.setInvisible();
				
			}
		}
		
		catch(Exception e) {}
		
		//A boolean that will tell us if the aliens have reached an edge of the screen.
		boolean alienDrop = false;
		
		try {
			
			//Here I get the x locations of the first and last aliens in the first row (can be any row).
			int x_first = aliens[0].getx();
			int x_last = aliens[6].getx();
			
			//If the aliens are moving in the positive x direction, I check the far end.
			if(alienDirection == 1) {
				
				if(x_last > 450) {
					
					//If the last alien is about to hit the edge (450 on the x), I flip the direction of the aliens.
					//I also set the drop flag to true which will drop all the aliens one row.
					alienDirection = -1;
					alienDrop = true;
					
				}
			}
			
			//If the aliens are moving in the negetive x direction, I check zero end.
			else if(alienDirection == -1) {
				
				if(x_first < -60) {
					
					//If the last alien is about to hit the edge (-60 on the x), I flip the direction of the aliens.
					//I also set the drop flag to true which will drop all the aliens one row.
					alienDirection = 1;
					alienDrop = true;
					
				}
				
			}
			
			//Now I iterate through each alien, moving each one.
			for(Alien alien : aliens) {
				
				alien.setx(alien.x += 2*alienDirection);
				
				if(alienDrop) {
					
					alien.sety(alien.y += 10);
					
				}
			}
		}
		
		catch(Exception e) {}
		
		try {
			
			//Here I iterate through all the aliens and drop a bomb from them if the random chance allows it.
			for(Alien alien : aliens) {
				
				int chance = generator.nextInt(Constants.BOMB_DROP_RATE);
				
				if(chance == 1) {
				
					Bomb bomb = new Bomb(alien.getx(), alien.gety());
					bombs[bomb_index] = bomb;
					
					//I will keep adding the bombs to the list until it max's out, at which point I go back and start overwriting the older bombs. 
					if(bomb_index == 19) {
						
						bomb_index = 0;
						
					}
					
					else {
						
						bomb_index += 1;
					}
					
				}
			}
		}
		
		catch(Exception e) {}
		
		//Here I generate a boost if the chance allows it. First I make sure one has not been placed already. 
		if(!speedUpUsed) {
			
			int chance = generator.nextInt(Constants.BOOST_RATE);
			if(chance == 1) {
				
				speedUp = new SpeedUp();
				speedUpUsed = true;
				
			}
		}
		
		try {
			
			//Iterate through each of the bombs to see if one of them have hit the player.
			for(Bomb bomb : bombs) {
				
				int Px = player.getx();
				int Py = player.gety();
				int Bx = bomb.getx();
				int By = bomb.gety();
				
				//Check the x and y coordinates of the bomb and the player and compare them. If they match, there was a hit.
				if(bomb.isVisible() && Bx>=Px && Bx<= Px+Constants.PLAYER_WIDTH && By>=Py && By<=Py+Constants.PLAYER_HEIGHT) {
					
					//If the player is hit, swap the player image with the exploding image.
					//The delay will keep that explosion there for a short while before removing it.
					
					var ii = new ImageIcon("D:\\explode.gif");
					player.setImage(ii.getImage());
					player.setDying();
					bomb.setInvisible();
					gameOver = true;
					
				}
				
				//Here I move all the bombs across the screen.
				int bomby = bomb.gety();
				bomby += 3;
				
				//If the bomb runs off the screen, remove it.
				if(bomby > 700) {
					
					bomb.setInvisible();
					
				}
				
				else {
					
					bomb.sety(bomby);
					
				}
			}
		}
			
		catch(Exception e){}
		
		try {
			
			//Iterate through each of the bullets, checking if they have hit an alien, or run off the screem.
			for(Shoot bullet : bullets) {
				
					for(Alien alien : aliens) {
						
						int Ax = alien.getx();
						int Ay = alien.gety();
						int Bx = bullet.getx();
						int By = bullet.gety();
						
						if(bullet.isVisible() && alien.isVisible() && Bx>=Ax && Bx<= Ax+Constants.ALIEN_WIDTH && By>=Ay && By<=Ay+Constants.ALIEN_HEIGHT) {
							
							//Same boat as the player being hit by a bomb.
							//Swap the alien image with the explosion image and use the delay timer to have it show for a while.
							//Destroy the bullet on impact.
							
							bullet.setInvisible();
							var ii = new ImageIcon("D:\\explode.gif");
							alien.setImage(ii.getImage());
							
							//A little offset for the explosion image so that I appears in the middle of the alien image.
							alien.setx(alien.getx()+65);
							alien.sety(alien.gety()+40);
							alien.setDying();
							aliens_killed += 1;
							
							//Keep track of the aliens killed. Once all are killed, the game is won.
							if(aliens_killed == 35) {
								
								gameOver = true;
								won = true;
							}
							
						}
						
					}	
				
				//Now I move each bullet across the screen.
				int shooty = bullet.gety();
				shooty -= 15;
				if(shooty < 0) {
					bullet.setInvisible();
				}
				
				else {
					
					bullet.sety(shooty);
					
				}
			}
		}
		
		catch(Exception e){}
		
	}
	
	public void doGameCycle() {
		
		if(gameStarted) {
			update();
			repaint();
		}
	}
	
	private class GameCycle implements ActionListener {

	        @Override
	        public void actionPerformed(ActionEvent e) {

	            doGameCycle();
	        }
	}
	
	private class TAdapter extends KeyAdapter{
		
		@Override
        public void keyPressed(KeyEvent e) {
			
			player.keyPressed(e);
			int x = player.getx();
			int y = player.gety();
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_SPACE) {				
				
				if(!player.isDying()) {
					
					//Create a new bullet everytime the space button is pressed.
					Shoot bullet = new Shoot(x,y);
					bullets[bullet_index] = bullet;
					
					//just like the bombs, I set a cap and the max bullets and then start overwriting.
					if(bullet_index == 10) {
						bullet_index = 0;
					}
					
					else {
						bullet_index += 1;
					}
				}
	        }
			
			//For the user to press to start the game.
			if(key == KeyEvent.VK_ENTER) {				
				gameStarted = true;
			}
	    }	
	}
}
