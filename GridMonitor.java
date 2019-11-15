import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Contains constructors and methods to initialise the various grids and find
 * the bugs in the system
 * 
 * @author Rohit
 *
 */
public class GridMonitor implements GridMonitorInterface {

	private File file;
	private double[][] grid;
	private double[][] deltaGrid;
	private double[][] surroundingSumGrid;
	private double[][] surroundingAvgGrid;
	private boolean[][] dangerGrid;

	/**
	 * constructor to initialise and construct the various grids needed to debug the
	 * problem
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public GridMonitor(String filename) throws FileNotFoundException {

		File myFile = new File(filename);

		Scanner sc = new Scanner(myFile);

		Scanner scan = new Scanner(myFile);

		int[] num = new int[2];

		for (int i = 0; i < 2; i++) 
		{
			num[i] = Integer.parseInt(scan.next());

		}

		grid = new double[num[0]][num[1]];
		int row = grid.length;
		int col = grid[0].length;
		surroundingSumGrid = new double[row][col];
		surroundingAvgGrid = new double[row][col];
		deltaGrid = new double[row][col];
		dangerGrid = new boolean[row][col];

		for (int i = 0; i < grid.length; i++) 
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				double val = Double.parseDouble(scan.next());
				grid[i][j] = val;
			}
		}

		sc.close();
		scan.close();
		for (int i = 0; i < surroundingSumGrid.length; i++) 
		{
			for (int j = 0; j < surroundingSumGrid[i].length; j++)
			{
				if (surroundingSumGrid.length == 1)
					surroundingSumGrid[i][j] = 4 * grid[i][j];
				else
				{
					if (i == 0 && j == 0) // 1
						surroundingSumGrid[i][j] = grid[i][j] + grid[i][j] + grid[i][j + 1] + grid[i + 1][j];

					if (i == 0 && j == surroundingSumGrid[i].length - 1) // 2
						surroundingSumGrid[i][j] = grid[i][j - 1] + grid[i + 1][j] + (2 * grid[i][j]);

					if (i == surroundingSumGrid.length - 1 && j == 0) // 3
						surroundingSumGrid[i][j] = grid[i][j] + grid[i][j] + grid[i][j + 1] + grid[i - 1][j];

					if (i == surroundingSumGrid.length - 1 && j == surroundingSumGrid[i].length - 1) // 4
						surroundingSumGrid[i][j] = grid[i][j - 1] + grid[i - 1][j] + (2 * grid[i][j]);

					if (j == 0 && i != 0 && i != surroundingSumGrid.length - 1) // 5
						surroundingSumGrid[i][j] = grid[i - 1][j] + grid[i][j] + grid[i][j + 1] + grid[i + 1][j];

					if (j == surroundingSumGrid[i].length - 1 && i != 0 && i != surroundingSumGrid.length - 1) // 6
						surroundingSumGrid[i][j] = grid[i][j] + grid[i][j - 1] + grid[i - 1][j] + grid[i + 1][j];

					if (i == 0 && j != 0 && j != surroundingSumGrid[i].length - 1)// 7
						surroundingSumGrid[i][j] = grid[i][j] + grid[i][j + 1] + grid[i + 1][j] + grid[i][j - 1];

					if (i == surroundingSumGrid.length - 1 && j != 0 && j != surroundingSumGrid[i].length - 1)
						surroundingSumGrid[i][j] = grid[i][j] + grid[i - 1][j] + grid[i][j + 1] + grid[i][j - 1];

					if (i != 0 && i != surroundingSumGrid.length - 1 && j != 0 && j != surroundingSumGrid[i].length - 1)
						surroundingSumGrid[i][j] = grid[i][j - 1] + grid[i - 1][j] + grid[i][j + 1] + grid[i + 1][j];
				}
			}
		}

		for (int i = 0; i < surroundingAvgGrid.length; i++) 
		{
			for (int j = 0; j < surroundingAvgGrid[i].length; j++)
			{
				surroundingAvgGrid[i][j] = surroundingSumGrid[i][j] / 4.0;
			}
		}

		for (int i = 0; i < deltaGrid.length; i++) 
		{
			for (int j = 0; j < deltaGrid[i].length; j++) 
			{
				deltaGrid[i][j] = (Math.abs(surroundingAvgGrid[i][j])) * 0.5;
			}
		}

		for (int i = 0; i < deltaGrid.length; i++)
		{
			for (int j = 0; j < deltaGrid[i].length; j++) 
			{
				if ((((Math.abs(surroundingAvgGrid[i][j])) + deltaGrid[i][j]) >= Math.abs(grid[i][j])
						&& deltaGrid[i][j] <= Math.abs(grid[i][j])))
					dangerGrid[i][j] = false;
				else
					dangerGrid[i][j] = true;
			}
		}

	}

	/**
	 * returns the copy of the base grid
	 * 
	 * @return baseGridCopy
	 */
	public double[][] getBaseGrid() {
		// TODO Auto-generated method stub
		double[][] baseGridCopy;
		baseGridCopy = new double[dangerGrid.length][dangerGrid[0].length];
		for (int i = 0; i < deltaGrid.length; i++) 
		{
			for (int j = 0; j < deltaGrid[i].length; j++) 
			{
				baseGridCopy[i][j] = grid[i][j];
			}

		}
		return baseGridCopy;
	}

