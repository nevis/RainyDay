package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import ua.nevis.rainyday.gameobjects.GameMenu;
import ua.nevis.rainyday.gameobjects.PauseButton;
import ua.nevis.rainyday.managers.MissionManager;
import ua.nevis.rainyday.managers.SceneManager;

public class GameScene extends BaseScene {
	private MissionManager missionManager;
	private HUD hud;
	private PauseButton pauseButton;
	private final String SCORE = "Score:";
	private Text scoreText;
	private AutoParallaxBackground background;
	private GameMenu gameMenu;

	@Override
	public void createScene() {
		missionManager = MissionManager.getInstance();
		createBackground();
		createHud();
		createGameMenu();
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
		// score
		scoreText = new Text(10, 27, resourceManager.gamePaintdrpFont, SCORE + missionManager.getCurrentScore(), "Score:0123456789".length(), vboManager);
		scoreText.setColor(resourceManager.COLOR_BLUE);
		hud.attachChild(scoreText);
		// pause button
		pauseButton = new PauseButton();
		pauseButton.setPosition(camera.getWidth() - pauseButton.getWidth() - 10, 10);
		hud.attachChild(pauseButton);
		hud.registerTouchArea(pauseButton);
		// ----
		camera.setHUD(hud);
	}

	private void disposeHud() {
		// score text
		scoreText.detachSelf();
		scoreText.dispose();
		scoreText = null;
		// pause button
		hud.unregisterTouchArea(pauseButton);
		pauseButton.disposeButton();
		pauseButton = null;
		// ----
		hud.detachSelf();
		hud.dispose();
		hud = null;
	}

	private void createGameMenu() {
		gameMenu = new GameMenu();
	}

	private void disposeGameMenu() {
		clearChildScene();
		gameMenu.disposeMenu();
	}

	@Override
	public void disposeScene() {
		disposeGameMenu();
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
		if (!hasChildScene()) {
			pauseButton.setVisible(false);
			hud.unregisterTouchArea(pauseButton);
			gameMenu.showEndMissionMenu();
			setChildScene(gameMenu, false, true, true);
		}
	}

	// =============
	// LOGIC
	// =============
	public void pauseButtonAction() {
		if (!hasChildScene()) {
			pauseButton.setVisible(false);
			hud.unregisterTouchArea(pauseButton);
			gameMenu.showPauseMenu();
			setChildScene(gameMenu, false, true, true);
		}
	}

	public void playButtonAction() {
		if (hasChildScene()) {
			pauseButton.setVisible(true);
			hud.registerTouchArea(pauseButton);
			clearChildScene();
		}
	}
	
	public void backButtonAction() {
		SceneManager.getInstance().createMissionScene();
		SceneManager.getInstance().disposeGameScene();
	}

	public void setScore(int score) {
		missionManager.plusToCurrentScore(score);
		scoreText.setText(SCORE + missionManager.getCurrentScore());
	}

}
