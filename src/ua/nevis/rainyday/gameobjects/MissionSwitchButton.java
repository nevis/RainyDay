package ua.nevis.rainyday.gameobjects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import ua.nevis.rainyday.managers.ResourceManager;
import ua.nevis.rainyday.managers.SceneManager;
import ua.nevis.rainyday.scenes.MissionScene;

public class MissionSwitchButton extends Sprite {
	public int index = 0;
	public int from = 0;
	public int to = 0;

	public MissionSwitchButton(float x, float y, ITextureRegion textureRegion) {
		super(x, y, textureRegion, ResourceManager.getInstance().vboManager);
	}

	@Override
	protected void preDraw(GLState pGLState, org.andengine.engine.camera.Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}
	
	public void action() {
		((MissionScene) SceneManager.getInstance().getCurrentScene()).touchToMissionSwitchButton(index);
	}

	public void disposeButton() {
		this.detachSelf();
		this.dispose();
	}

}
