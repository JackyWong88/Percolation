/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 *
 * @author Jacky
 */
public class Percolation {
    private boolean percolates = false;
    private final boolean[] map;
    private final WeightedQuickUnionUF tree;
    private final WeightedQuickUnionUF tree2;
    private final int n;
    
    //create N-by-N grid, with all sites blocked
    //create extra 2 spaces for the ends and open the sites at both ends
    public Percolation(int N){
        if(N <= 0) {
            throw new IllegalArgumentException();
        }
        n = N;
        map = new boolean[N*N+1];
        tree = new WeightedQuickUnionUF(N*N+2);
        tree2 = new WeightedQuickUnionUF(N*N+1);
    }
    
    private int rcToIdx(int i, int j){
        return (i-1)*n+j;
    }
    
    private void validate(int i, int j){
        if(i < 1 || i > n)
            throw new IndexOutOfBoundsException("index i " + i + " is not between 0 and " + (n));
        if(j < 1 || j > n)
            throw new IndexOutOfBoundsException("index j " + j + " is not between 0 and " + (n));
    }
    
    //open site (row i, column j) if it is not open already
    public void open(int i, int j){
        validate(i,j);
        if(isOpen(i,j)) return;
        int idx = rcToIdx(i,j);
        map[idx] = true;
        if(j != 1){
            if(isOpen(i,j-1)) {  //left side
                tree.union(idx,idx-1);
                tree2.union(idx,idx-1);
            }
        }
        if(j != n){
            if(isOpen(i,j+1)) {  //right side
                tree.union(idx,idx+1);
                tree2.union(idx,idx+1);
            }
        }
        if (i != n){
            if(isOpen(i+1,j)) {  //bottom side
                tree.union(idx,idx+n);
                tree2.union(idx,idx+n);
            }
        }else{
            tree.union(idx, n*n+1);
        }
        if(i == 1){
            tree.union(0,idx);
            tree2.union(0,idx);
        }else{
            if(isOpen(i-1,j)) {  //top side
                tree.union(idx,idx-n);
                tree2.union(idx,idx-n);
            }
        }
    }
    
    //is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i,j);
        int idx = rcToIdx(i,j);
        return map[idx];
    }
    
    //is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i,j);
        if(!isOpen(i,j)) return false;
        int idx = rcToIdx(i,j);
        return tree2.connected(0,idx);
    }
    
    //does the system percolate?
    public boolean percolates() {
        if (!percolates) {
            if(tree.connected(0,n*n+1))
                percolates = true;
        }
        return percolates;
    }
}
