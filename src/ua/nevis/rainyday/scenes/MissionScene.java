package ua.nevis.rainyday.scenes;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import ua.nevis.rainyday.gameobjects.MissionButton;
import ua.nevis.rainyday.managers.MissionManager;
import ua.nevis.rainyday.managers.SceneManager;

public class MissionScene extends BaseScene {
	private MissionManager missionManager;
	private Sprite background;
	private List<MissionButton> missionButtons;
	private final int missionCountInScene = 10;
	private final int missionCountInRow = 5; 

	@Override
	public void createScene() {
		missionManager = MissionManager.getInstance();
		missionButtons = new ArrayList<MissionButton>();
		createBackground();
		createMissionButtons();
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
	
	private void createMissionButtons() {
		int delta = 0;
		int currentMissionCount = missionManager.getMissions().size() >= missionCountInScene ? missionCountInScene : missionManager.getMissions().size();
		float positionX = 0f;
		float positionY = 140f;
		for (int i = 0; i < currentMissionCount; i++) {
			if (missionManager.getMissions().get(i).isActive()) {
				missionButtons.add(new MissionButton(missionManager.getMissions().get(i), 0, 0, resourceManager.missionActiveRegion));
			} else {
				missionButtons.add(new MissionButton(missionManager.getMissions().get(i), 0, 0, resourceManager.missionDisactiveRegion));
			}
			if (i != 0 && i % missionCountInRow == 0) {
				positionY += missionButtons.get(i).getHeight() + 5f;
				delta += missionCountInRow;
			}
			positionX = (missionButtons.get(i).getWidth() + 5f) * (i - delta) + 15f;
			missionButtons.get(i).setPosition(positionX, positionY);
			attachChild(missionButtons.get(i));
			registerTouchArea(missionButtons.get(i));
		}
	}

	@Override
	public void disposeScene() {
		for (MissionButton missionButton : missionButtons) {
			unregisterTouchArea(missionButton);
			missionButton.disposeButton();
		}
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
