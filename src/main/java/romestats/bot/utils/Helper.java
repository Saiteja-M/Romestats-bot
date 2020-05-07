package romestats.bot.utils;

public class Helper {

	public String getMapName(String level) {
		return level;
	}

	public static String getRankName(int score) {
		if (score <= 6500)
			return "Noob";
		else if (score > 6500 && score < 11000)
			return "Private I";
		else if (score > 11000 && score < 18500)
			return "Private II";
		else if (score > 18500 && score < 28000)
			return "Private III";
		else if (score > 28000 && score < 40000)
			return "Specialist I";
		else if (score > 40000 && score < 53000)
			return "Specialist II";
		else if (score > 53000 && score < 68000)
			return "Specialist III";
		else if (score > 68000 && score < 84000)
			return "Corporal I";
		else if (score > 84000 && score < 100000)
			return "Corporal II";
		else if (score > 100000 && score < 120000)
			return "Corporal III";
		else if (score > 120000 && score < 138000)
			return "Sergeant I";
		else if (score > 138000 && score < 158000)
			return "Sergeant II";
		else if (score > 158000 && score < 179000)
			return "Sergeant III";
		else if (score > 179000 && score < 200000)
			return "Staff Sergeant I";
		else if (score > 200000 && score < 224000)
			return "Staff Sergeant II";
		else if (score > 224000 && score < 247000)
			return "Staff Sergeant III";
		else if (score > 247000 && score < 272000)
			return "Master Sergeant I";
		else if (score > 272000 && score < 297000)
			return "Master Sergeant II";
		else if (score > 297000 && score < 323000)
			return "Master Sergeant III";
		else if (score > 323000 && score < 350000)
			return "First Sergeant I";
		else if (score > 350000 && score < 377000)
			return "First Sergeant II";
		else if (score > 377000 && score < 405000)
			return "First Sergeant III";
		else if (score > 405000 && score < 437000)
			return "Warrant Officer I";
		else if (score > 437000 && score < 472000)
			return "Warrant Officer II";
		else if (score > 472000 && score < 537000)
			return "Warrant Officer III";
		else if (score > 537000 && score < 620000)
			return "Chief Warrant Officer I";
		else if (score > 620000 && score < 720000)
			return "Chief Warrant Officer II";
		else if (score > 720000 && score < 832000)
			return "Chief Warrant Officer II";
		else if (score > 832000 && score < 956000)
			return "Chief Warrant Officer III";
		else if (score > 956000 && score < 1090000)
			return "2nd Lieutenant II";
		else if (score > 1090000 && score < 1240000)
			return "2nd Lieutenant III";
		else if (score > 1240000 && score < 1400000)
			return "1st Lieutenant I";
		else if (score > 1400000 && score < 1550000)
			return "1st Lieutenant II";
		else if (score > 1550000 && score < 1730000)
			return "1st Lieutenant III";
		else if (score > 1730000 && score < 1900000)
			return "Captain I";
		else if (score > 1900000 && score < 2100000)
			return "Captain II";
		else if (score > 2100000 && score < 2300000)
			return "Captain III";
		else if (score > 2300000 && score < 2500000)
			return "Major I";
		else if (score > 2500000 && score < 2700000)
			return "Major II";
		else if (score > 2700000 && score < 2900000)
			return "Major III";
		else if (score > 2900000 && score < 3140000)
			return "Lieutenant Colonel I";
		else if (score > 3140000 && score < 3370000)
			return "Lieutenant Colonel II";
		else if (score > 3370000 && score < 3600000)
			return "Lieutenant Colonel III";
		else if (score > 3600000 && score < 3800000)
			return "Colonel I";
		else if (score > 3800000 && score < 4010000)
			return "Colonel II";
		else if (score > 4010000 && score < 4300000)
			return "Colonel III";
		else if (score > 4300000 && score < 4600000)
			return "Brigadier General I";
		else if (score > 4600000 && score < 4900000)
			return "Brigadier General II";
		else if (score > 4900000 && score < 5100000)
			return "Brigadier General III";
		else if (score > 5100000 && score < 5400000)
			return "General";
		else if (score > 5400000)
			return "General of the Army";
		else
			return "Noob";
	}
}
