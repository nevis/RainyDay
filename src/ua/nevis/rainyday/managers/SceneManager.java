package ua.nevis.rainyday.managers;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import ua.nevis.rainyday.scenes.BaseScene;
import ua.nevis.rainyday.scenes.GameScene;
import ua.nevis.rainyday.scenes.MainMenuScene;
import ua.nevis.rainyday.scenes.MissionScene;
import ua.nevis.rainyday.scenes.SceneType;
import ua.nevis.rainyday.scenes.SplashScene;

public class SceneManager {
	private static SceneManager INSTANCE = new SceneManager();
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	private Engine engine = ResourceManager.getInstance().engine;
	/*
	 * Scenes
	 */
	private BaseScene splashScene;
	private BaseScene mainMenuScene;
	private BaseScene missionScene;
	private BaseScene gameScene;

	private SceneManager() {

	}

	public static SceneManager getInstance() {
		return INSTANCE;
	}

	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}

	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_SPLASH:
			setScene(splashScene);
			break;
		case SCENE_MENU:
			setScene(mainMenuScene);
			break;
		case SCENE_MISSION:
			setScene(missionScene);
			break;
		case SCENE_GAME:
			setScene(gameScene);
			break;
		default:
			break;
		}
	}

	public BaseScene getCurrentScene() {
		return currentScene;
	}

	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}

	/*
	 * Splash scene
	 */
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourceManager.getInstance().loadSplashResource();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}

	public void disposeSplashScene() {
		ResourceManager.getInstance().unloadSplashResource();
		splashScene.disposeScene();
		splashScene = null;
	}

	/*
	 * Menu scene
	 */
	public void createMainMenuScene() {
		ResourceManager.getInstance().loadMainMenuResource();
		mainMenuScene = new MainMenuScene();
		setScene(mainMenuScene);
	}

	public void disposeMainMenuScene() {
		ResourceManager.getInstance().unloadMainMenuResource();
		mainMenuScene.disposeScene();
		mainMenuScene = null;
	}

	/*
	 * Mission scene
	 */
	public void createMissionScene() {
		ResourceManager.getInstance().loadMissionResource();
		missionScene = new MissionScene();
		setScene(missionScene);
	}

	public void disposeMissionScene() {
		ResourceManager.getInstance().unloadMissionResource();
		missionScene.disposeScene();
		missionScene = null;
	}

	/*
	 * Game scene
	 */
	public void createGameScene() {
		ResourceManager.getInstance().loadGameResource();
		gameScene = new GameScene();
		setScene(gameScene);
	}

	public void disposeGameScene() {
		ResourceManager.getInstance().unloadGameResource();
		gameScene.disposeScene();
		gameScene = null;
	}
}
