package ua.nevis.rainyday.data;

import java.io.Serializable;

public class Mission implements Serializable {
	private static final long serialVersionUID = -5454863093402565470L;
	private boolean active;
	private String missionName;
	private int scoreValue;
	private int starCount;
	private int maxDropCount;
	private int completeDropCount;

	public Mission() {
		this(false, "none", 0, 0);
	}

	public Mission(boolean active, String missionName, int maxDropCount, int completeDropCount) {
		this.active = active;
		this.missionName = missionName;
		this.maxDropCount = maxDropCount;
		this.completeDropCount = completeDropCount;
		this.scoreValue = 0;
		this.starCount = 0;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionNumber(String missionName) {
		this.missionName = missionName;
	}

	public int getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		if (starCount > 3) {
			this.starCount = 3;
		} else {
			this.starCount = starCount;
		}
	}

	public int getMaxDropCount() {
		return maxDropCount;
	}

	public void setMaxDropCount(int maxDropCount) {
		this.maxDropCount = maxDropCount;
	}

	public int getCompleteDropCount() {
		return completeDropCount;
	}

	public void setCompleteDropCount(int completeDropCount) {
		this.completeDropCount = completeDropCount;
	}
}
