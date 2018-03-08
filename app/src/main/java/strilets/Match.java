package strilets;

public class Match {

	int id;
	String nameTeam1;
	String nameTeam2;
	String goalTeam1;
	String goalTeam2;
	int numberMatch;

	Match() {
		super();
	}

	public Match(String nameTeam1, String nameTeam2, String goalTeam1, String goalTeam2, int numberMatch) {
		this.nameTeam1 = nameTeam1;
		this.nameTeam2 = nameTeam2;
		this.goalTeam1 = goalTeam1;
		this.goalTeam2 = goalTeam2;
		this.numberMatch = numberMatch;
	}

	public void setId(int number) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

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

	public void setGoalTeam1(String goalTeam1) {
		this.goalTeam1 = goalTeam1;
	}

	public String getGoalTeam1() {
		return this.goalTeam1;
	}

	public void setGoalTeam2(String goalTeam2) {
		this.goalTeam2 = goalTeam2;
	}

	public String getGoalTeam2() {
		return this.goalTeam2;
	}

	public void setNumberMatch(int numberMatch) {
		this.numberMatch = numberMatch;
	}

	public int getNumberMatch() {
		return this.numberMatch;
	}

}
