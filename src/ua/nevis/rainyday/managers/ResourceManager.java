package ua.nevis.rainyday.managers;

public class ResourceManager {
	private static ResourceManager INSTANCE = new ResourceManager();

	private ResourceManager() {

	}

	public static ResourceManager getInstance() {
		return INSTANCE;
	}
}
