package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import ua.nevis.rainyday.managers.MissionManager;
import ua.nevis.rainyday.managers.SceneManager;

public class GameScene extends BaseScene {
	private MissionManager missionManager;
	private Sprite background;

	@Override
	public void createScene() {
		missionManager = MissionManager.getInstance();
		background = new Sprite(0, 0, resourceManager.backgroundGameRegion, vboManager) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(background);
	}

	@Override
	public void disposeScene() {
		background.detachSelf();
		background.dispose();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().createMainMenuScene();
		SceneManager.getInstance().disposeGameScene();
	}

	@Override
	public void onMenuKeyPressed() {
		missionManager.saveMission();
	}

}
