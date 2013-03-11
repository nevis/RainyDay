package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import ua.nevis.rainyday.managers.SceneManager;

public class MissionScene extends BaseScene {
	private Sprite background;

	@Override
	public void createScene() {
		createBackground();
	}

	private void createBackground() {
		background = new Sprite(0, 0, resourceManager.backgroundMissionRegion, vboManager) {
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
		return SceneType.SCENE_MISSION;
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().createMainMenuScene();
		SceneManager.getInstance().disposeMissionScene();
	}

	@Override
	public void onMenuKeyPressed() {
		SceneManager.getInstance().createMainMenuScene();
		SceneManager.getInstance().disposeMissionScene();
	}
}
