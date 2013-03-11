package ua.nevis.rainyday;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import ua.nevis.rainyday.managers.ResourceManager;
import ua.nevis.rainyday.managers.SceneManager;

public class RainyDayActivity extends BaseGameActivity {
	public static final int DEFAULT_SCREEN_WIDTH = 800;
	public static final int DEFAULT_SCREEN_HEIGHT = 480;
	private final int FPS = 60;
	private Camera camera;
	private SceneManager sceneManager;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, FPS);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT), this.camera);
		// add music and sounds if need on this place
		// options.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		options.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return options;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		ResourceManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		sceneManager = SceneManager.getInstance();
		sceneManager.createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				sceneManager.createMainMenuScene();
				sceneManager.disposeSplashScene();
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onBackPressed() {
		sceneManager.getCurrentScene().onBackKeyPressed();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_MENU == keyCode) {
			sceneManager.getCurrentScene().onMenuKeyPressed();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
