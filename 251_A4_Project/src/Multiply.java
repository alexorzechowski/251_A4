import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
    	if(size==1){	//Base Case: Trivial multiplication of 2 bits
    		int[] result={x*y, 1};
    		return  result;
    	}
    	//Find split point
    	int m= (int) Math.ceil(size/2);
    	int a =	x>>m; //Shift to the right by m (Move binary point left)
    	int b = x&((1<<m)-1); //Get x mod 2^m
    	
    	int c=y>>m;
    	int d=y&((1<<m)-1);
    	
    	int[] e=naive(m,a,c);
    	int[] f=naive(m,b,d);
    	int[] g=naive(m,b,c);
    	int[] h=naive(m,a,d);
        
    	int[] result ={ (e[0]<<(m<<1)) + ((g[0]+h[0])<<m) +f[0], 3*m + e[1]+f[1]+g[1]+h[1]};
        return result;
        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        // YOUR CODE GOES HERE  (Note: Change return statement)
    	if(size==1){
    		int[] result ={x*y,1};
    		return result;
    	}
        int m = (int) Math.ceil(size/2);
        int a =	x>>m; //Shift to the right by m (Move binary point left)
    	int b = x&((1<<m)-1); //Get x mod 2^m
    	int c=y>>m;
    	int d=y&((1<<m)-1);
    	int[] e=naive(m,a,c);
    	int[] f=naive(m,b,d);
    	int[] g=naive(m,a-b,c-d);
    	
    	int[] result = { (e[0]<<(m<<1)) + ((e[0]+f[0]-g[0])<<m)+f[0], 6*m+e[1]+f[1]+g[1]};
        return result;
        
    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
