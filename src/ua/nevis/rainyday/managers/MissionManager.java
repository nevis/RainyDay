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
		missions.add(new Mission(false, 10, 140, 70));
		
		missions.add(new Mission(false, 11, 140, 70));
		missions.add(new Mission(false, 12, 140, 70));
		missions.add(new Mission(false, 13, 140, 70));
		missions.add(new Mission(false, 14, 140, 70));
		missions.add(new Mission(false, 15, 140, 70));
		missions.add(new Mission(false, 16, 140, 70));
		missions.add(new Mission(false, 17, 140, 70));
		missions.add(new Mission(false, 18, 140, 70));
		missions.add(new Mission(false, 19, 140, 70));
		missions.add(new Mission(false, 20, 140, 70));
		
		missions.add(new Mission(false, 21, 140, 70));
		missions.add(new Mission(false, 22, 140, 70));
		missions.add(new Mission(false, 23, 140, 70));
		missions.add(new Mission(false, 24, 140, 70));
		missions.add(new Mission(false, 25, 140, 70));
		missions.add(new Mission(false, 26, 140, 70));
		missions.add(new Mission(false, 27, 140, 70));
		missions.add(new Mission(false, 28, 140, 70));
		missions.add(new Mission(false, 29, 140, 70));
		missions.add(new Mission(false, 30, 140, 70));
		
		missions.add(new Mission(false, 31, 140, 70));
		missions.add(new Mission(false, 32, 140, 70));
		missions.add(new Mission(false, 33, 140, 70));
		missions.add(new Mission(false, 34, 140, 70));
		missions.add(new Mission(false, 35, 140, 70));
		missions.add(new Mission(false, 36, 140, 70));
		missions.add(new Mission(false, 37, 140, 70));
	}
}
