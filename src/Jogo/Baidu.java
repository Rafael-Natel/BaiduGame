package Jogo;

import java.awt.Graphics;
import java.awt.Image;

public class Baidu {

	private int posX;
	private int posY;
	private Image baidu;

	public Baidu() {
		baidu = Utils.getInstance().loadImage("image/coletavel.png");
		this.posX = 1000;
		this.posY = 600;
	}

	public void draw(Graphics g) {
		g.drawImage(baidu, posX, posY, null);
	}

	public boolean colisao(Josiandro josiandro) {
		if (this.getPosX() >= josiandro.getPosX()
				&& this.getPosX() <= josiandro.getPosX()
						+ (josiandro.getSpriteWalkingLeft().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + josiandro.getSpriteWalkingLeft().getHeight(null)) {

			return true;
		}

		return false;

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

	public Image getBaidu() {
		return baidu;
	}

	public void setBaidu(Image baidu) {
		this.baidu = baidu;
	}

}
