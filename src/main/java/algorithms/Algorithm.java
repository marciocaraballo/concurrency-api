package algorithms;

/**
 * Base class that defines a template all algorithms can extend.
 * Provides a base template to calculate the avg execution time of 
 * all algorithms.
 * 
 * @author Marcio
 *
 * @param <T> return type
 */
public abstract class Algorithm<T> {

	public abstract T calculate(int size);
	
	public abstract void init(int size);
	
	public abstract void close();
	
	/** 
	 * Triggers an execution and calculates the time elapsed 
	 * Calculates an avg of execution time based on how many times
	 * the algorithm runs
	 */
	public double averageExecTime(int size, int repeats) {
		
		double startTime = 0.0, endTime = 0.0, total = 0.0;
		
		init(size);
		
		for (int i = 0; i < repeats; i++) {
			startTime = 0;
			endTime = 0;
			
			startTime = System.currentTimeMillis();
			
			calculate(size);
			
			endTime = System.currentTimeMillis();
			
			total += (endTime - startTime); 
			
		}
		
		close();
		
		return total / repeats;
	}
}
