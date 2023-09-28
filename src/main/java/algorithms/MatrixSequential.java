package algorithms;

public class MatrixSequential extends Algorithm<int[][]> {

	private int a[][], b[][], mul[][];
	
	public MatrixSequential() {};
	
	@Override
	public void init(int size) {
		
		a = new int[size][size];
		b = new int[size][size];
		mul = new int[size][size];
		
		int i = size, j = size;
		
		/** Init matrix a with random ints */
        for(i=0;i<size;i++) {    
            for(j=0;j<size;j++) { 
                a[i][j] = i+j;  
            }    
        }    
    
        /** Init matrix b with random ints */
        for(i=0;i<size;i++) {    
            for(j=0;j<size;j++) {    
                b[i][j] = i+j;    
            }    
        }
	}

	@Override
	public int[][] calculate(int size) {
	
		int i = 0, j = 0, k = 0;
		
        for(i=0;i<size;i++) {    
            for(j=0;j<size;j++) {    
                for(k=0;k<size;k++) {   
                    mul[i][j] += a[i][k] * b[k][j];    
                }       
            }    
        }
      
        return mul;
	}

	@Override
	public void close() {}
}
