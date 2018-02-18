/*
Created by: Khayyam Akhtar
Dated: 17-02-18

Updated by:
---
---

Last updated:
00-00-00
*/

import java.util.*;

public class Sudoku {
	int[][] mainGrid; //for STORING and DISPLAYING main grid values
	int randomNum;	//random number that will be placed in grid
	int randomRow , randomCol;	//random coordinates
	Random rGen = new Random();	//Random number generator object
	int[] fixedPlaces;	//Array to store coordinates of places that cannot be changed by the user
	int rowcoCheck , colcoCheck;	//Coordinates to check individual values in blocks
	int userInput; //value added by user
	int userRow , userCol; //Row and Column coordinates selected by user
	int totalNums; //number of digits in grid, if it reaches 81 then the user has solved the puzzle
	int preNum = 20; //numbers to make predefined

	void InitGrid() {
		for (int indexRow = 0 ; indexRow < 9 ; indexRow = indexRow + 1) {
			for (int indexCol = 0 ; indexCol < 9 ; indexCol = indexCol + 1) {
				mainGrid[indexRow][indexCol] = 0;
			}
		}
	}

	void GameEnd() {
		int gridNum; //Number in grid at Row and Coloumn
		totalNums = 0;
		for (int indexRow = 0 ; indexRow < 9 ; indexRow = indexRow + 1) {
			for (int indexCol = 0 ; indexCol < 9 ; indexCol = indexCol + 1) {
				gridNum = mainGrid[indexRow][indexCol];
				if (gridNum != 0) {
					totalNums = totalNums + 1;
				}
			}
		}
	}

	void RandomStart() {
		int counter = 0;	//total number of random values to add
		int fpCounter = 0;	//FixedPlaces array counter
		boolean numAdd , regenNum , rowPassed , colPassed;
		//numAdd: whether to add new number to grid or not
		//regenNum: whether to regenerate a new random number or not
		//rowPassed and colPassed are to check whether the new random number fulfills all the game rules before being added
		int preGridNum; //number on grid previously read
		while (counter < preNum) {
			randomRow = rGen.nextInt(9);
			randomCol = rGen.nextInt(9);
			numAdd = false;
			regenNum = true;
			while (numAdd == false) {
				colPassed = true;
				rowPassed = true;
				if (regenNum == true) {
					randomNum = rGen.nextInt(9) + 1;
				}
				for (int indexCol = 0 ; indexCol < 9 ; indexCol = indexCol + 1) {
					preGridNum = mainGrid[randomRow][indexCol];
					if (preGridNum == randomNum) {
						colPassed = false;
					}
				}
				for (int indexRow = 0 ; indexRow < 9 ; indexRow = indexRow + 1) {
					preGridNum = mainGrid[indexRow][randomCol];
					if (preGridNum == randomNum) {
						rowPassed = false;
					}
				}
				if (randomRow <= 2) {
					rowcoCheck = 0;
				} else {
					if (randomRow <= 5) {
						rowcoCheck = 3;
					} else {
						rowcoCheck = 6;
					}
				}
				if (randomCol <= 2) {
					colcoCheck = 0;
				} else {
					if (randomCol <= 5) {
						colcoCheck = 3;
					} else {
						colcoCheck = 6;
					}
				}
				for (int indexRow = rowcoCheck ; indexRow <= rowcoCheck + 2 ; indexRow = indexRow + 1) {
					for (int indexCol = colcoCheck ; indexCol <= colcoCheck + 2 ; indexCol = indexCol + 1) {
						if (indexRow != randomRow && indexCol != randomCol) {
							preGridNum = mainGrid[indexRow][indexCol];
							if (randomNum == preGridNum) {
								colPassed = false;
								rowPassed = false;
							}
						}
					}
				}
				if (colPassed == true && rowPassed == true) {
					regenNum = false;
					numAdd = true;
				} else {
					regenNum = true;
				}
			}
			mainGrid[randomRow][randomCol] = randomNum;
			fixedPlaces[fpCounter] = randomRow;
			fixedPlaces[fpCounter + 1] = randomCol;
			fpCounter = fpCounter + 2;
			counter = counter + 1;
		}
	}

