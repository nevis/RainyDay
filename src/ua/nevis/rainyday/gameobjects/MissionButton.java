package ua.nevis.rainyday.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;

import ua.nevis.rainyday.data.Mission;
import ua.nevis.rainyday.managers.ResourceManager;

public class MissionButton extends Sprite {
	private Mission mission;

	public MissionButton(Mission mission, float x, float y, ITextureRegion textureRegion) {
		super(x, y, textureRegion, ResourceManager.getInstance().vboManager);
		this.mission = mission;
		setMissionInfo();
	}

	private void setMissionInfo() {
		
	}

	@Override
	protected void preDraw(GLState pGLState, org.andengine.engine.camera.Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			if (mission.isActive()) {
				// start mission
			}
		}
		return true;
	}
	
	public void disposeButton () {
		this.detachSelf();
		this.dispose();
	}
}
