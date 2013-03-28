package ua.nevis.rainyday.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import ua.nevis.rainyday.managers.SceneManager;

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
		background = new Sprite(0, 0, resourceManager.backgroundMenuRegion, vboManager) {
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
		menuScene.setPosition(0, 0);
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PLAY_BTN, resourceManager.playBtnRegion, vboManager), 1.1f, 1.0f);
		final IMenuItem exitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(EXIT_BTN, resourceManager.exitRtnRegion, vboManager), 1.1f, 1.0f);
		menuScene.addMenuItem(playMenuItem);
		menuScene.addMenuItem(exitMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(false);
		exitMenuItem.setPosition(exitMenuItem.getX(), exitMenuItem.getY() + 10);
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
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case PLAY_BTN:
			SceneManager.getInstance().createMissionScene();
			SceneManager.getInstance().disposeMainMenuScene();
			return true;
		case EXIT_BTN:
			SceneManager.getInstance().disposeMainMenuScene();
			System.exit(0);
			return true;
		default:
			return false;
		}
	}

}
