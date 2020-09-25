package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class UI {
	
	public  static BufferedImage life;
	public  static BufferedImage life2;
	public  static BufferedImage life3;
	public  static BufferedImage life4;
	

	public void render(Graphics g) {
		
		
	//	g.setColor(Color.RED);
		//g.fillRect(20, 20,70, 18);
		//g.setColor(Color.GREEN);
		//g.fillRect(20, 20, (int) ((Player.vida )*0.7), 18);
		//g.setColor(Color.RED);
		g.setColor(Color.WHITE);
		g.setFont(Game.newfont);
		g.drawString("Modas: "+Player.currentCoins+"/"+Player.maxCoins,610,15);
	
		
	
		
		
	
	}
	
}
