# BlackJack Game

## Overview
This is a simple graphical implementation of the classic card game BlackJack, created using Java and Swing. The game allows a player to compete against a dealer, with the objective of getting a hand value as close to 21 as possible without exceeding it.

## Features
- Player can "Hit" to take another card or "Stay" to hold their current hand.
- Dealer automatically plays according to standard rules (hits until reaching 17).
- Displays the outcome of the game (Win, Lose, Draw).
- Resets the game to allow for a new round.

## Technologies Used
- Java
- Swing for GUI

## Game Logic
- The game uses a standard deck of cards and shuffles it at the beginning of each round.
- Aces can count as either 1 or 11, depending on the player's total.
- The player and dealer each start with two cards.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/ForbiddenKiwis/BlackJack.git
