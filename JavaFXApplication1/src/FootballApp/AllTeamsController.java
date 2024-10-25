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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AllTeamsController implements Initializable {

        // user input
	@FXML
	private TextField start_date;

	@FXML
	private TextField end_date;

        // table queried
	@FXML
	private TableView<AllTeamsTable> allTable;

	@FXML
	private TableColumn<AllTeamsTable, String> countryName;

	@FXML
	private TableColumn<AllTeamsTable, String> wins;

	@FXML
	private TableColumn<AllTeamsTable, String> goalScored;

	@FXML
	private TableColumn<AllTeamsTable, String> games;

        // if user inputs dates, then clicks sort, it will connect to db then query
        // depending on the button clicked
	@FXML
	void sortGames(MouseEvent event) {

		ObservableList<AllTeamsTable> statsList = FXCollections.observableArrayList();
		String start = start_date.getText();
		String end = end_date.getText();

                // level 2 query
		String query = "SELECT goals.country, IFNULL(win.wins, 0) as wins, goals.goalsScored, goals.totalgames "
				+ "FROM (SELECT t.CountryName AS country, sum(m.away_score) + sum(m.home_score) as goalsScored, COUNT(m.MatchId) as totalgames "
				+ "FROM Teams t, Matches m WHERE (t.CountryName = m.home_team or t.CountryName = m.away_team) and m.Matchdate between '"+ start +"' and '"+end+"' "
				+ "GROUP BY t.CountryName) as goals "
				+ "LEFT JOIN (SELECT t.CountryName AS country, COUNT(m.matchId) AS wins "
				+ "FROM  Teams t, Matches m WHERE (t.CountryName = m.home_team and m.home_score > m.away_score or t.CountryName = m.away_team and m.away_score > m.home_score) and m.Matchdate between '"+ start +"' and '"+end+"' "
				+ "Group by t.CountryName) as win on goals.country = win.country ORDER BY totalgames DESC";
		
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery(query);

			while (rs.next()) {
				statsList.add(new AllTeamsTable(rs.getString("country"), rs.getString("wins"), rs.getString("goalsScored"), rs.getString("totalgames")));
			}
		} catch (SQLException e) {
			Logger.getLogger(AllTeamsController.class.getName()).log(Level.SEVERE, null, e);
		}

		allTable.setItems(statsList);
	}

	@FXML
	void sortGoals(MouseEvent event) {

		ObservableList<AllTeamsTable> statsList = FXCollections.observableArrayList();
		String start = start_date.getText();
		String end = end_date.getText();

		String query = "SELECT goals.country, IFNULL(win.wins, 0) as wins, goals.goalsScored, goals.totalgames "
				+ "FROM (SELECT t.CountryName AS country, sum(m.away_score) + sum(m.home_score) as goalsScored, COUNT(m.MatchId) as totalgames "
				+ "FROM Teams t, Matches m WHERE (t.CountryName = m.home_team or t.CountryName = m.away_team) and m.Matchdate between '"+ start +"' and '"+end+"' "
				+ "GROUP BY t.CountryName) as goals "
				+ "LEFT JOIN (SELECT t.CountryName AS country, COUNT(m.matchId) AS wins "
				+ "FROM  Teams t, Matches m WHERE (t.CountryName = m.home_team and m.home_score > m.away_score or t.CountryName = m.away_team and m.away_score > m.home_score) and m.Matchdate between '"+ start +"' and '"+end+"' "
				+ "Group by t.CountryName) as win on goals.country = win.country ORDER BY goalsScored DESC";
		
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery(query);

			while (rs.next()) {
				statsList.add(new AllTeamsTable(rs.getString("country"), rs.getString("wins"), rs.getString("goalsScored"), rs.getString("totalgames")));
			}
		} catch (SQLException e) {
			Logger.getLogger(AllTeamsController.class.getName()).log(Level.SEVERE, null, e);
		}

		allTable.setItems(statsList);
	}

	@FXML
	void sortWins(MouseEvent event) {

		ObservableList<AllTeamsTable> statsList = FXCollections.observableArrayList();
		String start = start_date.getText();
		String end = end_date.getText();

		String query = "SELECT goals.country, IFNULL(win.wins, 0) as wins, goals.goalsScored, goals.totalgames "
				+ "FROM (SELECT t.CountryName AS country, CASE m.home_team WHEN t.CountryName THEN sum(m.home_score) ELSE sum(m.away_score) END goalsScored, COUNT(m.MatchId) as totalgames "
				+ "FROM Teams t, Matches m WHERE (t.CountryName = m.home_team or t.CountryName = m.away_team) and m.Matchdate between '"+ start +"' and '"+end+"' "
				+ "GROUP BY t.CountryName) as goals "
				+ "LEFT JOIN (SELECT t.CountryName AS country, COUNT(m.matchId) AS wins "
				+ "FROM  Teams t, Matches m WHERE (t.CountryName = m.home_team and m.home_score > m.away_score or t.CountryName = m.away_team and m.away_score > m.home_score) and m.Matchdate between '"+ start +"' and '"+end+"' "
				+ "Group by t.CountryName) as win on goals.country = win.country ORDER BY wins DESC";
		
		try {
			Connection conn = DbConnect.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery(query);

			while (rs.next()) {
				statsList.add(new AllTeamsTable(rs.getString("country"), rs.getString("wins"), rs.getString("goalsScored"), rs.getString("totalgames")));
			}
		} catch (SQLException e) {
			Logger.getLogger(AllTeamsController.class.getName()).log(Level.SEVERE, null, e);
		}

		allTable.setItems(statsList);
	}
        
        @FXML
        void backButton(MouseEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root)); 
        }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		countryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		wins.setCellValueFactory(new PropertyValueFactory<>("wins"));
		goalScored.setCellValueFactory(new PropertyValueFactory<>("goalScored"));
		games.setCellValueFactory(new PropertyValueFactory<>("games"));
	}
}
