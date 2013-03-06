package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import ua.nevis.rainyday.RainyDayActivity;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {
	private final int PLAY_BTN = 1;
	private final int EXIT_BTN = 2;
	private Sprite background;
	private MenuScene menuScene;
	@Override
	public void createScene() {
		createBackground();
		createMenu();
	}
	
	private void createBackground() {
		background = new Sprite(0, 0, resourceManager.backgroundRegion, vboManager) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(background);
	}
	
	private void createMenu() {
		menuScene = new MenuScene(camera);
		menuScene.setPosition(RainyDayActivity.DEFAULT_SCREEN_WIDTH / 2, RainyDayActivity.DEFAULT_SCREEN_HEIGHT / 2);
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PLAY_BTN, resourceManager.playBtnRegion, vboManager), 1.1f, 1.0f);
		final IMenuItem exitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(EXIT_BTN, resourceManager.exitRtnRegion, vboManager), 1.1f, 1.0f);
		menuScene.addMenuItem(playMenuItem);
		menuScene.addMenuItem(exitMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(false);
		playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 10);
		exitMenuItem.setPosition(exitMenuItem.getX(), exitMenuItem.getY() - 60);
		menuScene.setOnMenuItemClickListener(this);
		setChildScene(menuScene);
	}

	@Override
	public void disposeScene() {
		menuScene.detachSelf();
		menuScene.dispose();
		background.detachSelf();
		background.dispose();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onMenuKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case PLAY_BTN:
			return true;
		case EXIT_BTN:
			return true;
		default:
			return false;
		}
	}

}