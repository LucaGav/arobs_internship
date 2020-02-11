Use nba;


CREATE TABLE team (
team_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
team_name VARCHAR(255) NOT NULL,
team_location VARCHAR(255) NOT NULL
);

CREATE TABLE player (
player_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
player_name VARCHAR(255) NOT NULL,
player_position VARCHAR(255) NOT NULL,
player_height VARCHAR(255) NOT NULL,
team_id INT NOT NULL,
FOREIGN KEY (team_id) REFERENCES team (team_id) 
);

CREATE TABLE referee (
referee_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
referee_name VARCHAR(255) NOT NULL
);

CREATE TABLE game (
game_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
game_location VARCHAR(255) NOT NULL,
team1_id INT NOT NULL,
team2_id INT NOT NULL,
FOREIGN KEY (team1_id) REFERENCES team (team_id),
FOREIGN KEY (team2_id) REFERENCES team (team_id)
);

CREATE TABLE referee_game (
referee_id INT NOT NULL,
game_id INT NOT NULL,
FOREIGN KEY(referee_id) REFERENCES referee(referee_id),
FOREIGN KEY(game_id) REFERENCES game(game_id),
PRIMARY KEY (referee_id, game_id)
);

INSERT INTO team(team_name, team_location)
VALUES ('Los Angeles Lakers','Los Angeles'),
	   ('Los Angeles Clippers','Los Angeles');

INSERT INTO player(player_name, player_position, player_height,team_id)
VALUES ('Lebron James','SF','6 foot 8',1),
       ('Anthony Davis','C','7 foot',1),
       ('Kawhi Leonard','SF','6 foot 7',2),
       ('Paul George','SG','6 foot 6',2);
       
INSERT INTO referee(referee_name)
VALUES ('Mike Scott'),
       ('Milk Mouse'),
       ('Dune Man'),
       ('Dan Prescott');
       
INSERT INTO game(game_location,team1_id,team2_id)
VALUES ('Los Angeles @ Lakers',1,2),
       ('Los Angeles @ Lakers',1,2),
       ('Los Angeles @ Clippers',2,1),
       ('Los Angeles @ Clippers',2,1);
       
INSERT INTO referee_game(referee_id,game_id)
VALUES (1,1),
	   (2,1),
       (3,1),
       (1,2),
       (3,2),
       (4,2),
       (1,3),
       (3,3),
       (4,3),
       (2,4),
       (3,4),
       (4,4);
      
       