	void UserAdding() {
		boolean numAdd , rowPassed , colPassed , blockPassed , preGenHit;
		//numAdd: whether to add user number to grid or not
		//rowPassed and colPassed are to check whether the user number fulfills all the game rules before being added
		//blockPassed is to check whether the user number fulfills all the game rules of individual blocks
		//preGenHit is to check whether the user tried to change the pre-generated number
		int preGridNum; //number on grid previously read
		numAdd = false;
		preGenHit = false;
		userRow = userRow - 1;
		userCol = userCol - 1;
		int fixRow , fixCol; //Places that cannot be changed by user
		for (int index = 0 ; index < 18 ; index = index + 2) {
			fixRow = fixedPlaces[index];
			fixCol = fixedPlaces[index + 1];
			if (userRow == fixRow && userCol == fixCol) {
				System.out.println("This is a pre-generated number. It cannot be changed.");
				preGenHit = true;
			}
		}
		colPassed = true;
		rowPassed = true;
		blockPassed = true;
		for (int indexCol = 0 ; indexCol < 9 ; indexCol = indexCol + 1) {
			preGridNum = mainGrid[userRow][indexCol];
			if (preGridNum == userInput && userInput != 0) {
				colPassed = false;
			}
		}
		for (int indexRow = 0 ; indexRow < 9 ; indexRow = indexRow + 1) {
			preGridNum = mainGrid[indexRow][userCol];
			if (preGridNum == userInput && userInput != 0) {
				rowPassed = false;
			}
		}
		if (userRow <= 2) {
			rowcoCheck = 0;
		} else {
			if (userRow <= 5) {
				rowcoCheck = 3;
			} else {
				rowcoCheck = 6;
			}
		}
		if (userCol <= 2) {
			colcoCheck = 0;
		} else {
			if (userCol <= 5) {
				colcoCheck = 3;
			} else {
				colcoCheck = 6;
			}
		}
		for (int indexRow = rowcoCheck ; indexRow <= rowcoCheck + 2 ; indexRow = indexRow + 1) {
			for (int indexCol = colcoCheck ; indexCol <= colcoCheck + 2 ; indexCol = indexCol + 1) {
				if (indexRow != userRow && indexCol != userCol) {
					preGridNum = mainGrid[indexRow][indexCol];
					if (userInput == preGridNum && userInput != 0) {
						blockPassed = false;
					}
				}
			}
		}
		if (colPassed == true && rowPassed == true && blockPassed == true) {
			numAdd = true;
		} else {
			if (colPassed == false && preGenHit == false) {
				System.out.println("Same number " + userInput +" exists in row " + (userRow + 1) + ".");
			}
			if (rowPassed == false && preGenHit == false) {
				System.out.println("Same number " + userInput + " exists in column " + (userCol + 1) + ".");
			}
			if (blockPassed == false && preGenHit == false) {
				System.out.println("Same number " + userInput + " exists in the block.");
			}
		}
		if (numAdd == true) {
			mainGrid[userRow][userCol] = userInput;
		}
	}

	void DisplayGrid() {
		int colCount = 1;
		int rowCount = 0;
		//After every 3 counts, block line has to be added
		//Runtime.getRuntime().exec("clear");
		System.out.println("Sudoku Grid:");
		System.out.print("    ");
		for (int index = 0 ; index < 9 ; index = index + 1) {
			System.out.print((index + 1) + "     ");
			if (index == 2 || index == 5) {
				System.out.print(" ");
			}
		}
		System.out.println();
		for (int indexRow = 0 ; indexRow < 9 ; indexRow = indexRow + 1) {
			if (rowCount == 3) {
				for (int indexTemp = 0 ; indexTemp < 58 ; indexTemp = indexTemp + 1) {
					System.out.print("-");
				}
				System.out.println();
				rowCount = 1;
			} else {
				rowCount = rowCount + 1;
			}
			for (int indexCol = 0 ; indexCol < 9 ; indexCol = indexCol + 1) {
				if (mainGrid[indexRow][indexCol] != 0) {
					if (indexCol == 0) {
						//System.out.print((indexRow + 1) + " [ " + mainGrid[indexRow][indexCol] + " ]");
						System.out.print((indexRow + 1) + " - " + mainGrid[indexRow][indexCol] + " -");
					} else {
						//System.out.print(" [ " + mainGrid[indexRow][indexCol] + " ]");
						System.out.print(" - " + mainGrid[indexRow][indexCol] + " -");
					}
					/*if (colCount == 3) {
						System.out.print("|");
						colCount = 0;
					} else {
						colCount = colCount + 1;
					}*/
				} else {
					if (indexCol == 0) {
						//System.out.print((indexRow + 1) + " [   ]");
						System.out.print((indexRow + 1) + " -   -");
					} else {
						//System.out.print(" [   ]");
						System.out.print(" -   -");
					}
				}
				if (colCount == 3) {
					System.out.print("|");
					colCount = 1;
				} else {
					colCount = colCount + 1;
				}
			}
			System.out.println();
		}
	}

	public static void main(String s[]) {
		Sudoku newGame = new Sudoku();
		Scanner input = new Scanner(System.in);
		boolean gameEnded = false;

		newGame.mainGrid = new int[9][9];
		newGame.fixedPlaces = new int[2 * newGame.preNum];

		newGame.InitGrid();
		newGame.RandomStart();


		while (gameEnded != true) {
			newGame.DisplayGrid();
			System.out.println("Enter row number");
			newGame.userRow = input.nextInt();
			System.out.println("Enter column number");
			newGame.userCol = input.nextInt();
			System.out.println("Enter number to add, 1-9. (0 to reset)");
			newGame.userInput = input.nextInt();
			if (newGame.userInput >= 0 && newGame.userInput < 10) {
				newGame.UserAdding();
			} else {
				System.out.println("Entered number is out of range.");
			}
			newGame.GameEnd();
			if (newGame.totalNums == 81) {
				System.out.println("Puzzle has been solved!");
				gameEnded = true;
			}
		}
	}

}
