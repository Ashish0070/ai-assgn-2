import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolver {
	

	public static int[][] getGrid(String filename) throws IOException{
		
		String str = "";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		    }
		    str = sb.toString();
		} finally {
		    br.close();
		}
		return gridBuilder(str);
	}
	

	public static int[][] gridBuilder(String str){
		int[][] grid = new int[9][9];
		char[] numArray = str.toCharArray();
		
		int row = 0;
		int col = 0;
		int j = 0;
		while(row <9 && col < 9){
			if(numArray[j]>= 48 && numArray[j]<=57){
				grid[row][col] = Character.getNumericValue(numArray[j]);
				if(col < 8){
					col ++;
				}else{
					col = 0;
					row ++;
				}
			}
			j++;
			
			
		}
		return grid;
	}
	
	/**
	 * main method
	 * use FC, MRV or BT to solve sudoku, calculate time and memory.
	 * @param args the first args is the filename, the second args is the method type
	 * 
	 * @throws IOException IOException will be thrown if the file cannot be found
	 */
	
	public static void main(String[] args) throws IOException {
		
		String filename = null;
		if(args.length > 0) 
			filename = args[0];
		
	
		int[][] grid = getGrid(filename);
		Solve myGrid = null;
		long totalStartTime = System.nanoTime();
		if(args.length == 1){
			myGrid = new SimpleSolve(grid);
		}else if (args.length >=2){
			if(args[1].equals("MRV")){
				myGrid = new MRVSolve(grid);
			}else if(args[1].equals("FC")){
				myGrid = new fwdCheckingSolve(grid);
			}else if(args[1].equals("LCV")){
				myGrid = new LCVSolve(grid);
			}
		}
		
		//before
		System.out.println(myGrid.toString());
		if(args.length >= 2){
			System.out.println("Method: " + args[1]);
		}else if (args.length == 1){
			System.out.println("Method: BT" );
		}
		
		//start solving
		long startTime = System.nanoTime();
		boolean solved = myGrid.solve();
		long endTime = System.nanoTime();
		
		//print
		System.out.println("Solved: "+ solved);
		System.out.println(myGrid.toString());
		System.out.println("Total time duration: "+((endTime - totalStartTime)/(double)1000000));
		System.out.println("Search time duration: "+((endTime - startTime)/(double)1000000));
		
	}
}
