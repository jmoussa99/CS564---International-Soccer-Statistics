package FootballApp;

public class AllTeamsTable {

	String countryName, wins, goalScored, games;

	public AllTeamsTable(String countryName, String wins, String goalScored, String games) {
		this.countryName = countryName;
		this.wins = wins;
		this.goalScored = goalScored;
		this.games = games;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getWins() {
		return wins;
	}

	public void setWins(String wins) {
		this.wins = wins;
	}

	public String getGoalScored() {
		return goalScored;
	}

	public void setGoalScored(String goalScored) {
		this.goalScored = goalScored;
	}

	public String getGames() {
		return games;
	}

	public void setGames(String games) {
		this.games = games;
	}
	
	
}
