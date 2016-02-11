package Jogo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javazoom.jl.player.Player;

public class Jogo extends Canvas {
	private BufferStrategy strategy;
	private boolean gameRunning = true;
	private Image imageMenu;
	private Image imageGame;
	private Image imageCredits;
	private boolean startGame = false;
	private Josiandro josiandro;
	private Baidu baidu1;
	private boolean showBaidu1 = true;
	private Baidu baidu2;
	private boolean showBaidu2 = true;
	private Baidu baidu3;
	private boolean showBaidu3 = true;
	private List<Raio> raios;
	private List<Worms> worms;
	private Random random;
	private Image avida;
	private Image bvida;
	private Image cvida;
	private Image fase2;
	private Image fase3;
	private Image tuto;
	boolean colidiu = false;
	private Image winner;
	private Image winner1;
	private Image winner2;
	private Image winner3;
	private Image historia;
	private Image loose;
	private Boss boss;
	
	private Timer raioTimer;
	private Timer wormTimer;
	// Contador
	private Graphics2D g;
	private String fase;

	private Mp3 mp3;
	
	private boolean raioReloading;
	private long raioLastTime;
	private boolean wormSpawning;

	public Jogo() {
		JFrame container = new JFrame("Worm");

		container.setUndecorated(true);

		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Utils.getInstance().setWidth(fullScreen.width);
		Utils.getInstance().setHeight(fullScreen.height);

		System.out.println("Screen resolution: " + fullScreen.width + ", " + fullScreen.height);

		JPanel panel = (JPanel) container.getContentPane();

		panel.setPreferredSize(new Dimension(Utils.getInstance().getWidth(), Utils.getInstance().getHeight()));
		panel.setLayout(null);

		setBounds(0, 0, Utils.getInstance().getWidth(), Utils.getInstance().getHeight());
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addMouseListener(new MouseInputHandler());

		addKeyListener(new KeyInputHandler());

		init();

		container.setLocationRelativeTo(null);

		container.setVisible(true);

		requestFocus();

		try {
			Thread.sleep(1);
		} catch (Exception e) {
		}

		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	public void init() {

		// Tela Gameplay

		imageMenu = Utils.getInstance().loadImage("image/Play.png");
		imageMenu = imageMenu.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);

		imageCredits = Utils.getInstance().loadImage("image/credito.png");
		imageCredits = imageCredits.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);

