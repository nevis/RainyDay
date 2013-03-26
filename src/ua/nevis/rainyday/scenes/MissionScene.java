package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
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
	private int currentMissionIndex;
	private Sprite activeMissionSwitchButton;

	@Override
	public void createScene() {
		missionManager = MissionManager.getInstance();
		createBackground();
		createMissionSwitchButtons();
		findLastMissionPage();
		activeMissionSwitchButton.setPosition(missionSwitchButtons[currentMissionIndex].getX(), missionSwitchButtons[currentMissionIndex].getY());
		createMissionButtons(missionSwitchButtons[currentMissionIndex].from, missionSwitchButtons[currentMissionIndex].to);
	}

	private void findLastMissionPage() {
		int lastActiveIndex = 0;
		for (int i = 0; i < missionManager.getMissions().size(); i++) {
			if (!missionManager.getMissions().get(i).isActive()) {
				lastActiveIndex = i - 1;
				break;
			}
		}
		currentMissionIndex = (int) lastActiveIndex / missionCountInScene;
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
		float positionY = 120f;
		int index = 0;
		for (int i = from; i < to; i++) {
			index = i - from;
			if (missionManager.getMissions().get(i).isActive()) {
				missionButtons[index] = new MissionButton(missionManager.getMissions().get(i), 0, 0, resourceManager.missionActiveRegion);
			} else {
				missionButtons[index] = new MissionButton(missionManager.getMissions().get(i), 0, 0, resourceManager.missionDisactiveRegion);
			}
			if (index != 0 && index % missionCountInRow == 0) {
				positionY += missionButtons[index].getHeight() + BASE_OFFSET * 2;
				delta += missionCountInRow;
			}
			positionX = (missionButtons[index].getWidth() + BASE_OFFSET) * (index - delta) + 15f;
			missionButtons[index].setPosition(positionX, positionY);
			background.attachChild(missionButtons[index]);
		}
	}

	private void disposeMissionButtons() {
		for (int i = 0; i < missionButtons.length; i++) {
			missionButtons[i].disposeButton();
		}
		missionButtons = null;
	}

	private void createMissionSwitchButtons() {
		countMissionSwitchButton = (int) (missionManager.getMissions().size() / missionCountInScene);
		if (missionManager.getMissions().size() % missionCountInScene != 0) {
			countMissionSwitchButton += 1;
		}
		missionSwitchButtons = new MissionSwitchButton[countMissionSwitchButton];
		float firstOffset = 0;
		float positionX = 0;
		float positionY = 40;
		for (int i = 0; i < countMissionSwitchButton; i++) {
			missionSwitchButtons[i] = new MissionSwitchButton(0, 0, resourceManager.starGreyRegion);
			if (i == 0) {
				firstOffset = (background.getWidth() - (missionSwitchButtons[i].getWidth() * countMissionSwitchButton + (countMissionSwitchButton - 1) * BASE_OFFSET)) / 2;
			}
			positionX = firstOffset + (missionSwitchButtons[i].getWidth() + 2 * BASE_OFFSET) * i;
			missionSwitchButtons[i].setPosition(positionX, positionY);
			missionSwitchButtons[i].index = i;
			missionSwitchButtons[i].from = i * missionCountInScene;
			if ((missionManager.getMissions().size() - (i + 1) * missionCountInScene) >= 0) {
				missionSwitchButtons[i].to = (i + 1) * missionCountInScene;
			} else {
				missionSwitchButtons[i].to = missionManager.getMissions().size();
			}
			background.attachChild(missionSwitchButtons[i]);
		}
		// ----
		activeMissionSwitchButton = new Sprite(0, 0, resourceManager.starYellowRegion, vboManager) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		background.attachChild(activeMissionSwitchButton);
	}

	private void disposeMissionSwitchButtons() {
		activeMissionSwitchButton.detachSelf();
		activeMissionSwitchButton.dispose();
		activeMissionSwitchButton = null;
		for (int i = 0; i < missionSwitchButtons.length; i++) {
			missionSwitchButtons[i].disposeButton();
		}
		missionSwitchButtons = null;
	}

	public void touchToMissionSwitchButton(int indexButton) {
		if (currentMissionIndex != indexButton) {
			activeMissionSwitchButton.setPosition(missionSwitchButtons[indexButton].getX(), missionSwitchButtons[indexButton].getY());
			currentMissionIndex = indexButton;
			// set mission buttons
			disposeMissionButtons();
			createMissionButtons(missionSwitchButtons[currentMissionIndex].from, missionSwitchButtons[currentMissionIndex].to);
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

	/*
	 * Scene touch
	 */
	private float startTouchX;
	private boolean isSwitchMissions = false;
	private final float SWIPE_OFFSET_X = 30f;

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			actionDown(pSceneTouchEvent);
			break;
		case TouchEvent.ACTION_MOVE:
			actionMove(pSceneTouchEvent);
			break;
		case TouchEvent.ACTION_UP:
			actionUp(pSceneTouchEvent);
			break;
		default:
			break;
		}
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	private void actionDown(TouchEvent pSceneTouchEvent) {
		startTouchX = pSceneTouchEvent.getX();
	}

	private void actionMove(TouchEvent pSceneTouchEvent) {
		if (!isSwitchMissions) {
			if (pSceneTouchEvent.getX() - startTouchX > SWIPE_OFFSET_X) { // left
				isSwitchMissions = true;
				swipeMissions(-1);
			} else if (startTouchX - pSceneTouchEvent.getX() > SWIPE_OFFSET_X) { // right
				isSwitchMissions = true;
				swipeMissions(1);
			}
		}
	}

	private void swipeMissions(int direction) {
		if (direction < 0 && currentMissionIndex > 0) { // left
			touchToMissionSwitchButton(currentMissionIndex - 1);
		} else if (direction > 0 && currentMissionIndex < countMissionSwitchButton - 1) { // right
			touchToMissionSwitchButton(currentMissionIndex + 1);
		}
	}

	private void actionUp(TouchEvent pSceneTouchEvent) {
		if (isSwitchMissions) {
			isSwitchMissions = false;
		} else {
			float x = pSceneTouchEvent.getX();
			float y = pSceneTouchEvent.getY();
			Sprite button = null;
			for (int i = 0; i < background.getChildCount(); i++) {
				Sprite child = (Sprite) background.getChildByIndex(i);
				if (x > child.getX() && x < (child.getX() + child.getWidth())) {
					if (y > child.getY() && y < (child.getY() + child.getHeight())) {
						button = child;
						break;
					}
				}
			}
			if (button != null) {
				if (button instanceof MissionButton) {
					((MissionButton) button).action();
				} else if (button instanceof MissionSwitchButton) {
					((MissionSwitchButton) button).action();
				}
			}
		}
	}
}
