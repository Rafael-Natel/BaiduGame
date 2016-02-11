package Jogo;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Utils {
	public static final int PERSONAGEM_WIDTH = 110;
	public static final int PERSONAGEM_HEIGHT = 117;
	public static final int JOSIANDRO_STOPPED_WIDTH = 110;
	public static final int JOSIANDRO_STOPPED_HEIGHT = 117;
	public static final int JOSIANDRO_WALKING_WIDTH = 110;
	public static final int JOSIANDRO_WALKING_HEIGHT = 117;
	public static final int WORM_WIDTH = 184;
	public static final int WORM_HEIGHT = 117;
	public static final int WORM_STOPPED_WIDTH = 184;
	public static final int WORM_STOPPED_HEIGHT = 117;
	public static final int WORM_WALKING_WIDTH = 184;
	public static final int WORM_WALKING_HEIGHT = 117;
	public static final int TIRO_WIDTH = 42;
	public static final int TIRO_HEIGHT = 32;
	public static final int BOSS_WIDTH = 153;
	public static final int BOSS_HEIGHT = 298;
	public static final int RAIO_WIDTH = 32;
	public static final int RAIO_HEIGHT = 32;
	public static final int BAIDU_WIDTH = 42;
	public static final int BAIDU_HEIGHT = 42;

	private static Utils instance;

	public int width = 800;
	public int height = 600;

	public static Utils getInstance() {
		if (instance == null) {
			instance = new Utils();
		}
		return instance;
	}

	public Image loadImage(String fileName) {
		URL imgUrl = getClass().getClassLoader().getResource(fileName);
		if (imgUrl == null) {
			System.out.println("Erro ao carregar a imagem: " + fileName);
			System.exit(1);
		} else {
			try {
				return ImageIO.read(imgUrl);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return null;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

}
