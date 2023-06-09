package edu.iastate.cs228.hw1;

/**
 *  
 * @author Chen Sang
 *
 */

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
public class Plain 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 *  // Assumption: The input file is in correct format. 
		// 
		// You may create the grid plain in the following steps: 
		// 
		// 1) Reads the first line to determine the width of the grid.
		// 
		// 2) Creates a grid object. 
		// 
		// 3) Fills in the grid according to the input file. 
		// 
		// Be sure to close the input file when you are done.
	 */
	public Plain(String inputFileName) throws FileNotFoundException
	{		
		Scanner sc = new Scanner(new File(inputFileName));
		width      = sc.nextLine().length()/3;       // the width of the plain
		grid       = new Living [width][width];    // create grid object
		
		Scanner sc2 = new Scanner(new File(inputFileName));  
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < width; col++){
				String name = sc2.next();
				char   type = name.charAt(0);
				int    age  = 0;
				
				if(name.length() > 1){
					age = Integer.parseInt(name.substring(1));  //
				}
				
				if(type == 'B'){
					grid[row][col] = new Badger(this,row,col,age); 
				}
				else if(type == 'E'){
					grid[row][col] = new Empty(this,row,col);
				}
				else if (type == 'F'){
					grid[row][col] = new Fox(this,row,col,age);
				}
				else if (type == 'G'){
					grid[row][col] = new Grass(this,row,col);
				}
				else if (type == 'R'){
					grid[row][col] = new Rabbit(this,row,col,age);
				}
			}
		}
		sc2.close();
		sc.close();
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing it. 
	 * @param width  the grid 
	 */
	public Plain(int w)
	{
		width = w;
		grid = new Living[width][width];
	}
	
	
	public int getWidth()
	{
		return width;  
	}
	
	/**
	 * Initialize the plain by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		Random generator = new Random(); 
		for(int row = 0; row < width; row++)
		{
			for (int col = 0; col < width; col++)
			{
				int animal = generator.nextInt(5);

				if(animal == 0)      // badger with 0 age
				{
					grid[row][col] = new Badger(this,row,col,0);
				}
				else if(animal == 1) // Empty
				{
					grid[row][col] = new Empty(this,row,col);
				}
				else if (animal == 2) //Fox with 0 age
				{
					grid[row][col] = new Fox(this,row,col,0);
				}
				else if(animal == 3) //grass
				{
					grid[row][col] = new Grass(this,row,col);
				}
				else if(animal == 4) // rabbit with age 0
				{
					grid[row][col] = new Rabbit(this,row,col,0);
				}
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		String name = "";
		for (int row = 0; row < width; row++)
		{
			for(int col = 0; col < width; col++)
			{
				if(grid[row][col].who() == State.BADGER)
				{
					name += ("B" + ((Animal) grid[row][col]).myAge() + " ");
				}
				else if(grid[row][col].who() == State.EMPTY)
				{
					name += ("E " + " ");
				}
				else if(grid[row][col].who() == State.FOX)
				{
					name += ("F" + ((Animal)grid[row][col]).myAge() + " ");
				}
				else if(grid[row][col].who() == State.GRASS)
				{
					name += ("G " + " ");
				}
				else if(grid[row][col].who() == State.RABBIT)
				{
					name += ("R" + ((Animal)grid[row][col]).myAge() + " ");
				}
			}
			name += "\n";
		}
		return name;  
	}
	

	/**
	 * Write the plain grid to an output file.  Also useful for saving a randomly 
	 * generated plain for debugging purpose. 
	 * // 1. Open the file. 
		// 
		// 2. Write to the file. The five life forms are represented by characters 
		//    B, E, F, G, R. Leave one blank space in between. Examples are given in
		//    the project description. 
		// 
		// 3. Close the file. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		File f = new File(outputFileName);
		PrintWriter pw = new PrintWriter(f);
		pw.print(this.toString());
		pw.close();
	}			
}
