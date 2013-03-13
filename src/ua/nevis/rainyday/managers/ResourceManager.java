package ua.nevis.rainyday.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import ua.nevis.rainyday.RainyDayActivity;

public class ResourceManager {
	private static ResourceManager INSTANCE = new ResourceManager();
	private final String GRAPHICS_PATH = "gfx/";
	private final String FONT_PATH = "font/";
	public Engine engine;
	public RainyDayActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vboManager;

	private ResourceManager() {

	}

	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	public static void prepareManager(Engine engine, RainyDayActivity activity, Camera camera, VertexBufferObjectManager vboManager) {
		INSTANCE.engine = engine;
		INSTANCE.activity = activity;
		INSTANCE.camera = camera;
		INSTANCE.vboManager = vboManager;
	}

	/*
	 * Splash resource
	 */
	private final String IMG_SPLASH = "splash.png";
	private BitmapTextureAtlas splashTA;
	public ITextureRegion splashRegion;

	public void loadSplashResource() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GRAPHICS_PATH);
		splashTA = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTA, activity, IMG_SPLASH, 0, 0);
		splashTA.load();
	}

	public void unloadSplashResource() {
		splashTA.unload();
		splashTA = null;
		splashRegion = null;
	}

	/*
	 * Menu resource
	 */
	private final String IMG_BACKGROUND_MENU = "background_menu.png";
	private final String IMG_PLAY_BTN = "play_btn.png";
	private final String IMG_EXIT_BTN = "exit_btn.png";
	private BuildableBitmapTextureAtlas menuTA;
	public ITextureRegion backgroundMenuRegion;
	public ITextureRegion playBtnRegion;
	public ITextureRegion exitRtnRegion;

	public void loadMainMenuResource() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GRAPHICS_PATH);
		menuTA = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		backgroundMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTA, activity, IMG_BACKGROUND_MENU);
		playBtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTA, activity, IMG_PLAY_BTN);
		exitRtnRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTA, activity, IMG_EXIT_BTN);
		try {
			menuTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			menuTA.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	public void unloadMainMenuResource() {
		menuTA.unload();
		menuTA = null;
		backgroundMenuRegion = null;
		playBtnRegion = null;
		exitRtnRegion = null;
	}

	/*
	 * Mission resource
	 */
	private final String IMG_BACKGROUND_MISSION = "background.png";
	private final String IMG_MISSION_ACTIVE = "mission_active_btn.png";
	private final String IMG_MISSION_DISACTIVE = "mission_disactive_btn.png";
	private final String IMG_STAR_YELLOW = "star_yellow.png";
	private final String IMG_STAR_GREY = "star_grey.png";
	private final String FONT_PAINTDRP = "Paintdrp.ttf";
	private BuildableBitmapTextureAtlas missionTA;
	public ITextureRegion backgroundMissionRegion;
	public ITextureRegion missionActiveRegion;
	public ITextureRegion missionDisactiveRegion;
	public ITextureRegion starYellowRegion;
	public ITextureRegion starGreyRegion;
	public Font paintdrpFont;

	public void loadMissionResource() {
		// textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GRAPHICS_PATH);
		missionTA = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		backgroundMissionRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(missionTA, activity, IMG_BACKGROUND_MISSION);
		missionActiveRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(missionTA, activity, IMG_MISSION_ACTIVE);
		missionDisactiveRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(missionTA, activity, IMG_MISSION_DISACTIVE);
		starYellowRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(missionTA, activity, IMG_STAR_YELLOW);
		starGreyRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(missionTA, activity, IMG_STAR_GREY);
		try {
			missionTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			missionTA.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		// fonts
		FontFactory.setAssetBasePath(FONT_PATH);
		final ITexture fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		paintdrpFont = FontFactory.createFromAsset(activity.getFontManager(), fontTexture, activity.getAssets(), FONT_PAINTDRP, 24, true, Color.WHITE);
		paintdrpFont.load();
	}

	public void unloadMissionResource() {
		missionTA.unload();
		missionTA = null;
		backgroundMissionRegion = null;
		missionActiveRegion = null;
		missionDisactiveRegion = null;
		starYellowRegion = null;
		starGreyRegion = null;
		paintdrpFont.unload();
		paintdrpFont = null;
	}
}
