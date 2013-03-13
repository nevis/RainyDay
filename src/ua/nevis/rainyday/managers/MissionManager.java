package ua.nevis.rainyday.managers;

import java.util.ArrayList;
import java.util.List;

import ua.nevis.rainyday.data.Mission;

public class MissionManager {
	private static MissionManager INSTANCE = new MissionManager();
	private List<Mission> missions = new ArrayList<Mission>();

	private MissionManager() {

	}

	public static MissionManager getInstance() {
		return INSTANCE;
	}

	/*
	 * Getters and setters
	 */
	public List<Mission> getMissions() {
		return missions;
	}

	/*
	 * Logic
	 */
	public void loadMission() {
		createMissions();
	}
	
	private void createMissions() {
		missions.add(new Mission(true, 1, 20, 10));
		missions.add(new Mission(false, 2, 40, 20));
		missions.add(new Mission(false, 3, 60, 30));
		missions.add(new Mission(false, 4, 80, 40));
		missions.add(new Mission(false, 5, 100, 50));
		missions.add(new Mission(false, 6, 120, 60));
		missions.add(new Mission(false, 7, 140, 70));
		missions.add(new Mission(false, 8, 140, 70));
		missions.add(new Mission(false, 9, 140, 70));
	}
}
