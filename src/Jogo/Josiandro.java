package Jogo;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Josiandro {

	private final int COLS = 6;
	private Image spriteWalkingResRight;
	private Image spriteWalkingResLeft;
	private Image spriteStoppedResRight;
	private Image spriteStoppedResLeft;
	private Image spriteStoppedLeft;
	private Image spriteStoppedRight;
	private Image spriteWalkingLeft;
	private Image spriteWalkingRight;
	private Image spriteJumpingLeft;
	private Image spriteJumpingRight;
	private Image spriteJumpingResLeft;
	private Image spriteJumpingResRight;
	private List<Tiro> tiros;
	private boolean walking = false;
	private boolean left = false;
	private boolean jumping = false;
	private int controlePulo = 0;
	private int pontos;
	private int posX;
	private int posY;
	private int frameX;
	private boolean atirar = false;
	private int time;
	private int hp;
	private int dmg;
	private int velX;
	private boolean respawning;
	private int spriteNumber = 6;
	private Timer respawnTimer;
	private Timer reloadingTimer;
	private boolean reloading = false;
	private int tamanhoDoPasso = 20;

	public Josiandro() {
		spriteWalkingResLeft = Utils.getInstance().loadImage("image/andandoDE.png");
		spriteWalkingResRight = Utils.getInstance().loadImage("image/andandoDD.png");
		spriteStoppedResRight = Utils.getInstance().loadImage("image/paradoDD.png");
		spriteStoppedResLeft = Utils.getInstance().loadImage("image/paradoDE.png");
		spriteStoppedLeft = Utils.getInstance().loadImage("image/paradoE.png");
		spriteStoppedRight = Utils.getInstance().loadImage("image/ParadoD.png");
		spriteWalkingLeft = Utils.getInstance().loadImage("image/AndandoE.png");
		spriteWalkingRight = Utils.getInstance().loadImage("image/AndandoD.png");
		spriteJumpingResLeft = Utils.getInstance().loadImage("image/puloDE.png");
		spriteJumpingResRight = Utils.getInstance().loadImage("image/puloDD.png");
		spriteJumpingLeft = Utils.getInstance().loadImage("image/puloE.png");
		spriteJumpingRight = Utils.getInstance().loadImage("image/puloD.png");
		posY = Utils.getInstance().getHeight() - 215;
		frameX = 0;
		pontos = 0;
		hp = 3;
		dmg = 5;
		tiros = new ArrayList<Tiro>();
	}

	public void update() {
		if (time == 5) {
			time = 0;

			frameX++;
			if (frameX % COLS == 0) {
				frameX = 0;
			}

			if (jumping) {
				pular();
			} else if (walking && left) {
				posX -= tamanhoDoPasso;
			} else if (walking && !left) {
				posX += tamanhoDoPasso;
			}
			if (atirar) {
				Tiro tiro = new Tiro(
						this.getPosX() + (this.getSpriteStoppedRight().getWidth(null) / (this.getSpriteNumber() * 2)),
						this.getPosY() + (this.getSpriteWalkingRight().getHeight(null) / 2));
				if (left) {
					tiro.setVelX(tiro.getVelX() * -1);
				}
				// Tiro tiro = new Tiro(this.getPosX(), this.getPosY());
				tiros.add(tiro);
				atirar = false;
				reloading = true;

				Mp3 mp3a = new Mp3();
				mp3a.carregar("arremesso.mp3");
				mp3a.start();

				// Inicializando contador
				this.reloadingTimer = new Timer();

				TimerTask tarefa = new TimerTask() {
					@Override
					public void run() {
						reloading = false;
					}
				};

				reloadingTimer.schedule(tarefa, 300);
			}

		} else {
			time++;
		}

		// remove tiros que sairam da tela
		List<Tiro> removeTiros = new ArrayList<Tiro>();

		for (int i = 0; i < tiros.size(); i++) {
			Tiro t = tiros.get(i);

			if (t.getPosX() <= 0 || t.getPosX() >= Utils.getInstance().getWidth()) {
				removeTiros.add(t);
			}
		}

		for (Tiro tiro : removeTiros) {
			tiros.remove(tiro);
		}
	}

	void reset() {
		this.posX = 0;

	}

	private void pular() {
		int passos = 8;

		if (controlePulo < passos) {
			posY -= 15;
		} else if (controlePulo > passos) {
			posY += 15;
		}
		if (left) {
			posX -= 20;
		} else {
			posX += 20;
		}
		controlePulo++;
		if (controlePulo == (2 * passos + 1)) {
			jumping = false;
			controlePulo = 0;
		}

	}

	public void draw(Graphics g) {

		// g.drawImage(paradoD, 79, 112, null);
		// g.drawImage(paradoD, 0, 0, 117, 832, 0, 0, 117, 832, null);
		// g.drawImage(paradoD, 0, 0, 117, 832, 0, 0, 117, 832, null);

		if (!respawning) {
			if (jumping && left) {
				g.drawImage(spriteJumpingLeft, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (jumping && !left) {
				g.drawImage(spriteJumpingRight, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (walking && left) {
				g.drawImage(spriteWalkingLeft, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (walking && !left) {
				g.drawImage(spriteWalkingRight, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (!walking && left) {
				g.drawImage(spriteStoppedLeft, posX, posY, Utils.JOSIANDRO_STOPPED_WIDTH + posX,
						Utils.JOSIANDRO_STOPPED_HEIGHT + posY, (frameX * Utils.JOSIANDRO_STOPPED_WIDTH), 0,
						(Utils.JOSIANDRO_STOPPED_WIDTH * frameX) + Utils.JOSIANDRO_STOPPED_WIDTH,
						Utils.JOSIANDRO_STOPPED_HEIGHT, null);
			} else if (!walking && !left) {
				g.drawImage(spriteStoppedRight, posX, posY, Utils.JOSIANDRO_STOPPED_WIDTH + posX,
						Utils.JOSIANDRO_STOPPED_HEIGHT + posY, (frameX * Utils.JOSIANDRO_STOPPED_WIDTH), 0,
						(Utils.JOSIANDRO_STOPPED_WIDTH * frameX) + Utils.JOSIANDRO_STOPPED_WIDTH,
						Utils.JOSIANDRO_STOPPED_HEIGHT, null);
			}
		} else {

			if (walking && left) {
				g.drawImage(spriteWalkingResLeft, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (walking && !left) {
				g.drawImage(spriteWalkingResRight, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (!walking && left) {
				g.drawImage(spriteStoppedResLeft, posX, posY, Utils.JOSIANDRO_STOPPED_WIDTH + posX,
						Utils.JOSIANDRO_STOPPED_HEIGHT + posY, (frameX * Utils.JOSIANDRO_STOPPED_WIDTH), 0,
						(Utils.JOSIANDRO_STOPPED_WIDTH * frameX) + Utils.JOSIANDRO_STOPPED_WIDTH,
						Utils.JOSIANDRO_STOPPED_HEIGHT, null);
			} else if (!walking && !left) {
				g.drawImage(spriteStoppedResRight, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (jumping && left) {
				g.drawImage(spriteJumpingResLeft, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			} else if (jumping && !left) {
				g.drawImage(spriteJumpingResRight, posX, posY, Utils.JOSIANDRO_WALKING_WIDTH + posX,
						Utils.JOSIANDRO_WALKING_HEIGHT + posY, (frameX * Utils.JOSIANDRO_WALKING_WIDTH), 0,
						(Utils.JOSIANDRO_WALKING_WIDTH * frameX) + Utils.JOSIANDRO_WALKING_WIDTH,
						Utils.JOSIANDRO_WALKING_HEIGHT, null);
			}
		}

	}

	public Tiro colisao(List<Tiro> tiros, Worms worm) {
		for (Tiro tiro : tiros) {
			if (tiro.getPosX() >= tiro.getPosX() - worm.getSpriteVerme().getWidth(null)
					&& worm.getPosX() <= tiro.getPosX() + tiro.getSprite().getWidth(null)
					&& worm.getPosY() + worm.getSpriteVerme().getHeight(null) >= tiro.getPosY()
					&& worm.getPosY() <= tiro.getPosY() + tiro.getSprite().getHeight(null)) {

				Mp3 mp3d = new Mp3();
				mp3d.carregar("vermeMorto.mp3");
				mp3d.start();

				return tiro;
			}
		}

		return null;
	}

	public Tiro colisao(List<Tiro> tiros, Boss boss) {
		for (Tiro tiro : tiros) {
			if (boss != null && tiro.getPosX() >= boss.getSpriteBoss().getWidth(null)
					&& boss.getPosX() <= tiro.getPosX() + tiro.getSprite().getWidth(null)) {
				return tiro;
			}
		}
		return null;
	}

	public int getSpriteNumber() {
		return spriteNumber;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public boolean isAtirar() {
		return atirar;
	}

	public void setAtirar(boolean atirar) {
		if (!reloading) {
			this.atirar = atirar;
		}
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setRespawning(boolean isSpawning) {
		this.respawning = isSpawning;

		if (isSpawning) {
			// Inicializando contador
			this.respawnTimer = new Timer();

			TimerTask tarefa = new TimerTask() {
				@Override
				public void run() {
					setRespawning(false);
				}
			};

			respawnTimer.schedule(tarefa, 3000);
		} else {
			this.respawnTimer.cancel();
		}
	}

	public boolean getRespawning() {
		return this.respawning;
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

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getFrameX() {
		return frameX;
	}

	public void setFrameX(int frameX) {
		this.frameX = frameX;
	}

	public Image getSpriteStoppedLeft() {
		return spriteStoppedLeft;
	}

	public void setSpriteStoppedLeft(Image spriteStoppedLeft) {
		this.spriteStoppedLeft = spriteStoppedLeft;
	}

	public Image getSpriteStoppedRight() {
		return spriteStoppedRight;
	}

	public void setSpriteStoppedRight(Image spriteStoppedRight) {
		this.spriteStoppedRight = spriteStoppedRight;
	}

	public Image getSpriteWalkingLeft() {
		return spriteWalkingLeft;
	}

	public void setSpriteWalkingLeft(Image spriteWalkingLeft) {
		this.spriteWalkingLeft = spriteWalkingLeft;
	}

	public Image getSpriteWalkingRight() {
		return spriteWalkingRight;
	}

	public void setSpriteWalkingRight(Image spriteWalkingRight) {
		this.spriteWalkingRight = spriteWalkingRight;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isWalking() {
		return walking;
	}

	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	public Image getSpriteJumpingLeft() {
		return spriteJumpingLeft;
	}

	public void setSpriteJumpingLeft(Image spriteJumpingLeft) {
		this.spriteJumpingLeft = spriteJumpingLeft;
	}

	public Image getSpriteJumpingRight() {
		return spriteJumpingRight;
	}

	public void setSpriteJumpingRight(Image spriteJumpingRight) {
		this.spriteJumpingRight = spriteJumpingRight;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void setTiros(List<Tiro> tiros) {
		this.tiros = tiros;
	}

	public List<Tiro> getTiros() {
		return this.tiros;
	}

}
