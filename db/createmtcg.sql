CREATE TABLE IF NOT EXISTS users(
user_id SERIAL,
username VARCHAR UNIQUE PRIMARY KEY,
password VARCHAR,
name VARCHAR,
coins INT DEFAULT 20,
bio VARCHAR,
image VARCHAR,
elo FLOAT DEFAULT 100,
wins INT DEFAULT 0,
losses INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS logincredentials (
username VARCHAR(255) PRIMARY KEY, 
password VARCHAR(255) NOT NULL, 
token VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS cards (
card_id VARCHAR(255) PRIMARY KEY, 
username VARCHAR NOT NULL,
name VARCHAR(255) NOT NULL,
type VARCHAR(255) NOT NULL, 
element VARCHAR(255) NOT NULL,
damage FLOAT NOT NULL
);

CREATE TABLE packages (
package_id serial PRIMARY KEY,
cards VARCHAR NOT NULL
);


CREATE TABLE IF NOT EXISTS stack (
card_id VARCHAR PRIMARY KEY, 
name VARCHAR NOT NULL,
type VARCHAR,
element VARCHAR,
damage FLOAT NOT NULL,
username VARCHAR NOT NULL,
in_deck BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS deck (
card_id VARCHAR UNIQUE PRIMARY KEY,
name VARCHAR NOT NULL,
damage FLOAT NOT NULL,
username VARCHAR NOT NULL,
in_deck BOOLEAN DEFAULT FALSE
);


CREATE TABLE IF NOT EXISTS battle(
battle_id serial PRIMARY KEY,
player1 VARCHAR NOT NULL,
player2 VARCHAR,
);

CREATE TABLE IF NOT EXISTS battlelog(
log_id serial PRIMARY KEY,
battle_id INT,
battle_log VARCHAR
);

CREATE TABLE IF NOT EXISTS tradings(
trading_id VARCHAR PRIMARY KEY,
username VARCHAR NOT NULL,
card_id VARCHAR NOT NULL,
card_type VARCHAR NOT NULL,
min_damage FLOAT NOT NULL
);