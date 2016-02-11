package Jogo;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

public class Boss {
	private int frameX;
	private int time;
	private int posX;
	private int posY;
	private int velX;
	private int velY;
	private int life;
	private Image spriteBoss;
	private List<Raio> raios;
	private boolean atirar;

	public Boss() {
		spriteBoss = Utils.getInstance().loadImage("image/boss.png");
		posX = Utils.getInstance().getWidth() - 150;
		velX = 0;
		posY = 430;
		life = 15;

	}

	public void draw(Graphics g) {

		g.drawImage(spriteBoss, posX, posY, Utils.BOSS_WIDTH + posX, Utils.BOSS_HEIGHT + posY,
				(frameX * Utils.BOSS_WIDTH), 0, (Utils.BOSS_WIDTH * frameX) + Utils.BOSS_WIDTH, Utils.BOSS_HEIGHT,
				null);

	}

	public Raio colisao(List<Raio> raios, Josiandro josiandro) {
		if (josiandro.getRespawning()) {
			return null;
		}
		
		for (Raio raio : raios) {
			if (raio.getPosX() >= josiandro.getPosX()
					&& raio.getPosX() <= josiandro.getPosX()
							+ (josiandro.getSpriteStoppedLeft().getWidth(null) / josiandro.getSpriteNumber())
					&& raio.getPosY() >= josiandro.getPosY()
					&& raio.getPosY() <= josiandro.getPosY() + josiandro.getSpriteStoppedLeft().getHeight(null)) {
				return raio;
			}
		}
		return null;
	}

	public boolean colisao(Josiandro josiandro) {
		if (josiandro.getRespawning()) {
			return false;
		}
		
		if (this.getPosX() >= josiandro.getPosX() - this.getSpriteBoss().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteWalkingRight().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteBoss().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteWalkingRight().getHeight(null))
				&& this.getPosX() >= josiandro.getPosX() - this.getSpriteBoss().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteStoppedRight().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteBoss().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteStoppedRight().getHeight(null))
				&& this.getPosX() >= josiandro.getPosX() - this.getSpriteBoss().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteWalkingLeft().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteBoss().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteWalkingLeft().getHeight(null))
				&& this.getPosX() >= josiandro.getPosX() - this.getSpriteBoss().getWidth(null)
				&& this.getPosX() <= josiandro.getPosX() + (josiandro.getSpriteStoppedLeft().getWidth(null) / josiandro.getSpriteNumber())
				&& this.getPosY() + this.getSpriteBoss().getHeight(null) >= josiandro.getPosY()
				&& this.getPosY() <= josiandro.getPosY() + (josiandro.getSpriteStoppedLeft().getHeight(null))) {
			return true;

		}
		
		return false;
	}

	public void update() {
		posX -= velX;

	}

	public List<Raio> getRaios() {
		return raios;
	}

	public void setRaios(List<Raio> raios) {
		this.raios = raios;
	}

	public boolean isAtirar() {
		return atirar;
	}

	public void setAtirar(boolean atirar) {
		this.atirar = atirar;
	}

	public void setSpriteBoss(Image spriteBoss) {
		this.spriteBoss = spriteBoss;
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

	public Image getSpriteBoss() {
		return spriteBoss;
	}

	public void setSpriteVerme(Image spriteBoss) {
		this.spriteBoss = spriteBoss;
	}

}
