import java.io.IOException;
import java.util.Scanner;


public class EM_Driver {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		System.out.print("Enter the number of clusters: ");
		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();
		System.out.println("\n");		
		
		int k = Integer.parseInt(string);
		
		System.out.print("Enter the maximum number of iteration below which the convergence could be reached: ");
		Scanner new_scanner = new Scanner(System.in);
		String new_string = new_scanner.nextLine();
		System.out.println("\n");
		
		int n = Integer.parseInt(new_string);
		heuristic_selection(k,n);
		
		scanner.close();
		new_scanner.close();
	}
	
	public static void heuristic_selection(int k, int n) throws IOException{
		
		EM_DataNode datanode = new EM_DataNode();
		
		System.out.println("1. Randomized K-Mean Selection Heuristic");
		System.out.println("2. Randomized Class Distribution Heuristic");
		System.out.println("3. Randomized K-Mean Selection with Variance equal to 1");
		System.out.println("4. Randomized Class Distribution Heuristic with Variance equal to 1");
		System.out.println("5. Randomized Weight Distribution without E-Step");
		System.out.println("6. Exit");
		System.out.println("\n");
		System.out.print("Please enter any of the five above heuristics: ");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();
		System.out.println("");
		
		switch (Integer.parseInt(string)){
		
		case 1:			
			
			datanode = EM_Algorithm_Driver.FileReader(k, Integer.parseInt(string));
			Gaussian_Collection.probability_gaussian(datanode,k);
			last_class last = new last_class();
			
			for (int i = 0; i < n; i++){
				
				Gaussian_Collection.estimation_step(datanode, k);
				Gaussian_Collection.maximization_step(datanode, k);
				Gaussian_Collection.convergence(datanode, k, last);
				
				if ( last.bool == true){
					System.out.println("Number of iterations took for convergence: "+i);
					break;
				}
				
			}
			
			System.out.println("\n");
			heuristic_selection(k,n);
		
		case 2:
			
			datanode = EM_Algorithm_Driver.FileReader(k, Integer.parseInt(string));
			Gaussian_Collection.probability_gaussian(datanode,k);
			last_class last_two = new last_class();
			
			for (int i = 0; i < n; i++){
				
				Gaussian_Collection.estimation_step(datanode, k);
				Gaussian_Collection.maximization_step(datanode, k);
				Gaussian_Collection.convergence(datanode, k, last_two);
				
				if ( last_two.bool == true){
					System.out.println("Number of iterations took for convergence: "+i);
					break;
					
				}
				
			}
			
			
			System.out.println("\n");
			heuristic_selection(k,n);
			
		case 3:
			
			datanode = EM_Algorithm_Driver.FileReader(k, Integer.parseInt(string));
			Gaussian_Collection.probability_gaussian(datanode,k);
			last_class last_three = new last_class();
			
			for (int i = 0; i < n; i++){
				
				Gaussian_Collection.estimation_step(datanode, k);
				Gaussian_Collection.maximization_step(datanode, k);
				Gaussian_Collection.convergence(datanode, k, last_three);
				
				if ( last_three.bool == true){
					System.out.println("Number of iterations took for convergence: "+i);
					break;
					
				}
				
			}
			
			
			System.out.println("\n");
			heuristic_selection(k,n);
			
		case 4:
			
			datanode = EM_Algorithm_Driver.FileReader(k, Integer.parseInt(string));
			Gaussian_Collection.probability_gaussian(datanode,k);
			last_class last_four = new last_class();
			
			for (int i = 0; i < n; i++){
				
				Gaussian_Collection.estimation_step(datanode, k);
				Gaussian_Collection.maximization_step(datanode, k);
				Gaussian_Collection.convergence(datanode, k, last_four);
				
				if ( last_four.bool == true){
					System.out.println("Number of iterations took for convergence: "+i);
					break;
					
				}
				
			}
			
			
			System.out.println("\n");
			heuristic_selection(k,n);	
			
		case 5:
			
			datanode = EM_Algorithm_Driver.FileReader(k, Integer.parseInt(string));
			last_class last_five = new last_class();
			
			for (int i = 0; i < n; i++){
				
				if ( i != 0){
					Gaussian_Collection.estimation_step(datanode, k);
				}
				
				Gaussian_Collection.maximization_step(datanode, k);
				Gaussian_Collection.convergence(datanode, k, last_five);
				
				if ( last_five.bool == true){
					System.out.println("Number of iterations took for convergence: "+i);
					break;
					
				}
				
			}
			
			System.out.println("\n");
			heuristic_selection(k,n);
			
		case 6:
			System.exit(0);
		
		}
	}

}
