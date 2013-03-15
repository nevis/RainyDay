package ua.nevis.rainyday.managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.content.Context;
import ua.nevis.rainyday.data.Mission;

public class MissionManager {
	private final String FILE_NAME = "rdms.info";
	private static MissionManager INSTANCE = new MissionManager();
	private ArrayList<Mission> missions;
	private Mission currentMission;

	private MissionManager() {

	}

	public static MissionManager getInstance() {
		return INSTANCE;
	}

	/*
	 * Getters and setters
	 */
	public ArrayList<Mission> getMissions() {
		return missions;
	}

	public Mission getCurrentMission() {
		return currentMission;
	}

	public void setCurrentMission(Mission currentMission) {
		this.currentMission = currentMission;
	}

	/*
	 * Logic
	 */
	@SuppressWarnings("unchecked")
	public void loadMission() {
		try {
			FileInputStream fis = ResourceManager.getInstance().activity.openFileInput(FILE_NAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			ArrayList<Mission> loadMissions = (ArrayList<Mission>) is.readObject();
			is.close();
			if (loadMissions != null && loadMissions.size() > 0) {
				missions = new ArrayList<Mission>(loadMissions);
			} else {
				createMissions();
			}
		} catch (Exception e) {
			createMissions();
		}
	}

	public void saveMission() {
		try {
			FileOutputStream fos = ResourceManager.getInstance().activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(missions);
			os.close();
		} catch (Exception e) {
			//
		}
	}

	private void createMissions() {
		missions = new ArrayList<Mission>();
		missions.add(new Mission(true, "1-1", 20, 10));
		missions.add(new Mission(false, "1-2", 40, 20));
		missions.add(new Mission(false, "1-3", 60, 30));
		missions.add(new Mission(false, "1-4", 80, 40));
		missions.add(new Mission(false, "1-5", 100, 50));
		missions.add(new Mission(false, "1-6", 120, 60));
		missions.add(new Mission(false, "1-7", 140, 70));
		missions.add(new Mission(false, "1-8", 140, 70));
		missions.add(new Mission(false, "1-9", 140, 70));
		missions.add(new Mission(false, "1-10", 140, 70));

		missions.add(new Mission(false, "1-11", 140, 70));
		missions.add(new Mission(false, "1-12", 140, 70));
		missions.add(new Mission(false, "1-13", 140, 70));
		missions.add(new Mission(false, "1-14", 140, 70));
		missions.add(new Mission(false, "1-15", 140, 70));
		missions.add(new Mission(false, "1-16", 140, 70));
		missions.add(new Mission(false, "1-17", 140, 70));
		missions.add(new Mission(false, "1-18", 140, 70));
		missions.add(new Mission(false, "1-19", 140, 70));
		missions.add(new Mission(false, "1-20", 140, 70));

		missions.add(new Mission(false, "1-21", 140, 70));
		missions.add(new Mission(false, "1-22", 140, 70));
		missions.add(new Mission(false, "1-23", 140, 70));
		missions.add(new Mission(false, "1-24", 140, 70));
		missions.add(new Mission(false, "1-25", 140, 70));
		missions.add(new Mission(false, "1-26", 140, 70));
		missions.add(new Mission(false, "1-27", 140, 70));
		missions.add(new Mission(false, "1-28", 140, 70));
		missions.add(new Mission(false, "1-29", 140, 70));
		missions.add(new Mission(false, "1-30", 140, 70));

		missions.add(new Mission(false, "1-31", 140, 70));
		missions.add(new Mission(false, "1-32", 140, 70));
		missions.add(new Mission(false, "1-33", 140, 70));
		missions.add(new Mission(false, "1-34", 140, 70));
		missions.add(new Mission(false, "1-35", 140, 70));
		missions.add(new Mission(false, "1-36", 140, 70));
		missions.add(new Mission(false, "1-37", 140, 70));
	}
}
