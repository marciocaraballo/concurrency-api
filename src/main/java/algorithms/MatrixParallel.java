package algorithms;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MatrixCalculation implements Callable<int[]> {
	private int start;
	private int size;
	private int a[][];
	private int b[][];
	private int mul[];
	
	public MatrixCalculation(int start, int size, int a[][], int b[][]) {
		this.start = start;
		this.size = size;
		this.a = a;
		this.b = b;
		this.mul = new int[this.size];
	}
	
	@Override
	public int[] call() throws Exception {
		/** Array is calculated for the given row */
        for(int j=0;j<this.size;j++) {    
            for(int k=0;k<this.size;k++) {   
                mul[j] += a[this.start][k] * b[k][j];    
            }       
        }    
        
		return mul;
	}
	
}

public class MatrixParallel extends Algorithm<int[][]> {

	private int a[][], b[][], mul[][];
	private ExecutorService pool;
	
	public MatrixParallel(int threadSize) {
		pool = Executors.newFixedThreadPool(threadSize);
	};
	
	@Override
	public void init(int size) {
		
		a = new int[size][size];
		b = new int[size][size];
		mul = new int[size][size];
		
		int i = 0, j = 0;
		
		//Init matrix a with random ints
        for(i=0;i<size;i++) {    
            for(j=0;j<size;j++) { 
                a[i][j] = i+j;  
            }    
        }    
    
        //Init matrix b with random ints
        for(i=0;i<size;i++) {    
            for(j=0;j<size;j++) {    
                b[i][j] = i+j;    
            }    
        }      
	}

	@Override
	public int[][] calculate(int size) {
	
		ArrayList<Future<int[]>> futures = new ArrayList<Future<int[]>>();
		/** 
		 * Each i row will run in a different Callable instance 
		 * Each Future instance will be pushed to an ArrayList in the order
		 * determined by the loop, so later the result matrix can be built
		 * */
        for(int i=0;i<size;i++) {    
        	Future<int[]> future = pool.submit(new MatrixCalculation(i, size, a, b));
			futures.add(future); 
        }
        
        ListIterator<Future<int[]>> it = futures.listIterator();
        
		while(it.hasNext()) {
			/** 
			 * The position is retrieved in order to populate the result matrix.
			 * Since the calculation is made by row, and each Future was pushed to the ArrayList,
			 * the order will be kept, so we can be sure that the first item in the iteration 
			 * corresponds to the first row (iteration order is guaranteed by using a listIterator) 
			 */
			int index = it.nextIndex();
			
			Future<int[]> current = it.next();
			
			try {
				int[] result = current.get();
				
				mul[index] = result;
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      
        return mul;
	}

	@Override
	public void close() {
		pool.shutdown();
	}
}
