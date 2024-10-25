/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FootballApp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author jojmo
 */
public class TeamsController implements Initializable {

    // fields for finding all teams query 
   @FXML
    private TableView<TeamTable> table;

    @FXML
    private TableColumn<TeamTable, String> countryName;

    ObservableList<TeamTable> list = FXCollections.observableArrayList();

    // fields for retrieving teams stats
    @FXML
    private TableView<StatsTable> statTable;

    @FXML
    private TableColumn<StatsTable, String> year_col;

    @FXML
    private TableColumn<StatsTable, String> wins_col;

    @FXML
    private TableColumn<StatsTable, String> ties_col;

    @FXML
    private TableColumn<StatsTable, String> losses_col;

    @FXML
    private TableColumn<StatsTable, String> tgames_col;

    @FXML
    private TableColumn<StatsTable, String> goalScored_col;
	
    
    // fields for finding tournaments played in
    @FXML
    private TableView<TeamTable> tournamentTable;

    @FXML
    private TableColumn<TeamTable, String> tournament_col;

    
    // fields for finding cities and countries that teams have played in
    @FXML
    private TableView<LocationTable> locationTable;

    @FXML
    private TableColumn<LocationTable, String> city_col;

    @FXML
    private TableColumn<LocationTable, String> country_col;

	@FXML
	private Text clickedCountry;

	

	@FXML
	void clickOnCountry(MouseEvent event) {
		ObservableList<StatsTable> statsList = FXCollections.observableArrayList();
		String clickedCountry = "";
		if (event.getClickCount() == 1) {
			clickedCountry = table.getSelectionModel().getSelectedItem().getCountryName();
		}
                // level 2 query
		this.clickedCountry.setText(clickedCountry);
		String query = "SELECT goals.Year, IFNULL(win.Wins, 0) as Wins, IFNULL(tie.ties, 0) as Ties, IFNULL(loss.losses,0) as Losses, goals.totalgames, goals.goalsScored "
				+ "FROM (SELECT strftime('%Y', Matchdate) as Year, CASE m.home_team WHEN '"+ clickedCountry+"' THEN sum(m.home_score) ELSE sum(m.away_score) END goalsScored, COUNT(m.MatchId) as totalgames "
				+ "From Matches m Where ( m.home_team = '"+ clickedCountry +"' or m.away_team = '"+ clickedCountry +"') "
				+ "Group by strftime('%Y', Matchdate)) as goals "
				+ "LEFT JOIN (SELECT strftime('%Y', Matchdate) as Year, COUNT(m.MatchId) as Wins "
				+ "From Matches m Where ( m.home_team = '"+ clickedCountry +"' and m.home_score > m.away_score or m.away_team = '"+ clickedCountry +"' and m.away_score > m.home_score ) "
				+ "Group by strftime('%Y', Matchdate)) as win on win.Year = goals.Year "
				+ "LEFT JOIN (SELECT strftime('%Y', Matchdate) as Year, COUNT(m.MatchId) as losses "
				+ "From Matches m Where ( m.home_team = '"+ clickedCountry +"' and m.home_score < m.away_score or m.away_team = '"+ clickedCountry +"' and m.away_score < m.home_score ) "
				+ "Group by strftime('%Y', Matchdate)) as loss on goals.Year = loss.Year "
				+ "LEFT JOIN (SELECT strftime('%Y', Matchdate) as Year, COUNT(m.MatchId) as ties "
				+ "From Matches m Where ( m.home_team = '"+ clickedCountry +"' or m.away_team = '"+ clickedCountry +"') and m.home_score = m.away_score "
				+ "Group by strftime('%Y', Matchdate)) as tie on goals.Year = tie.Year "
				+ "GROUP BY goals.Year";
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery(query);

			while (rs.next()) {
				statsList.add(new StatsTable(rs.getString("Year"), rs.getString("Wins"), rs.getString("Ties"), rs.getString("Losses"), rs.getString("totalgames"), rs.getString("goalsScored")));
			}
		} catch (SQLException e) {
			Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, e);
		}
		
		statTable.setItems(statsList);
		
		// calling tournament and teams table from SQL and updating the UI table, level 2 query
		ObservableList<TeamTable> tournamentList = FXCollections.observableArrayList();
		String tournamentQuery = "SELECT tr.tournamentName FROM Tournament tr, Teams t, CompetesIn c WHERE t.CountryName = '"+ clickedCountry +"' and t.TeamId = c.TeamId and c.TournamentId = tr.tid";
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery(tournamentQuery);

			while (rs.next()) {
				tournamentList.add(new TeamTable(rs.getString("tournamentName")));
			}
		} catch (SQLException e) {
			Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, e);
		}
		
		tournamentTable.setItems(tournamentList);
		
		// Code for calling matches and playedIn tables from SQL and updating UI level 2 query
		ObservableList<LocationTable> locationlist = FXCollections.observableArrayList();
		String locationQuery = "SELECT DISTINCT p.City, p.Country " + 
				"FROM Matches m, PlayedIn p " + 
				"WHERE (m.away_team = '" + clickedCountry + "' or m.home_team = '" + clickedCountry + "') and m.MatchId = p.MatchId";
		
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery(locationQuery);

			while (rs.next()) {
				locationlist.add(new LocationTable(rs.getString("City"), rs.getString("Country")));
			}
		} catch (SQLException e) {
			Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, e);
		}
		
		locationTable.setItems(locationlist);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT CountryName FROM Teams");

			while (rs.next()) {
				list.add(new TeamTable(rs.getString("CountryName")));
			}
		} catch (SQLException e) {
			Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, e);
		}

		countryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		
		year_col.setCellValueFactory(new PropertyValueFactory<>("year"));
		wins_col.setCellValueFactory(new PropertyValueFactory<>("wins"));
		ties_col.setCellValueFactory(new PropertyValueFactory<>("ties"));
		losses_col.setCellValueFactory(new PropertyValueFactory<>("losses"));
		tgames_col.setCellValueFactory(new PropertyValueFactory<>("tgames"));
		goalScored_col.setCellValueFactory(new PropertyValueFactory<>("goalScored"));
		
		tournament_col.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		
		city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
		country_col.setCellValueFactory(new PropertyValueFactory<>("country"));
		table.setItems(list);
	}

	@FXML
	void sortGoals(MouseEvent event) {
		list.removeAll();
		table.getItems().clear();
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement()
					.executeQuery("SELECT CountryName FROM Teams ORDER BY GoalScored DESC");

			while (rs.next()) {
				list.add(new TeamTable(rs.getString("CountryName")));
			}
		} catch (SQLException e) {
			Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, e);
		}

		countryName.setCellValueFactory(new PropertyValueFactory<>("CountryName"));

		table.setItems(list);
	}

	@FXML
	void sortWins(MouseEvent event) {

		list.removeAll();
		table.getItems().clear();
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT CountryName FROM Teams ORDER BY Wins DESC");

			while (rs.next()) {
				list.add(new TeamTable(rs.getString("CountryName")));
			}
		} catch (SQLException e) {
			Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, e);
		}

		countryName.setCellValueFactory(new PropertyValueFactory<>("CountryName"));
		table.setItems(list);
	}
    
        @FXML
        void backButton(MouseEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root)); 
        }
}
