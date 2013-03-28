package ua.nevis.rainyday.gameobjects;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import android.util.Log;

import ua.nevis.rainyday.managers.ResourceManager;
import ua.nevis.rainyday.managers.SceneManager;
import ua.nevis.rainyday.scenes.GameScene;

public class GameMenu extends MenuScene {
	public final int MENU_PLAY = 1;
	public final int MENU_RESTART = 2;
	public final int MENU_BACK = 3;
	private final int ACTIVE_BTN_COUNT = 3;
	private ResourceManager resourceManager;
	private SceneManager sceneManager;
	private Sprite menuRect;
	private final IMenuItem playMenuItem;
	private final IMenuItem restartMenuItem;
	private final IMenuItem backMenuItem;
	private final String PAUSE = "Pause";
	private Text pauseText;
	private final String MISSION_COMPLETE = "Mission complete";
	private Text missionCompleteText;

	public GameMenu() {
		super(ResourceManager.getInstance().camera);
		setBackgroundEnabled(false);
		setOnMenuItemClickListener(new GameMenuItemListener());
		resourceManager = ResourceManager.getInstance();
		sceneManager = SceneManager.getInstance();
		// create rect
		menuRect = new Sprite(0, 0, resourceManager.gameMenuRectRegion, resourceManager.vboManager) {
			@Override
			protected void preDraw(GLState pGLState, org.andengine.engine.camera.Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		menuRect.setPosition((resourceManager.camera.getWidth() - menuRect.getWidth()) / 2, (resourceManager.camera.getHeight() - menuRect.getHeight()) / 2);
		attachChild(menuRect);
		// create menu buttons
		float deltaX = (menuRect.getWidth() - resourceManager.gameMenuPlayBtnRegion.getWidth() * ACTIVE_BTN_COUNT) / (ACTIVE_BTN_COUNT + 1);
		float btnPositionY = menuRect.getY() + menuRect.getHeight() - resourceManager.gameMenuPlayBtnRegion.getHeight() - deltaX;
		
		backMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_BACK, resourceManager.gameMenuBackBtnRegion, resourceManager.vboManager), 1.1f, 1.0f);
		backMenuItem.setPosition(menuRect.getX() + resourceManager.gameMenuPlayBtnRegion.getWidth() * 0 + deltaX * 1, btnPositionY);
		
		restartMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RESTART, resourceManager.gameMenuRestartBtnRegion, resourceManager.vboManager), 1.1f, 1.0f);
		restartMenuItem.setPosition(menuRect.getX() + resourceManager.gameMenuPlayBtnRegion.getWidth() * 1 + deltaX * 2, btnPositionY);
		
		playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourceManager.gameMenuPlayBtnRegion, resourceManager.vboManager), 1.1f, 1.0f);
		playMenuItem.setPosition(menuRect.getX() + resourceManager.gameMenuPlayBtnRegion.getWidth() * 2 + deltaX * 3, btnPositionY);
		
		// info
		pauseText = new Text(0, 0, resourceManager.gamePaintdrpFont, PAUSE, resourceManager.vboManager);
		pauseText.setColor(resourceManager.COLOR_BLUE);
		pauseText.setPosition((menuRect.getWidth() - pauseText.getWidth()) / 2, 75f);
		
		missionCompleteText = new Text(0, 0, resourceManager.gamePaintdrpFont, MISSION_COMPLETE, resourceManager.vboManager);
		missionCompleteText.setColor(resourceManager.COLOR_BLUE);
		missionCompleteText.setPosition((menuRect.getWidth() - missionCompleteText.getWidth()) / 2, 15f);
	}

	public void showPauseMenu () {
		// info
		detachAllOnMenuRect();
		menuRect.attachChild(pauseText);
		// buttons
		clearMenuItems();
		addMenuItem(backMenuItem);
		addMenuItem(restartMenuItem);
		addMenuItem(playMenuItem);
	}
	
	public void showEndMissionMenu () {
		//info
		detachAllOnMenuRect();
		menuRect.attachChild(missionCompleteText);
		// buttons
		clearMenuItems();
		addMenuItem(backMenuItem);
		addMenuItem(restartMenuItem);
	}

	public void disposeMenu() {
		pauseText.detachSelf();
		pauseText.dispose();
		pauseText = null;
		missionCompleteText.detachSelf();
		missionCompleteText.dispose();
		missionCompleteText = null;
		
		menuRect.detachSelf();
		menuRect.dispose();
		menuRect = null;
		this.detachSelf();
		this.dispose();
	}
	
	private void detachAllOnMenuRect() {
		for (int i = 0; i < menuRect.getChildCount(); i++) {
			menuRect.getChildByIndex(i).detachSelf();
		}
	}

	private class GameMenuItemListener implements IOnMenuItemClickListener {

		@Override
		public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
			switch (pMenuItem.getID()) {
			case MENU_PLAY:
				((GameScene) sceneManager.getCurrentScene()).playButtonAction();
				break;
			case MENU_RESTART:
				Log.d("debug", "restart");
				break;
			case MENU_BACK:
				((GameScene) sceneManager.getCurrentScene()).backButtonAction();
				break;
			default:
				break;
			}
			return false;
		}

	}
}
