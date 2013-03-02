package ua.nevis.rainyday.managers;

public class SceneManager {
	private static SceneManager INSTANCE = new SceneManager();

	private SceneManager() {

	}

	public static SceneManager getInstance() {
		return INSTANCE;
	}
}
