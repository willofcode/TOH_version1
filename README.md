# Tower of Hanoi Puzzle Game

## Overview:
Tower of Hanoi is a classic puzzle game that involves moving disks from one peg to another. The objective is to move all the disks from the first peg to the last peg, obeying the following rules:
1. Only one disk can be moved at a time.
2. Each move consists of taking the top disk from one of the pegs and placing it on top of another peg.
3. No disk may be placed on top of a smaller disk.

## Features:
- Java Swing GUI: The game is implemented using Java Swing, providing a graphical interface for user interaction.
- Dynamic Disk Movement: Disks move dynamically from one peg to another, providing a visually engaging experience.
- Resign Option: Players can resign from the game anytime, resetting the game state to its initial configuration.
- Winning Condition: The game automatically checks for the winning condition and prompts the user to play again or exit upon winning.
- Input Validation: User inputs are validated to ensure they adhere to the game rules and constraints.
- Customizable Number of Disks: Users can choose the number of disks to play with, with a maximum limit of 10.

## How to Play:
1. Run the `towerOfHanoi` Java application.
2. Enter the number of disks you want to play with when prompted. (Maximum 10 disks)
3. Click the "Move" button to move a disk from one peg to another. Enter the move in the "X to Y" format, where X and Y are the peg numbers.
4. If you wish to resign from the game, click the "Resign" button.
5. The game automatically checks for the winning condition. If all disks are stacked on the last peg in ascending order, you win!

## Technical Details:
- The game is implemented using Java programming language and Swing GUI library.
- Disks are represented as `JLabel` components with customizable colors.
- A stack-based data structure is used to manage disks on each peg.
- Recursion is employed to solve the Tower of Hanoi puzzle algorithmically.
- Input validation is performed to ensure valid user moves and disk placements.
- Timer-based animation is utilized to provide smooth disk movement.

## Credits:
- Developed by MD Rahman and William Ng.
- Inspired by the classic Tower of Hanoi puzzle.

## Feedback:
We welcome any feedback or suggestions for improving the game. 

[![email badge]](mailto:wng003@citymail.cuny.edu)

[email badge]: https://img.shields.io/badge/Email-D14836?style=flat-square&logo=gmail&logoColor=white
