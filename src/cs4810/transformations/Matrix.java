package cs4810.transformations;

/**
 * Class representing a 3x3 matrix for the purpose of applying transformations to points.
 * @author carroll
 *
 */
public class Matrix {

	//represents a 3x3 matrix of [row][col] orientation
	public double[][] data = new double[3][3];

	/**
	 * Constructor for matrix objects.
	 */
	public Matrix(int nw, int n, int ne, int e, int m, int w, int sw, int s, int se) {
		data[0][0] = nw;
		data[0][1] = n;
		data[0][2] = ne;
		data[1][0] = e;
		data[1][1] = m;
		data[1][2] = w;
		data[2][0] = sw;
		data[2][1] = s;
		data[2][2] = se;
	}//constructor

	/**
	 * Empty constructor creates the identity matrix.
	 */
	public Matrix() {
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
	}//empty constructor
	
	/**
	 * Prints the data of the matrix in 3 rows.
	 */
	public void print() {
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				System.out.print(data[row][col] + " ");
			}//for col
			System.out.println();
		}//for row
	}//print
	
	/**
	 * Multiplies a matrix by the input matrix.
	 * @param m The matrix to multiply by.
	 */
	public void multiply(Matrix m){
		Matrix n = new Matrix();
		
		//make temp matrix to store resultant info
		n.data[0][0] = (data[0][0] * m.data[0][0]) + (data[0][1] * m.data[1][0]) + (data[0][2] * m.data[2][0]);
		n.data[0][1] = (data[0][0] * m.data[0][1]) + (data[0][1] * m.data[1][1]) + (data[0][2] * m.data[2][1]);
		n.data[0][2] = (data[0][0] * m.data[0][2]) + (data[0][1] * m.data[1][2]) + (data[0][2] * m.data[2][2]);
		n.data[1][0] = (data[1][0] * m.data[0][0]) + (data[1][1] * m.data[1][0]) + (data[1][2] * m.data[2][0]);
		n.data[1][1] = (data[1][0] * m.data[0][1]) + (data[1][1] * m.data[1][1]) + (data[1][2] * m.data[2][1]); 
		n.data[1][2] = (data[1][0] * m.data[0][2]) + (data[1][1] * m.data[1][2]) + (data[1][2] * m.data[2][2]); 
		n.data[2][0] = (data[2][0] * m.data[0][0]) + (data[2][1] * m.data[1][0]) + (data[2][2] * m.data[2][0]);
		n.data[2][1] = (data[2][0] * m.data[0][1]) + (data[2][1] * m.data[1][1]) + (data[2][2] * m.data[2][1]);
		n.data[2][2] = (data[2][0] * m.data[0][2]) + (data[2][1] * m.data[1][2]) + (data[2][2] * m.data[2][2]);		
		
		//transfer to current matrix
		data[0][0] = n.data[0][0];
		data[0][1] = n.data[0][1];
		data[0][2] = n.data[0][2];
		data[1][0] = n.data[1][0];
		data[1][1] = n.data[1][1];
		data[1][2] = n.data[1][2];
		data[2][0] = n.data[2][0];
		data[2][1] = n.data[2][1];
		data[2][2] = n.data[2][2];
	}//multiply
	
}//Matrix
