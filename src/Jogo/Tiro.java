package Jogo;

import java.awt.Graphics;
import java.awt.Image;

public class Tiro {
	private int posX;
	private int posY;
	private int velX;
	private Image sprite;
	private final int COLS = 4;
	private int time;
	private boolean left;

	public Tiro(int posX, int posY) {
		sprite = Utils.getInstance().loadImage("image/cd.png");
		this.posX = posX;
		this.posY = posY;
		this.velX = 10;
	}

	public void draw(Graphics g) {
		g.drawImage(sprite, posX, posY, null);
	}

	public void update() {
		posX += velX;
	}

	public boolean verificarFim() {
		if (posY <= 0) {
			return true;
		}
		return false;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCOLS() {
		return COLS;
	}

	public void setVelX(int velX) {
		this.velX = velX;
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

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
}
