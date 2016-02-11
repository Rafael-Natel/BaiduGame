package Jogo;

import java.awt.Graphics;
import java.awt.Image;

public class Raio {
	private int posX;
	private int posY;
	private final int COLS = 4;
	private int time;
	private int frameX;
	private int velX;
	private Image sprite;

	public Raio() {
		sprite = Utils.getInstance().loadImage("image/tiroBoss.png");
		posY = 606;
		velX = -6;

		posX = 1215;
	}

	public void update() {
		posX += velX;
	}

	public void draw(Graphics g) {
		g.drawImage(sprite, posX, posY, null);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelY(int velY) {
		this.velX = velY;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getFrameX() {
		return frameX;
	}

	public void setFrameX(int frameX) {
		this.frameX = frameX;
	}

	public int getCOLS() {
		return COLS;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}
}
