package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import ua.nevis.rainyday.gameobjects.MissionButton;
import ua.nevis.rainyday.gameobjects.MissionSwitchButton;
import ua.nevis.rainyday.managers.MissionManager;
import ua.nevis.rainyday.managers.SceneManager;

public class MissionScene extends BaseScene {
	private final int missionCountInScene = 10;
	private final int missionCountInRow = 5;
	private final float BASE_OFFSET = 5f;
	private MissionManager missionManager;
	private Sprite background;
	private MissionButton[] missionButtons;
	private int countMissionSwitchButton;
	private MissionSwitchButton[] missionSwitchButtons;
	private int currentActiveMissionSwitchButton;

	@Override
	public void createScene() {
		missionManager = MissionManager.getInstance();
		createBackground();
		createMissionSwitchButtons();
		currentActiveMissionSwitchButton = 0;
		createMissionButtons(missionSwitchButtons[currentActiveMissionSwitchButton].from, missionSwitchButtons[currentActiveMissionSwitchButton].to);
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

	private void createMissionButtons(int from, int to) {
		missionButtons = new MissionButton[to - from];
		int delta = 0;
		float positionX = 0f;
		float positionY = 140f;
		int index = 0;
		for (int i = from; i < to; i++) {
			index = i - from;
			if (missionManager.getMissions().get(i).isActive()) {
				missionButtons[index] = new MissionButton(missionManager.getMissions().get(i), 0, 0, resourceManager.missionActiveRegion);
			} else {
				missionButtons[index] = new MissionButton(missionManager.getMissions().get(i), 0, 0, resourceManager.missionDisactiveRegion);
			}
			if (index != 0 && index % missionCountInRow == 0) {
				positionY += missionButtons[index].getHeight() + BASE_OFFSET;
				delta += missionCountInRow;
			}
			positionX = (missionButtons[index].getWidth() + BASE_OFFSET) * (index - delta) + 15f;
			missionButtons[index].setPosition(positionX, positionY);
			attachChild(missionButtons[index]);
			registerTouchArea(missionButtons[index]);
		}
	}

	private void disposeMissionButtons() {
		for (int i = 0; i < missionButtons.length; i++) {
			unregisterTouchArea(missionButtons[i]);
			missionButtons[i].disposeButton();
		}
		missionButtons = null;
	}

	private void createMissionSwitchButtons() {
		int countLastMissions = 0;
		countMissionSwitchButton = (int) (missionManager.getMissions().size() / missionCountInScene);
		if (missionManager.getMissions().size() % missionCountInScene != 0) {
			countMissionSwitchButton += 1;
		}
		countLastMissions = (int) (missionManager.getMissions().size() % missionCountInScene);
		missionSwitchButtons = new MissionSwitchButton[countMissionSwitchButton];
		float firstOffset = 0;
		float positionX = 0;
		float positionY = 80;
		for (int i = 0; i < countMissionSwitchButton; i++) {
			if (i == 0) {
				missionSwitchButtons[i] = new MissionSwitchButton(0, 0, resourceManager.starYellowRegion);
				firstOffset = (background.getWidth() - (missionSwitchButtons[i].getWidth() * countMissionSwitchButton + (countMissionSwitchButton - 1) * BASE_OFFSET)) / 2;
			} else {
				missionSwitchButtons[i] = new MissionSwitchButton(0, 0, resourceManager.starGreyRegion);
			}
			positionX = firstOffset + (missionSwitchButtons[i].getWidth() + BASE_OFFSET) * i;
			missionSwitchButtons[i].setPosition(positionX, positionY);
			missionSwitchButtons[i].index = i;
			if (i + 1 == countMissionSwitchButton) {
				missionSwitchButtons[i].from = i * 10;
				missionSwitchButtons[i].to = i * 10 + countLastMissions;
			} else {
				missionSwitchButtons[i].from = i * 10;
				missionSwitchButtons[i].to = (i + 1) * 10;
			}
			attachChild(missionSwitchButtons[i]);
			registerTouchArea(missionSwitchButtons[i]);
		}
	}

	private void disposeMissionSwitchButtons() {
		for (int i = 0; i < missionSwitchButtons.length; i++) {
			unregisterTouchArea(missionSwitchButtons[i]);
			missionSwitchButtons[i].disposeButton();
		}
		missionSwitchButtons = null;
	}

	public void touchToMissionSwitchButton(int indexButton) {
		if (currentActiveMissionSwitchButton != indexButton) {
			// current active switch button to inactive
			MissionSwitchButton inactiveButton = new MissionSwitchButton(0, 0, resourceManager.starGreyRegion);
			inactiveButton.setPosition(missionSwitchButtons[currentActiveMissionSwitchButton].getX(), missionSwitchButtons[currentActiveMissionSwitchButton].getY());
			inactiveButton.index = missionSwitchButtons[currentActiveMissionSwitchButton].index;
			inactiveButton.from = missionSwitchButtons[currentActiveMissionSwitchButton].from;
			inactiveButton.to = missionSwitchButtons[currentActiveMissionSwitchButton].to;
			unregisterTouchArea(missionSwitchButtons[currentActiveMissionSwitchButton]);
			missionSwitchButtons[currentActiveMissionSwitchButton].disposeButton();
			missionSwitchButtons[currentActiveMissionSwitchButton] = inactiveButton;
			attachChild(missionSwitchButtons[currentActiveMissionSwitchButton]);
			registerTouchArea(missionSwitchButtons[currentActiveMissionSwitchButton]);
			// inactive button set to current active switch
			currentActiveMissionSwitchButton = indexButton;
			MissionSwitchButton activeButton = new MissionSwitchButton(0, 0, resourceManager.starYellowRegion);
			activeButton.setPosition(missionSwitchButtons[currentActiveMissionSwitchButton].getX(), missionSwitchButtons[currentActiveMissionSwitchButton].getY());
			activeButton.index = currentActiveMissionSwitchButton;
			activeButton.from = missionSwitchButtons[currentActiveMissionSwitchButton].from;
			activeButton.to = missionSwitchButtons[currentActiveMissionSwitchButton].to;
			unregisterTouchArea(missionSwitchButtons[currentActiveMissionSwitchButton]);
			missionSwitchButtons[currentActiveMissionSwitchButton].disposeButton();
			missionSwitchButtons[currentActiveMissionSwitchButton] = activeButton;
			attachChild(missionSwitchButtons[currentActiveMissionSwitchButton]);
			registerTouchArea(missionSwitchButtons[currentActiveMissionSwitchButton]);
			// set mission buttons
			disposeMissionButtons();
			createMissionButtons(missionSwitchButtons[currentActiveMissionSwitchButton].from, missionSwitchButtons[currentActiveMissionSwitchButton].to);
		}
	}

	@Override
	public void disposeScene() {
		disposeMissionSwitchButtons();
		disposeMissionButtons();
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
