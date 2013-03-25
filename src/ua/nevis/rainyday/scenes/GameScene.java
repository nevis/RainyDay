package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;

import ua.nevis.rainyday.gameobjects.PauseButton;
import ua.nevis.rainyday.managers.MissionManager;
import ua.nevis.rainyday.managers.SceneManager;

public class GameScene extends BaseScene {
	private MissionManager missionManager;
	private HUD hud;
	private PauseButton pauseButton;
	private AutoParallaxBackground background;

	@Override
	public void createScene() {
		missionManager = MissionManager.getInstance();
		createBackground();
		createHud();
	}
	
	private void createBackground() {
		background = new AutoParallaxBackground(0, 0, 0, 5);
		background.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, 0, resourceManager.backgroundGameRegion, vboManager)));
		background.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 0, resourceManager.skyGameRegion, vboManager)));
		setBackground(background);
	}
	
	private void disposeBackground() {
		background = null;
	}
	
	private void createHud() {
		hud = new HUD();
		// pause button
		pauseButton = new PauseButton();
		pauseButton.setPosition(camera.getWidth() - pauseButton.getWidth() - 10, 10);
		hud.attachChild(pauseButton);
		hud.registerTouchArea(pauseButton);
		// ----
		camera.setHUD(hud);
	}
	
	private void disposeHud() {
		// pause button
		hud.unregisterTouchArea(pauseButton);
		pauseButton.disposeButton();
		// ----
		hud.detachSelf();
		hud.dispose();
		hud = null;
	}

	@Override
	public void disposeScene() {
		disposeHud();
		disposeBackground();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void onBackKeyPressed() {
		resumeGame();
		SceneManager.getInstance().createMainMenuScene();
		SceneManager.getInstance().disposeGameScene();
	}

	@Override
	public void onMenuKeyPressed() {
		resumeGame();
		missionManager.saveMission();
	}
	
	public void pauseGame() {
		if (engine.isRunning()) {
			engine.stop();
			pauseButton.setVisible(false);
		}
	}
	
	public void resumeGame() {
		if (!engine.isRunning()) {
			engine.start();
			pauseButton.setVisible(true);
		}
	}

}
