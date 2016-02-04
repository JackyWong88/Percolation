/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author Jacky
 */
public class PercolationStats {
    private double[] x;
    private int t;
    
    //perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T){
        if(N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        x = new double[T];
        t = T;
        for(int i = 0; i < T; i++){
            Percolation perc = new Percolation(N);
            x[i] = runExp(perc,N)/(N*N);
        }
    }
    
    private double runExp(Percolation perc, int N){
        double count = 0;
        while(!perc.percolates()){
            int r = StdRandom.uniform(1,N+1);
            int c = StdRandom.uniform(1,N+1);
            if(!perc.isOpen(r,c)){
                perc.open(r, c);
                count++;
            }
        }
        return count;
    }
    
    //sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(x);
    }
    
    //sample standard deviation of percolation threshold
    public double stddev(){
        if(t == 1)
            return Double.NaN;
        return StdStats.stddev(x);
    }
    
    //low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()-(1.96*stddev()/Math.sqrt(t));
    }
    
    //high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+(1.96*stddev()/Math.sqrt(t));
    }
    
    public static void main(String[] args){
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats stats = new PercolationStats(N,T);
        StdOut.println("mean = " +  stats.mean());
        StdOut.println("stddev = " +  stats.stddev());
        StdOut.println("95% confidence interval = " +  stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
