package algorithms;

public class PiSequential extends Algorithm<Double> {
	
	public PiSequential() {};
	
	@Override
	public Double calculate(int num_steps) {
	
		int i;
		double pi, x, sum=0.0, step;
		
		step = 1.0/(double)num_steps;
		
		for (i = 0; i < num_steps; i++) {
			x=(i+0.5)*step;
			sum = sum + 4.0/(1.0 + x*x);
		}
		
		pi = step * sum;
		
		return pi;
	}

	@Override
	public void init(int size) {}

	@Override
	public void close() {}
}
