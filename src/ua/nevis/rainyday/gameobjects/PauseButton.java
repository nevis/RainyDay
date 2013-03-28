package ua.nevis.rainyday.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import ua.nevis.rainyday.managers.ResourceManager;
import ua.nevis.rainyday.managers.SceneManager;
import ua.nevis.rainyday.scenes.GameScene;

public class PauseButton extends Sprite {

	public PauseButton() {
		super(0, 0, ResourceManager.getInstance().pauseBtnRegion, ResourceManager.getInstance().vboManager);

	}

	@Override
	protected void preDraw(GLState pGLState, org.andengine.engine.camera.Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			((GameScene) SceneManager.getInstance().getCurrentScene()).pauseButtonAction();
		}
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}

	public void disposeButton() {
		this.detachSelf();
		this.dispose();
	}
}
