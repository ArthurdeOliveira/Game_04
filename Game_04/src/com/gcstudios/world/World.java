package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.graficos.UI;
import com.gcstudios.world.World;
import com.gcstudios.entities.Coin;
import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++){
				for(int yy = 0; yy < map.getHeight(); yy++){
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFffffff) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
						if(yy-1 >= 0 &&  pixels[xx + ((yy-1) * map.getWidth())] == 0xFFffffff ) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Game.spritesheet.getSprite(16, 16, 16, 16));
						}else if(xx+1 > 0 &&  pixels[xx+ 1 + ((yy) * map.getWidth())] == 0xFF000000 && xx-1 > 0 &&  pixels[xx-1 + ((yy) * map.getWidth())] == 0xFF000000) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Game.spritesheet.getSprite(32, 48, 16, 16));
						}
						else if(xx-1 > 0 &&  pixels[xx-1 + ((yy) * map.getWidth())] == 0xFF000000 ) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Game.spritesheet.getSprite(0, 48, 16, 16));
						}
						else if(xx+1 > 0 &&  pixels[xx+ 1 + ((yy) * map.getWidth())] == 0xFF000000  ) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Game.spritesheet.getSprite(16, 48, 16, 16));
						}
						
					}else if (pixelAtual == 0xff1b00ff) {
							
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelAtual == 0xffff0000) {
						Enemy enemy = new Enemy(xx*16,yy*16,16,16,1,Entity.ENEMY1);
						Game.entities.add(enemy);
					}else if(pixelAtual == 0xfff8f800) {
						Coin coin = new Coin(xx*16,yy*16,16,16,1,Coin.coin1);
						Game.entities.add(coin);
						Player.maxCoins++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext  / TILE_SIZE ;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE ;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE ;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static void restartGame(){
		Player.maxCoins = 0;
		Player.currentCoins = 0;
		Game.entities.clear();
		
		Game.entities = new ArrayList<Entity>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(WIDTH/2 - 30,HEIGHT/2 ,16,2,1,Entity.PLAYER_SPRITE_RIGHT);
		Player.vida = 1;
		Game.world = new World("/level1.png");
		
		
		Game.entities.add(Game.player);

		return;
	}
	
	public void render(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
