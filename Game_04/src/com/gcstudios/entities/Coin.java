package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

public class Coin extends Entity {
	
	public static BufferedImage[] coin;
	public static BufferedImage coin1;
	
	private int frames = 0,maxFrames = 10,index = 0, maxIndex = 2;
	public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	
		coin = new BufferedImage[3];
		coin1 = Game.spritesheet.getSprite(48, 48,16, 16);
		for(int i = 0; i < 3; i++) {
			coin[i] = Game.spritesheet.getSprite(48 + (i*16), 48, 16, 16);
			}
	}
	
	public void tick() {
	frames++;
	if (frames == maxFrames) {
		frames = 0;
		index++; 
		if (index > maxIndex) {
			index =0;
		}
	}
	for(int i = 0; i < Game.entities.size();i++) {
		Entity e = Game.entities.get(i);
		if(e instanceof Player) {
			if(Entity.isColidding(this, e)) {
				System.out.println("Coleta");
				Player.currentCoins++;
				Game.entities.remove(this);
				break;
			}
			}
		}

	}
	
	public void render(Graphics g) {
		g.drawImage(coin[index], this.getX() - Camera.x, this.getY()- Camera.y, null);
	}
}
