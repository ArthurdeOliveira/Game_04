package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

import com.gcstudios.world.World;

public class Enemy extends Entity{
	
	private static int gravity = 2;
	
	public static BufferedImage[] enemy1;
	public static BufferedImage[] enemyHurt;
	
	public boolean hurt = false;
	public boolean right = false, left = true ;
	
	private int frames = 0,maxFrames = 10,index = 0, maxIndex = 1;
	
	public int vida = 2;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		      
		enemy1 = new BufferedImage[2];
		enemyHurt = new BufferedImage[2];
		
		for(int i = 0; i < 2; i++) {
			enemy1[i] = Game.spritesheet.getSprite(0 + (i*16), 64, 16, 16);
			}
		for(int i = 0; i < 2; i++) {
			enemyHurt[i] = Game.spritesheet.getSprite(32 + (i*16), 64, 16, 16);
			}
	}
	
	public void tick() {
		if(vida <2) {
			
			hurt = true;
		if(vida == 0) {
			hurt = false;
			Game.entities.remove(this);
		}
		}
		
		if(World.isFree((int) x, (int) (y+gravity)))	{
			y+=gravity;
		}else {
		if(right && World.isFree((int) (x+speed),(int) y))	{
			x+=speed;
			if(World.isFree((int)x+16, (int)y+1)) {
				right = false;
				left = true;
			}else if(!World.isFree(this.getX()+1, this.getY()))	{
				right = false;
				left = true;
			}
		}else if(left && World.isFree((int) (x-speed),(int) y)){
			x-=speed;
			if(World.isFree((int)x- 16, (int)y+1)) {
				right = true;
				left = false;
			}else if(!World.isFree(this.getX()-1, this.getY())) {
				right = true;
				left = false;
			}
		}
		}
		
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index =0;
			}
		}
		
	}

	public void render(Graphics g) {
		if(!hurt) {
		g.drawImage(enemy1[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
		}else {
			g.drawImage(enemyHurt[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
		}
	}
}
