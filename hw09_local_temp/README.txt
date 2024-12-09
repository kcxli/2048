=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 74550445
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D-Arrays: The 4x4 board for 2048 will be represented using a 2D array
  with 4 rows and 4 columns. All the possible numbers that can be displayed
  in each grid is either 0 (no number) or 2^n, with 1 < n < 11. Based on
  what direction the user chooses to slide the grids (up, down, left, or right),
  I can use 2D arrays to iterate through the grid and combine tiles when
  appropriate. The end states will also be based off this 2D array, as if
  the entire grid gets filled with no more possible moves (no more of the
  same number tile touching each other), the player will lose, and if they
  manage to make the 2048 block, they will win.

  2. File I/O: This game will depend on file input and output to save the
  status of the game. Players will be allowed to save their game and later
  reload into the same game they previously saved. When saving the game, the
  state of the 2D array will be outputted as a text file, and they can later
  input the same text file back in to resume the game when they paused it.

  3. JUnit testable component: In order to ensure all the tiles are moving
  correctly based on user input (tiles should only move in specified direction
  if there are two tiles of the same number touching one another or if there
  is an empty tile) and are combining correctly (only tiles with the same number
  should combine), etc., I will write tests.

  4. Collections: I plan on using a linked list that stores each move that the
  player does in the order they enact them. They way, there can be an undo
  button that will allow the player to undo their previous move and revert the
  state of the game to what it looked like previously.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  TwentyFortyEight is a class that creates 2048 boards to play on, basically
  2D 4x4 int arrays. There are many methods you can call on these objects,
  such as moving the tiles, etc. GameBoard creates an instance of this
  class and gives the user the ability to cause different parts of the game
  and user interface to run and react to their inputs (clicks and up/down/left/right).
  The RunTwentyFortyEight file contains all the JComponents to create the visual
  image of the game. I used the Model-View-Controller framework in my code.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I was unable to make collections work out (aka the undo button functionality)
  for a while, but later on realized it was because my objects were defined
  to be static, causing them to fail to change. I also originally had trouble
  making the tiles move and merge correctly, as my return type was a new 2D
  array, causing it to be very complex.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Because I am using the Model-View-Controller framework, there is good separation
  of functionality. GameBoard stores the model as a field and acts as both the
  controller (with a MouseListener) and the view (with its paintComponent method
  and the status JLabel). RunTwentyFortyEight initializes the view, implements a
  bit of controller functionality, and then instantiates a GameBoard. TwentyFortyEight
  is completely independent of the view and controller and gives control over all
  the functionality of the game. The private state is encapsulated because you can
  instantiate new TwentyFortyEight objects, which are private.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used
  while implementing your game.

  I took screenshots of the 2048 tiles to use from this site: https://2048game.com
