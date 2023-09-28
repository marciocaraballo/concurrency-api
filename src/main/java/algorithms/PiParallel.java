package algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Parallel version of pi calculation.
 * 
 * As there are a fixed number of threads that can be used,
 * each thread will take a portion of the calculation (some subset
 * of the steps) to execute. Afterwards, all the results will 
 * be added together to get the final pi value.
 * 
 * @author Marcio
 */
class PiCalculation implements Callable<Double> {
	private int start;
	private int end;
	private double step;
	
	public PiCalculation(int start, int end, double step) {
		this.start = start;
		this.end = end;
		this.step = step;
	}

	@Override
	public Double call() throws Exception {	
		
		double x = 0.0, sum = 0.0;
		
		for (int i = this.start; i<this.end; i++) {
			x=(i+0.5)*this.step;
			sum += 4.0/(1.0 + x*x);
		}
		
		return sum;
	}
}

public class PiParallel extends Algorithm<Double> {
	
	private ExecutorService pool;
	private int threadSize = 10;
	
	public PiParallel(int threadSize) {
		pool = Executors.newFixedThreadPool(threadSize);
	};

	@Override
	public Double calculate(int num_steps) {
		int i;
		double pi, sum=0.0, step;
		
		ArrayList<Future<Double>> futures = new ArrayList<Future<Double>>();
		
		/** 
		 * Each thread will run a subset of the calculations
		 */
		int stepsPerThread = num_steps / this.threadSize;
		
		step = 1.0/(double)num_steps;
		
		for (i = 0; i < this.threadSize; i++) {
			
			int start = stepsPerThread * i;
			int end = start + stepsPerThread;
			
			Future<Double> future = pool.submit(new PiCalculation(start, end, step));
			futures.add(future);
		}
		
		Iterator<Future<Double>> it = futures.iterator();
		
		while(it.hasNext()) {
			Future<Double> current = it.next();
			
			try {
				Double result = current.get();
				
				sum += result;
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pi = step * sum;
		
		return pi;
	}

	@Override
	public void init(int size) {}
	
	@Override
	public void close() {
		pool.shutdown();
	}
}
