Description of the system architecture
The system architecture of the application is made up of 3 components. For the user interface we used JavaFX writing in an FXML file which is the file type that corresponds to JavaFX. After creating the pages, each page has a controller class, which connects the FXML file to the Java controller class allowing for functionality of each page (each page has its own controller). After connecting to FXML, the controller then will connect to the SQL database, using a DB connector class which connects to the football database. The controller takes user input and queries the database; allowing for the desired result of the user.

Description of the dataset
Our dataset contains all the international men’s football matches played from 1872-2020. The attributes of the dataset include the date of the match, the scores, the teams that played, the location it was played in etc. The reason we chose this dataset is because it gives us a lot of information about the development of football through the ages. From this dataset, we can extract a lot of useful information such as figuring out who the best team is, which team has dominated a certain era of football, the best home/away team and the best offensive/defensive team in a specified range of time. This dataset that we discovered had a high usability score and had no null values. Our dataset contains about 40,000 results which meets the minimum requirement of 10,000.

ER diagram (final version from the previous checkpoint copied here)




<img width="408" alt="image" src="https://github.com/user-attachments/assets/cd6b8e26-609b-4584-99df-ba40fc68189c">




Relational model (final version from the previous checkpoint copied here)
competesIn( teamID: int, tournamentID: int)
playedIn(City: String, Country: String, MatchID: int)
played(pid: integer, matchID: integer, HomeID: integer, AwayID: int)
Tournament(tournamentID: Real, tournamentName: String)
Team(TeamID: Real, TeamName: String, Wins: Integer, goalScored:Integer)
Match(MatchID: Real, Home: String, Away: String, MatchDate: DATE, AwayScore: Integer, HomeScore: Integer) 

Implementation: description of the prototype
 
The prototype is composed of multiple pages, which consists of a signup/login page that takes you to the home page. From the home page there are many button options that can take you to the desired page in the application such as statistics, help and about.
2.6  Evaluation: describe how you test your application (e.g, create testing scenarios or queries or something else, running your application through these scenarios/queries/etc.., checking the returned results and see how the results make sense or not).
We had multiple testing scenarios that we came up with to ensure that the functionality of our application was correct. Our tests were divided into two distinct parts. First we checked if our database was implemented properly. We started out with the test to see if there were any null values present in any of our tables. We used a query to check if null was present in any column. For example when we were generating (counting) values for yearly wins, ties, losses we were expecting null values to be output because a country can have zero wins, losses or ties. Expecting this allows us to set null values to zero then we would compare the total games to the sum of wins, ties, loses and check if all tuples are equal. 
 After this, we ran tests to see if all columns exist and duplicates are not present in the database. Next we cross checked if the tables were generated correctly and if all our primary keys were unique. Second part of the test was to check if our application retrieved the correct data from the database. We wrote a query that would request for false data and an empty set which would return an empty set on success. Then various tests were performed to check whether correct values were being received from the database. 
To test accurate queries we would initially write the queries in SQLite and determine if the results are realistic for example countries should not be scoring high in games since most international football matches end in ties. After we implemented the queries in the java application and compared results, if they are equivalent then the query is successful
