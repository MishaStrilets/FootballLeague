package strilets;

public class Match {

	Match() {
	}

	public Match(String nameTeam1, String nameTeam2, int goalTeam1, int goalTeam2, int match) {
		this.nameTeam1 = nameTeam1;
		this.nameTeam2 = nameTeam2;
		this.goalTeam1 = goalTeam1;
		this.goalTeam2 = goalTeam2;
		this.match = match;
	}

	private String nameTeam1;
	private String nameTeam2;
	private int goalTeam1;
	private int goalTeam2;
	private int match;

	public void setNameTeam1(String nameTeam1) {
		this.nameTeam1 = nameTeam1;
	}

	public String getNameTeam1() {
		return this.nameTeam1;
	}

	public void setNameTeam2(String nameTeam2) {
		this.nameTeam2 = nameTeam2;
	}

	public String getNameTeam2() {
		return this.nameTeam2;
	}

	public void setGoalTeam1(int goalTeam1) {
		this.goalTeam1 = goalTeam1;
	}

	public int getGoalTeam1() {
		return this.goalTeam1;
	}

	public void setGoalTeam2(int goalTeam2) {
		this.goalTeam2 = goalTeam2;
	}

	public int getGoalTeam2() {
		return this.goalTeam2;
	}

	public void setMatch(int match) {
		this.match = match;
	}

	public int getMatch() {
		return this.match;
	}

}
