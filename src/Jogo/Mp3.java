package Jogo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Mp3 extends Thread {
	private File mp3;
	private File menu;
	private Player player;

	public void carregar(String caminho) {
		mp3 = new File(caminho);
	}

	public File getMp3() {
		return mp3;
	}

	public File getMenu() {
		return menu;
	}

	public void setMenu(File menu) {
		this.menu = menu;
	}

	public void setMp3(File mp3) {
		this.mp3 = mp3;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void run() {
		try {
			FileInputStream fis = new FileInputStream(mp3);
			BufferedInputStream bis = new BufferedInputStream(fis);

			this.player = new Player(bis);
			this.player.play();
		} catch (Exception e) {
			System.out.println("Problemas ao tocar a música: " + mp3.getName());
			e.printStackTrace();
		}
	}

}
