package strilets;

public class Table implements  Comparable<Table> {

	String nameTeam;
	int countGames;
	int missedBalls;
	int scoredBalls;
	int differenceBalls;
	int points;

	Table() {
		super();
	}

	public Table(String nameTeam, int countGames, int missedBalls, int scoredBalls, int differenceBalls, int points) {
		this.nameTeam = nameTeam;
		this.countGames = countGames;
		this.missedBalls = missedBalls;
		this.scoredBalls = scoredBalls;
		this.differenceBalls = differenceBalls;
		this.points = points;
	}

	public void setNameTeam(String nameTeam) {
		this.nameTeam = nameTeam;
	}

	public String getNameTeam() { return this.nameTeam; }

	public void setCountGames(int countGames) {
		this.countGames = countGames;
	}

	public int getCountGames() {
		return this.countGames;
	}

	public void setMissedBalls(int missedBalls) {
		this.missedBalls = missedBalls;
	}

	public int getMissedBalls() {
		return this.missedBalls;
	}

	public void setScoredBalls(int scoredBalls) { this.scoredBalls = scoredBalls; }

	public int getScoredBalls() { return this.scoredBalls; }

	public void setDifferenceBalls(int differenceBalls) {
		this.differenceBalls = differenceBalls;
	}

	public int getDifferenceBalls() {
		return this.differenceBalls;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return this.points;
	}
	
	public int compareTo(Table team) {

		if(this.points != team.getPoints())
			return team.getPoints() - this.points;
		else{
			if(this.differenceBalls != team.getDifferenceBalls())
				return team.getDifferenceBalls() - this.differenceBalls;
			else{
				if(this.scoredBalls != team.getScoredBalls())
					return team.getScoredBalls() - this.scoredBalls;
				else
					return this.nameTeam.compareToIgnoreCase(team.getNameTeam());
			}
		}
	}

}
