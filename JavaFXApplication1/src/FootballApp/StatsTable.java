package FootballApp;

public class StatsTable {

	String year,wins,ties,losses,tgames,goalScored;

	public StatsTable(String year, String wins, String ties, String losses, String tgames, String goalScored) {
		this.year = year;
		this.wins = wins;
		this.ties = ties;
		this.losses = losses;
		this.tgames = tgames;
		this.goalScored = goalScored;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getWins() {
		return wins;
	}

	public void setWins(String wins) {
		this.wins = wins;
	}

	public String getTies() {
		return ties;
	}

	public void setTies(String ties) {
		this.ties = ties;
	}

	public String getLosses() {
		return losses;
	}

	public void setLosses(String losses) {
		this.losses = losses;
	}

	public String getTgames() {
		return tgames;
	}

	public void setTgames(String tgames) {
		this.tgames = tgames;
	}

	public String getGoalScored() {
		return goalScored;
	}

	public void setGoalScored(String goalScored) {
		this.goalScored = goalScored;
	}
	
	
}