		tuto = Utils.getInstance().loadImage("image/tutorial.png");
		tuto = tuto.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);

		avida = Utils.getInstance().loadImage("image/vida.png");
		bvida = Utils.getInstance().loadImage("image/2vida.png");
		cvida = Utils.getInstance().loadImage("image/3vida.png");

		// fases

		imageGame = Utils.getInstance().loadImage("image/Arte 2.png");
		imageGame = imageGame.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);
		fase2 = Utils.getInstance().loadImage("image/fase 2.png");
		fase2 = fase2.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);
		fase3 = Utils.getInstance().loadImage("image/fase3.png");
		fase3 = fase3.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);

		// Tela vitória/derrota

		winner = Utils.getInstance().loadImage("image/vitoria.png");
		winner = winner.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);
		
		winner1 = Utils.getInstance().loadImage("image/1vitoria.png");
		winner1 = winner1.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);
		
		winner2 = Utils.getInstance().loadImage("image/2vitoria.png");
		winner2 = winner2.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);
		
		winner3 = Utils.getInstance().loadImage("image/3vitoria.png");
		winner3 = winner3.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);
		
		loose = Utils.getInstance().loadImage("image/Derrota.png");
		loose = loose.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);

		historia = Utils.getInstance().loadImage("image/historia.png");
		historia = historia.getScaledInstance(Utils.getInstance().getWidth(), Utils.getInstance().getHeight(),
				Image.SCALE_DEFAULT);
		
		resetGame();
	}

	public void resetGame() {
		worms = new ArrayList<Worms>();
		josiandro = new Josiandro();
		raios = new ArrayList<Raio>();
		boss = new Boss();
		random = new Random();
		
		// Coletavel
		baidu1 = new Baidu();
		baidu1.setPosX(500);
		showBaidu1 = true;
		
		baidu2 = new Baidu();
		baidu2.setPosX(600);
		showBaidu2= true;
		
		baidu3 = new Baidu();
		baidu3.setPosX(860);
		baidu3.setPosY(550);
		showBaidu3 = true;
		
		raioLastTime = System.currentTimeMillis();
	}
	
	public void spawnWorm(int maxTime) {
		// Inicializando contador
		
		if (wormSpawning) {
			return;
		}
		
		wormSpawning = true;
		
		wormTimer = new Timer();

		TimerTask tarefa = new TimerTask() {
			@Override
			public void run() {
				worms.add(new Worms());
				wormSpawning = false;
			}
		};

		wormTimer.schedule(tarefa, 1000 * random.nextInt(maxTime));
	}
	
	public void spawnRaio() {
		// Inicializando contador
		
		if (raioReloading) {
			return;
		}
		
		raioReloading = true;
		
		raioTimer = new Timer();

		TimerTask tarefa = new TimerTask() {
			@Override
			public void run() {
				raios.add(new Raio());
				raioReloading = false;
				raioLastTime = System.currentTimeMillis();
			}
		};
		
		long currentTime = System.currentTimeMillis();
		long tempoSemRaio = currentTime - raioLastTime;
		
		if (tempoSemRaio > 4000) {
			raioTimer.schedule(tarefa, 100);
		} else {
			raioTimer.schedule(tarefa, (4000 - tempoSemRaio));
		}
	}

	public void fase1() {
		List<Tiro> tiros = josiandro.getTiros();

		// Desenha Fase
		g.drawImage(imageGame, 0, 0, null);
		
		if (showBaidu1) {
			baidu1.draw(g);
		}
		
		if (showBaidu1 && baidu1.colisao(josiandro)) {
			Mp3 mp3baidu = new Mp3();
			mp3baidu.carregar("coleta.mp3");
			mp3baidu.start();
			showBaidu1 = false;
			josiandro.setPontos(josiandro.getPontos() + 1);
		}

		// Atualiza personagens e monstros
		for (int i = 0; i < worms.size(); i++) {
			Worms worm = worms.get(i);
			worm.update();
			worm.draw(g);
		}

		josiandro.draw(g);
		josiandro.update();

		List<Worms> wormsMortos = new ArrayList<Worms>();

		for (int i = 0; i < worms.size(); i++) {
			Worms worm = worms.get(i);
			colidiu = worm.colisao(josiandro);
			if (colidiu) {
				josiandro.setHp(josiandro.getHp() - 1);
				josiandro.setRespawning(true);
				wormsMortos.add(worm);
			}
		}

		for (int i = 0; i < wormsMortos.size(); i++) {
			worms.remove(wormsMortos.get(i));
		}

		wormsMortos.clear();

		// Verifica se acertou
		for (Worms worm : worms) {
			Tiro tiroAcertou = josiandro.colisao(tiros, worm);

			if (tiroAcertou != null) {
				tiros.remove(tiroAcertou);
				wormsMortos.add(worm);
			}
		}

		for (int i = 0; i < wormsMortos.size(); i++) {
			worms.remove(wormsMortos.get(i));
		}

		for (Tiro tiro : josiandro.getTiros()) {
			tiro.draw(g);
			tiro.update();
		}
		
		spawnWorm(4);

		// area obstaculo fase 1
		if (!josiandro.getRespawning() && josiandro.getPosX() >= 560 && josiandro.getPosX() <= 700
				&& josiandro.getPosY() <= 670 && startGame && josiandro.isJumping() == false) {
			Mp3 mp3g = new Mp3();
			mp3g.carregar("buraco.mp3");
			mp3g.start();
			josiandro.setHp(josiandro.getHp() - 1);
			josiandro.setRespawning(true);
		}

		if (josiandro.getHp() == 3) {
			g.drawImage(cvida, 81, 70, null);
		}
		if (josiandro.getHp() == 2) {
			g.drawImage(bvida, 81, 70, null);
		}
		if (josiandro.getHp() == 1) {
			g.drawImage(avida, 81, 70, null);
		}
	}

	public void fase2() {
		List<Tiro> tiros = josiandro.getTiros();

		// Desenha Fase
		g.drawImage(fase2, 0, 0, null);
		
		if (showBaidu2) {
			baidu2.draw(g);
		}
		
		if (showBaidu3) {
			baidu3.draw(g);
		}
		
		if (showBaidu2 && baidu2.colisao(josiandro)) {
			Mp3 mp3baidu = new Mp3();
			mp3baidu.carregar("coleta.mp3");
			mp3baidu.start();
			showBaidu2 = false;
			josiandro.setPontos(josiandro.getPontos() + 1);
		}
		
		if (showBaidu3 && baidu3.colisao(josiandro)) {
			Mp3 mp3baidu = new Mp3();
			mp3baidu.carregar("coleta.mp3");
			mp3baidu.start();
			showBaidu3 = false;
			josiandro.setPontos(josiandro.getPontos() + 1);
		}

		// Atualiza personagens e monstros
		for (int i = 0; i < worms.size(); i++) {
			Worms worm = worms.get(i);
			worm.update();
			worm.draw(g);
		}

		josiandro.draw(g);
		josiandro.update();

		List<Worms> wormsMortos = new ArrayList<Worms>();

		for (int i = 0; i < worms.size(); i++) {
			Worms worm = worms.get(i);
			colidiu = worm.colisao(josiandro);
			if (colidiu) {
				josiandro.setHp(josiandro.getHp() - 1);
				josiandro.setRespawning(true);
				wormsMortos.add(worm);
			}
		}

		for (int i = 0; i < wormsMortos.size(); i++) {
			worms.remove(wormsMortos.get(i));
		}

		wormsMortos.clear();

		// Verifica se acertou
		for (Worms worm : worms) {
			Tiro tiroAcertou = josiandro.colisao(tiros, worm);

			if (tiroAcertou != null) {
				tiros.remove(tiroAcertou);
				wormsMortos.add(worm);
			}
		}

		for (int i = 0; i < wormsMortos.size(); i++) {
			worms.remove(wormsMortos.get(i));
		}

		for (Tiro tiro : josiandro.getTiros()) {
			tiro.draw(g);
			tiro.update();
		}

		// area obstaculo
		if (!josiandro.getRespawning() && josiandro.getPosX() >= 330 && josiandro.getPosX() <= 520
				&& josiandro.getPosY() <= 674 && josiandro.isJumping() == false) {
			Mp3 mp3g = new Mp3();
			mp3g.carregar("buraco.mp3");
			mp3g.start();
			josiandro.setHp(josiandro.getHp() - 1);
			josiandro.setRespawning(true);
		}
		// area obstaculo
		if (!josiandro.getRespawning() && josiandro.getPosX() >= 735 && josiandro.getPosX() <= 930
				&& josiandro.getPosY() <= 674 && josiandro.isJumping() == false) {
			Mp3 mp3g = new Mp3();
			mp3g.carregar("buraco.mp3");
			mp3g.start();
			josiandro.setHp(josiandro.getHp() - 1);
			josiandro.setRespawning(true);
		}

		spawnWorm(3);
		spawnRaio();
		
		if (josiandro.getHp() == 3) {
			g.drawImage(cvida, 81, 70, null);
		}
		if (josiandro.getHp() == 2) {
			g.drawImage(bvida, 81, 70, null);
		}
		if (josiandro.getHp() == 1) {
			g.drawImage(avida, 81, 70, null);
		}
	}

	public void fase3() {
		List<Tiro> tiros = josiandro.getTiros();

		// Desenha Fase
		g.drawImage(fase3, 0, 0, null);

		// Atualiza personagens e monstros
		for (int i = 0; i < worms.size(); i++) {
			Worms worm = worms.get(i);
			worm.update();
			worm.draw(g);
		}

		josiandro.draw(g);
		josiandro.update();

		boss.draw(g);

		List<Worms> wormsMortos = new ArrayList<Worms>();

		for (int i = 0; i < worms.size(); i++) {
			Worms worm = worms.get(i);
			colidiu = worm.colisao(josiandro);
			if (colidiu) {
				josiandro.setHp(josiandro.getHp() - 1);
				josiandro.setRespawning(true);
				wormsMortos.add(worm);
			}
		}

		for (int i = 0; i < wormsMortos.size(); i++) {
			worms.remove(wormsMortos.get(i));
		}

		wormsMortos.clear();

		// Verifica se acertou
		for (Worms worm : worms) {
			Tiro tiroAcertou = josiandro.colisao(tiros, worm);

			if (tiroAcertou != null) {
				tiros.remove(tiroAcertou);
				wormsMortos.add(worm);
			}
		}

		for (int i = 0; i < wormsMortos.size(); i++) {
			worms.remove(wormsMortos.get(i));
		}

		Tiro tiroAcertou = josiandro.colisao(tiros, boss);

		if (tiroAcertou != null) {
			tiros.remove(tiroAcertou);
			boss.setLife(boss.getLife() - 1);
		}

		Raio raioAcertou = boss.colisao(raios, josiandro);

		if (raioAcertou != null) {
			raios.remove(raioAcertou);
			josiandro.setRespawning(true);
			josiandro.setHp(josiandro.getHp() - 1);
		}

		boolean tocouBoss = boss.colisao(josiandro);

		if (tocouBoss) {
			josiandro.setRespawning(true);
			josiandro.setHp(josiandro.getHp() - 1);
			boss.setLife(boss.getLife() - 1);
		}

		for (Raio raio : raios) {
			raio.draw(g);
			raio.update();
		}

		for (Tiro tiro : josiandro.getTiros()) {
			tiro.draw(g);
			tiro.update();
		}

		spawnWorm(4);

		if (random.nextInt(100) == 0) {
			raios.add(new Raio());
		}

		if (josiandro.getHp() == 3) {
			g.drawImage(cvida, 81, 70, null);
		}

		if (josiandro.getHp() == 2) {
			g.drawImage(bvida, 81, 70, null);
		}

		if (josiandro.getHp() == 1) {
			g.drawImage(avida, 81, 70, null);
		}
	}

	public void gameLoop() {
		mp3 = new Mp3();
		mp3.carregar("menu.mp3");
		mp3.start();

		Mp3 mp3loser = new Mp3();
		mp3loser.carregar("gameover.mp3");

		Mp3 mp3winner = new Mp3();
		mp3winner.carregar("win.mp3");

		Mp3 mp3boss = new Mp3();
		mp3boss.carregar("boss.mp3");

		Mp3 mp3main = new Mp3();
		mp3main.carregar("main.mp3");

		boolean menuSoundRunning = true;
		boolean loserSoundRunning = false;
		boolean winnerSoundRunning = false;
		boolean bossSoundRunning = false;
		boolean mainSoundRunning = false;

		fase = "menu";

		while (gameRunning) {
			this.g = (Graphics2D) strategy.getDrawGraphics();

			g.setColor(Color.black);
			g.fillRect(0, 0, Utils.getInstance().getWidth(), Utils.getInstance().getHeight());

			try {
				Thread.sleep(20);
			} catch (Exception e) {

			}

			if (josiandro.getPosX() >= 1100) {
				if (fase == "fase1") {
					fase = "fase2";
					wormTimer.cancel();
					wormSpawning = false;
					worms.clear();
					josiandro.setPosX(0);
				} else if (fase == "fase2") {
					fase = "fase3";
					wormTimer.cancel();
					wormSpawning = false;
					worms.clear();
					josiandro.setPosX(0);
				}
			}

			if (fase == "menu") {
				// Inicia Música
				if (loserSoundRunning) {
					Player player = mp3loser.getPlayer();
					player.close();
					loserSoundRunning = false;
				}

				if (bossSoundRunning) {
					Player player = mp3boss.getPlayer();
					player.close();
					bossSoundRunning = false;
				}

				if (mainSoundRunning) {
					Player player = mp3main.getPlayer();
					player.close();
					mainSoundRunning = false;
				}
				if (winnerSoundRunning) {
					Player player = mp3winner.getPlayer();
					player.close();
					winnerSoundRunning = false;
				}

				if (!menuSoundRunning) {
					mp3 = new Mp3();
					mp3.carregar("menu.mp3");
					mp3.start();
					menuSoundRunning = true;
				}

				resetGame();

				g.drawImage(imageMenu, 0, 0, null);

			} else if (fase == "creditos") {
				g.drawImage(imageCredits, 0, 0, null);
			} else if (fase == "historia") {
				g.drawImage(historia, 0, 0, null);
			} else if (fase == "tutorial") {
				g.drawImage(tuto, 0, 0, null);
			} else if (fase == "pause") {
				josiandro.draw(g);
				g.drawImage(imageGame, 0, 0, null);
				g.setColor(Color.green);
				g.setFont(new Font("Arial", Font.BOLD, 50));
				g.drawString("Pausado", 583, 408);

				List<Tiro> tiros = josiandro.getTiros();

				for (Tiro tiro : tiros) {
					tiro.draw(g);
				}

				for (Worms worm : worms) {
					worm.draw(g);
				}

			} else if (josiandro.getHp() <= 0 || fase == "loser") {
				fase = "loser";
				// josiandro morreu
				if (!loserSoundRunning) {
					mp3loser = new Mp3();
					mp3loser.carregar("gameover.mp3");
					mp3loser.start();
					loserSoundRunning = true;
				}

				if (mainSoundRunning) {
					Player player = mp3main.getPlayer();
					player.close();
					mainSoundRunning = false;
				}

				if (bossSoundRunning) {
					Player player = mp3boss.getPlayer();
					player.close();
					bossSoundRunning = false;
				}

				List<Tiro> tiros = josiandro.getTiros();
				tiros.clear();
				g.drawImage(loose, 0, 0, null);
				worms.clear();
				raios.clear();
			} else if (boss.getLife() <= 0 || fase == "winner") {
				fase = "winner";
				// josiandro morreu

				if (bossSoundRunning) {
					Player player = mp3boss.getPlayer();
					player.close();
					bossSoundRunning = false;
				}

				if (!winnerSoundRunning) {
					mp3winner = new Mp3();
					mp3winner.carregar("win.mp3");
					mp3winner.start();
					winnerSoundRunning = true;
				}

				if (josiandro.getPontos() == 0) {
				g.drawImage(winner, 0, 0, null);
				} else if (josiandro.getPontos() == 1) {
					g.drawImage(winner1, 0, 0, null);
				} else if (josiandro.getPontos() == 2) {
					g.drawImage(winner2, 0, 0, null);
				} else if (josiandro.getPontos() >= 3) {
					g.drawImage(winner3, 0, 0, null);
				}
			} else if (startGame && josiandro.getHp() > 0 && fase == "fase1") {
				if (menuSoundRunning) {
					Player player = mp3.getPlayer();
					player.close();
					menuSoundRunning = false;
				}

				if (loserSoundRunning) {
					Player player = mp3loser.getPlayer();
					player.close();
					loserSoundRunning = false;
				}

				if (!mainSoundRunning) {
					mp3main = new Mp3();
					mp3main.carregar("main.mp3");
					mp3main.start();
					mainSoundRunning = true;
				}

				fase1();
			} else if (startGame && josiandro.getHp() > 0 && fase == "fase2") {
				fase2();
			} else if (startGame && josiandro.getHp() > 0 && fase == "fase3") {
				if (mainSoundRunning) {
					Player player = mp3main.getPlayer();
					player.close();
					mainSoundRunning = false;
				}

				if (!bossSoundRunning) {
					mp3boss = new Mp3();
					mp3boss.carregar("boss.mp3");
					mp3boss.start();
					bossSoundRunning = true;
				}

				fase3();
			}

			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
			g.dispose();

			strategy.show();
		}

	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.gameLoop();
	}

	private class MouseInputHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			if (fase == "menu") {
				startGame = false;
				System.out.println(" x: " + event.getX() + " Y:" + event.getY());
				if (event.getX() >= 614 && event.getX() <= 883 && event.getY() >= 502 && event.getY() <= 575) {
					System.out.println("tutorial");
					fase = "historia";
					startGame = false;
				} else if (event.getX() >= 568 && event.getX() <= 912 && event.getY() >= 634 && event.getY() <= 690) {
					System.out.println("CRÉDITOS");
					startGame = false;
					fase = "creditos";
				}
			} else if (fase == "creditos") {
				System.out.println(" x: " + event.getX() + " Y:" + event.getY());
				if (event.getX() >= 1074 && event.getX() <= 1274 && event.getY() >= 699 && event.getY() <= 733) {
					System.out.println("VOLTAR");
					startGame = false;
					fase = "menu";

				} else if (fase == "tutorial") {
					if (event.getX() >= 1082 && event.getX() <= 1325 && event.getY() >= 699 && event.getY() <= 703) {
						System.out.println("tutorial");

						startGame = true;
						fase = "fase1";
					} else if (fase == "loser") {
						if (event.getX() >= 1082 && event.getX() <= 1325 && event.getY() >= 699
								&& event.getY() <= 703) {

							startGame = true;
							fase = "fase1";
						}

					}
				}
			}
		}

	}

	private class KeyInputHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_P) {
				fase = "pause";
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				josiandro.setWalking(true);
				josiandro.setLeft(false);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				josiandro.setWalking(true);
				josiandro.setLeft(true);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE && !josiandro.isJumping()) {
				josiandro.setJumping(true);
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			} else if (e.getKeyCode() == KeyEvent.VK_C) {
				josiandro.setAtirar(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				startGame = false;
				fase = "menu";
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (fase == "menu") {
					fase = "historia";
					startGame = false;
				} else if (fase == "historia") {
					fase = "tutorial";
					startGame = false;
				} else if (fase == "tutorial") {
					fase = "fase1";
					resetGame();
					startGame = true;
				} else if (fase == "loser") {
					resetGame();
					fase = "fase1";
					startGame = true;
				} else if (fase == "winner") {
					fase = "menu";
					startGame = false;
					resetGame();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && fase == "loser") {
				fase = "menu";
				josiandro.setPosX(0);
				startGame = false;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
				josiandro.setWalking(false);
			}
		}

	}

}