	@Override
	/**
	 * returns the copy of the surrounding sum grid
	 * 
	 * @return sumGridCopy
	 */
	public double[][] getSurroundingSumGrid() {
		// TODO Auto-generated method stub
		double[][] sumGridCopy;
		sumGridCopy = new double[dangerGrid.length][dangerGrid[0].length];
		for (int i = 0; i < deltaGrid.length; i++)
		{
			for (int j = 0; j < deltaGrid[i].length; j++)
			{
				sumGridCopy[i][j] = surroundingSumGrid[i][j];
			}

		}
		return sumGridCopy;
	}

	@Override
	/**
	 * returns the copy of the surrounding average grid
	 * 
	 * @return sumAvgCopy
	 */
	public double[][] getSurroundingAvgGrid() {
		// TODO Auto-generated method stub
		double[][] sumAvgCopy;
		sumAvgCopy = new double[dangerGrid.length][dangerGrid[0].length];
		for (int i = 0; i < deltaGrid.length; i++) 
		{
			for (int j = 0; j < deltaGrid[i].length; j++) 
			{
				sumAvgCopy[i][j] = surroundingAvgGrid[i][j];
			}

		}
		return sumAvgCopy;
	}

	@Override
	/**
	 * returns the copy of the delta grid
	 * 
	 * @return sumDeltaCopy
	 */
	public double[][] getDeltaGrid() {
		// TODO Auto-generated method stub
		double[][] sumDeltaCopy;
		sumDeltaCopy = new double[dangerGrid.length][dangerGrid[0].length];
		for (int i = 0; i < deltaGrid.length; i++)
		{
			for (int j = 0; j < deltaGrid[i].length; j++) 
			{
				sumDeltaCopy[i][j] = deltaGrid[i][j];
			}
		}
		return sumDeltaCopy;
	}

	@Override
	/**
	 * returns the copy of the danger grid
	 * 
	 * @return sumDangerCopy
	 */
	public boolean[][] getDangerGrid() {
		// TODO Auto-generated method stub
		boolean[][] sumDangerCopy;
		sumDangerCopy = new boolean[dangerGrid.length][dangerGrid[0].length];
		for (int i = 0; i < deltaGrid.length; i++)
		{
			for (int j = 0; j < deltaGrid[i].length; j++) 
			{
				sumDangerCopy[i][j] = dangerGrid[i][j];
			}

		}
		return sumDangerCopy;

	}

	/**
	 * overrides the inherited toString method and returns the base grid in String
	 * format
	 * 
	 * @return str
	 */
	public String toString() {
		String str = grid.toString();
		return str;
	}
}
