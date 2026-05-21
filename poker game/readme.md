# Poker Card Drawer Game

## Overview
This project is a simple Java Poker Card Drawer game.

The system is designed **only to draw and distribute cards** to players.  
It does **not** calculate winners, evaluate poker hands, or enforce poker rules.

Players are expected to:
- Know their own cards
- Understand poker rules
- Play manually according to the rules they choose

The application focuses only on:
- Creating a deck of cards
- Shuffling the deck
- Drawing cards
- Giving cards to players

---

# Features

- Standard 52-card deck
- Deck shuffling
- Card drawing system
- Multiple player support
- Simple console-based gameplay
- Lightweight Java implementation

---

# Project Structure

```text
poker game/
│
├── src/
│   ├── app/
│   │   └── Main.java
│   │
│   ├── model/
│   │   ├── Card.java
│   │   ├── Deck.java
│   │   └── Player.java
│   │
│   └── out/
│       ├── app/
│       └── model/
```

---

# Classes Description

## Card.java
Represents a single playing card.

Contains:
- Card suit
- Card rank/value

---

## Deck.java
Handles the deck operations.

Responsibilities:
- Creating all 52 cards
- Shuffling cards
- Drawing cards from the deck

---

## Player.java
Represents a player in the game.

Responsibilities:
- Holding player cards
- Displaying player hand

---

## Main.java
The main entry point of the application.

Responsibilities:
- Starting the game
- Creating players
- Distributing cards
- Displaying results

---

# Requirements

- Java JDK 8 or higher
- Command line or Java IDE

---

# How to Run

## Compile the project

```bash
javac src/model/*.java src/app/Main.java
```

## Run the project

```bash
java -cp src app.Main
```

---

# Example Gameplay

```text
Player 1 cards:
Ace of Spades
10 of Hearts

Player 2 cards:
King of Clubs
7 of Diamonds
```

After cards are drawn, players continue the game manually using standard poker rules.

---

# Notes

- This project does not determine poker winners.
- No betting system is included.
- No AI players are included.
- The game acts only as a digital card dealer.

---

# Future Improvements

Possible future upgrades:
- Poker hand evaluation
- Winner detection
- Betting system
- GUI interface
- Multiplayer networking
- Game statistics

---




