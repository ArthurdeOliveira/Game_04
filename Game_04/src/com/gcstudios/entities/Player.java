package com.gcstudios.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.graficos.UI;
import com.gcstudios.main.Game;
import com.gcstudios.main.Sound;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;


public class Player extends Entity{
	
	public static boolean right,left;

	private double gravity = 0.4;
	private double vspd = 0;
	
	public static int vida = 1;
	
	
	private BufferedImage[] playerRight;
	private BufferedImage[] playerLeft;
	private BufferedImage lastDir;
	private int dir = 1;
	
	public static int currentCoins = 0;
	public static int maxCoins = 0;
	public static double Pspeed ;
	public boolean jump = false, isJumping = false, moved =false, jumpLeft,jumpRight;
	public int jumpHeight = 38, jumpFrames = 0;
	private int frames = 0,maxFrames = 10,index = 0, maxIndex = 1;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		Pspeed = speed;
		playerRight = new BufferedImage[2];
		playerLeft = new BufferedImage[2];
		
		UI.life = Game.spritesheet.getSprite(0, 80, 16, 16);
		UI.life2 = Game.spritesheet.getSprite(16, 80, 16, 16);
		UI.life3 = Game.spritesheet.getSprite(32, 80, 16, 16);
		UI.life4 = Game.spritesheet.getSprite(48, 80, 16, 16);
		
		for(int i = 0; i < 2; i++) {
			playerRight[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
			}
			for(int i = 0; i < 2; i++) {
				playerLeft[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
				}
	}
	
	public void tick(){
		depth = 2;
		vspd+=gravity;
		if(!World.isFree((int)x,(int)(y+1)) && jump)
		{
			Sound.jump.play();
			vspd = -6;
			jump = false;
		}
		
		if(!World.isFree((int)x,(int)(y+vspd))) {
			
			int signVsp = 0;
			if(vspd >= 0)
			{
				signVsp = 1;
			}else  {
				signVsp = -1;
			}
			while(World.isFree((int)x,(int)(y+signVsp))) {
				y = y+signVsp;
			}
			vspd = 0;
		}
		
		y = y + vspd;
		
		if(World.isFree((int) x, (int) (y+gravity)) && isJumping == false) {
			y+=gravity;
			for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColiddingJump(this, e)) {
					
					//Lógica aqui
					isJumping = true;
					
					((Enemy)e).vida--;
					
					
				}
			}
		
			}
		}
		
		if(vida == 0) {
			World.restartGame();
		}
		jumpRight = true;
		if(right && World.isFree((int) (x+Pspeed ), (int) y)) {
			lastDir = Entity.PLAYER_SPRITE_RIGHT;
			jumpRight = true;
			jumpLeft = false;
			moved = true;
			dir = 1;
			x+=Pspeed;
		}else if(left && World.isFree((int) (x-Pspeed), (int) y)) {
			lastDir = Entity.PLAYER_SPRITE_LEFT;
			jumpLeft = true;
			jumpRight = false;
			moved = true;
			dir = -1;
			x-=Pspeed;	
		}
		if(jump) {
			if(!World.isFree(this.getX(),this.getY()+1)) {
				Sound.jump.play();
				isJumping = true;
			}else {
				jump = false;
			}
		}
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
			
				y-=2;
				jumpFrames+=2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jumpHeight = 38;
					jump = false;
					jumpFrames = 0;
				
				}
			}else {
				
				isJumping = false;
				
				jump = false;
				jumpFrames = 0;
			}
		}
		
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index =0;
				}
			}
		}
		
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this, e)) {
					vida--;
					
				}
			}
		
			}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT );
	}
	
	public void render (Graphics g) {
		if(!isJumping) {
		if(dir == 1) {
			g.drawImage(playerRight[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
		}else if(dir == -1) {
			g.drawImage(playerLeft[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
		}
		}else {
			if (jumpLeft) {
				g.drawImage( PLAYER_JUMP_LEFT,  this.getX() - Camera.x, this.getY()- Camera.y, null);
			}else if(jumpRight) {
				g.drawImage( PLAYER_JUMP_RIGHT,  this.getX() - Camera.x, this.getY()- Camera.y, null);
			}
	}
	}
	

	

}
