# Checkers

Simple implementation of [international checkers](https://en.wikipedia.org/wiki/International_draughts).

Two players can play checkers against each other. Moves are specified using the standard checkers
notation using 1-based "square numbers". On a 10x10 board the top-left most square has number 1
and the bottom-right square has number 50.

The game is shown in the terminal. Instructions are printed at startup.

Moves are validated before executed:
 - each player can only slide pieces forwards
 - jumps can be done in either direction
 - jumps can consist of multiple steps

Note: "kings row" and promoting a man to a king have not been implemented.

## Prerequisites

* Maven 3
* Java 17

## Build

Run tests, build and create a JAR file with:
```bash
mvn package 
```

## Run

Run the game with:
```bash
java -jar target/checkers.jar
```

## Example game

Run a partial example game with:
```bash
java -jar target/checkers.jar < example-game.txt
```
