package ua.nevis.rainyday.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import ua.nevis.rainyday.data.Mission;
import ua.nevis.rainyday.managers.MissionManager;
import ua.nevis.rainyday.managers.ResourceManager;
import ua.nevis.rainyday.managers.SceneManager;

public class MissionButton extends Sprite {
	private final String TXT_MISSION = "mission";
	private final String TXT_SCORE = "score";
	private final String TXT_LOCKED = "locked";
	private Mission mission;
	private ResourceManager resourceManager;
	private Text textMission;
	private Text valueMission;
	private Text textScore;
	private Text valueScore;
	private Text textLocked;
	private Sprite[] stars = new Sprite[3];

	public MissionButton(Mission mission, float x, float y, ITextureRegion textureRegion) {
		super(x, y, textureRegion, ResourceManager.getInstance().vboManager);
		this.mission = mission;
		this.resourceManager = ResourceManager.getInstance();
		setMissionInfo();
	}

	private void setMissionInfo() {
		if (mission.isActive()) {
			// mission text
			textMission = new Text(0, 0, resourceManager.paintdrpFont, TXT_MISSION, resourceManager.vboManager);
			textMission.setPosition((getWidth() - textMission.getWidth()) / 2, 10f);
			textMission.setColor(resourceManager.COLOR_BLUE);
			attachChild(textMission);
			// mission number
			valueMission = new Text(0, 0, resourceManager.paintdrpFont, mission.getMissionName(), resourceManager.vboManager);
			valueMission.setPosition((getWidth() - valueMission.getWidth()) / 2, textMission.getY() + textMission.getHeight() + 8f);
			valueMission.setColor(resourceManager.COLOR_BLUE);
			attachChild(valueMission);
			// score text
			textScore = new Text(0, 0, resourceManager.paintdrpFont, TXT_SCORE, resourceManager.vboManager);
			textScore.setPosition((getWidth() - textScore.getWidth()) / 2, valueMission.getY() + valueMission.getHeight() + 8f);
			textScore.setColor(resourceManager.COLOR_BLUE);
			attachChild(textScore);
			// score value
			valueScore = new Text(0, 0, resourceManager.paintdrpFont, Integer.toString(mission.getScoreValue()), resourceManager.vboManager);
			valueScore.setPosition((getWidth() - valueScore.getWidth()) / 2, textScore.getY() + textScore.getHeight() + 8f);
			valueScore.setColor(resourceManager.COLOR_BLUE);
			attachChild(valueScore);
			setStar();
		} else {
			textLocked = new Text(0, 0, resourceManager.paintdrpFont, TXT_LOCKED, resourceManager.vboManager);
			textLocked.setPosition((getWidth() - textLocked.getWidth()) / 2, (getHeight() - textLocked.getHeight()) / 2);
			textLocked.setColor(resourceManager.COLOR_GREY);
			attachChild(textLocked);
		}

	}

	private void setStar() {
		int starCount = mission.getStarCount();
		float positionX;
		float positionY = getHeight() - 40f;
		for (int i = 0; i < stars.length; i++) {
			if (starCount == 0) {
				stars[i] = new Sprite(0, 0, resourceManager.starGreyRegion, resourceManager.vboManager);
			} else {
				stars[i] = new Sprite(0, 0, resourceManager.starYellowRegion, resourceManager.vboManager);
				starCount -= 1;
			}
			positionX = 23.5f + (stars[i].getWidth() + 5f) * i;
			stars[i].setPosition(positionX, positionY);
			attachChild(stars[i]);
		}
	}

	@Override
	protected void preDraw(GLState pGLState, org.andengine.engine.camera.Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionUp() && mission.isActive()) {
			MissionManager.getInstance().setCurrentMission(mission);
			SceneManager.getInstance().createGameScene();
			SceneManager.getInstance().disposeMissionScene();
		}
		return true;
	}

	public void disposeButton() {
		disposeMissionInfo();
		this.detachSelf();
		this.dispose();
	}

	private void disposeMissionInfo() {
		if (mission.isActive()) {
			textMission.detachSelf();
			textMission.dispose();
			textMission = null;
			valueMission.detachSelf();
			valueMission.dispose();
			valueMission = null;
			textScore.detachSelf();
			textScore.dispose();
			textScore = null;
			valueScore.detachSelf();
			valueScore.dispose();
			valueScore = null;
			disposeStar();
		} else {
			textLocked.detachSelf();
			textLocked.dispose();
			textLocked = null;
		}
	}

	private void disposeStar() {
		for (int i = 0; i < stars.length; i++) {
			stars[i].detachSelf();
			stars[i].dispose();
			stars[i] = null;
		}
		stars = null;
	}
}
