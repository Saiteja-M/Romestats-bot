package romestats.bot.utils;

import com.jagrosh.jdautilities.examples.doc.Author;

@Author("Saiteja")
public class WeaponObj {
	
	private String name;
	private int kills;
	private int headshots;
	private int shotsFired;
	private int shotsHit;
	
	public WeaponObj(String name, int kills, int headshots, int shotsFired, int shotsHit) {
		super();
		this.name = name;
		this.kills = kills;
		this.headshots = headshots;
		this.shotsFired = shotsFired;
		this.shotsHit = shotsHit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getHeadshots() {
		return headshots;
	}

	public void setHeadshots(int headshots) {
		this.headshots = headshots;
	}

	public int getShotsFired() {
		return shotsFired;
	}

	public void setShotsFired(int shotsFired) {
		this.shotsFired = shotsFired;
	}

	public int getShotsHit() {
		return shotsHit;
	}

	public void setShotsHit(int shotsHit) {
		this.shotsHit = shotsHit;
	}
	
	public String getAccuracy() {
		float acc = (((float) shotsHit / (float) shotsFired) * 100);
		double accuracy = Math.round(acc * 100.0) / 100.0;
		return accuracy + "%";
	}

}
