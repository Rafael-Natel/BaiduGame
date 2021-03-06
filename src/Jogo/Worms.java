package Jogo;

import java.awt.Graphics;
import java.awt.Image;

public class Worms {
	private final int COLS = 4;
	private int frameX;
	private int time;
	private int posX;
	private int posY;
	private int velX;
	private int velY;
	private int life;
	private Image spriteVerme;

	public Worms() {
		spriteVerme = Utils.getInstance().loadImage("image/WormE.png");
		posX = Utils.getInstance().getWidth() + 200;
		velX = 30;
		posY = 570;
		life = 1;

	}

	public void draw(Graphics g) {

		g.drawImage(spriteVerme, posX, posY, Utils.WORM_WIDTH + posX, Utils.WORM_HEIGHT + posY,
				(frameX * Utils.WORM_WIDTH), 0, (Utils.WORM_WIDTH * frameX) + Utils.WORM_WIDTH, Utils.WORM_HEIGHT,
				null);

	}

	public boolean colisao(Josiandro josiandro) {
		if (josiandro.getRespawning()) {
			return false;
		}
		
		if (this.getPosX() >= josiandro.getPosX() - this.getSpriteVerme().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteWalkingRight().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteVerme().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteWalkingRight().getHeight(null))
				&& this.getPosX() >= josiandro.getPosX() - this.getSpriteVerme().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteStoppedRight().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteVerme().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteStoppedRight().getHeight(null))
				&& this.getPosX() >= josiandro.getPosX() - this.getSpriteVerme().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteWalkingLeft().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteVerme().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteWalkingLeft().getHeight(null))
				&& this.getPosX() >= josiandro.getPosX() - this.getSpriteVerme().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteStoppedLeft().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteVerme().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteStoppedLeft().getHeight(null))) {
			Mp3 mp3b = new Mp3();
			mp3b.carregar("dano.mp3");
			mp3b.start();
			return true;
		}
		{
			return false;
		}
	}

	public void update() {
		if (time == 3) {
			time = 0;

			frameX++;
			if (frameX % COLS == 0) {
				frameX = 0;
			}

			posX -= velX;

		} else {
			time++;
		}

	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
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

	public void setVelX(int velY) {
		this.velX = velY;
	}

	public int getFrameX() {
		return frameX;
	}

	public void setFrameX(int frameX) {
		this.frameX = frameX;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Image getSpriteVerme() {
		return spriteVerme;
	}

	public void setSpriteVerme(Image spriteVerme) {
		this.spriteVerme = spriteVerme;
	}

	public int getCOLS() {
		return COLS;
	}

}