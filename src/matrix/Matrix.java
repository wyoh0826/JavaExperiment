package matrix;

public class Matrix {

	private double[][] data;
	
	public Matrix(double[][] src){
		data = src;
	
		int l = 0;
		for(double[] row: src){
			if( l == 0 ) l = row.length;
			else{
				if(row.length != l){
					error("input illegal array");
					return;
				}
			}
		}
	}
	
	public Matrix add(Matrix x){
		if(x.data.length != data.length || x.data[0].length != data[0].length){
			error("illegal adding");
			return null;
		}  
		double[][] src = new double[data.length][data[0].length];
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				src[i][j] = data[i][j] + x.data[i][j];
			}
		}
		return new Matrix(src);
	}

	
	public Matrix sub(Matrix x){
		if(x.data.length != data.length || x.data[0].length != data[0].length){
			error("illegal subing");
			return null;
		}  
		double[][] src = new double[data.length][data[0].length];
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				src[i][j] = data[i][j] - x.data[i][j];
			}
		}
		return new Matrix(src);
	}
	
	public Matrix mul(Matrix x){
		if(data.length != x.data[0].length || data[0].length != x.data.length){
			error("illegal multiply");
			return null;
		}
		
		double[][] src = new double[data.length][x.data[0].length];
		for(int i = 0; i < src.length; i++){
			for(int j = 0; j < src[0].length ; j++){
				src[i][j] = 0.0;
				for(int n = 0; n < data[0].length && n < x.data.length; n++){
					src[i][j] += data[i][n] * x.data[n][j];
				}
			}
		}
		return new Matrix(src);
		
			
	}
	
	public void print(){
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				System.out.print(String.format("%5f",data[i][j])+" ");
			}
			System.out.println();
		}
	}
	
	private void error(String s){
		System.out.println("error: "+s);
	}
	
	public static void main(String args[]){
		double[][] src0 = { {2,3} , {1,4},{2,1} } ;
		double[][] src1 = { {3,1,2},{2,4,2} };
		
		Matrix a = new Matrix( src0 );
		Matrix b = new Matrix(src1);
		a.mul(b).print();
	}
}
