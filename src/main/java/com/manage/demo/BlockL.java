package com.manage.demo;

/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/4/7
 */
public class BlockL implements block {


	public boolean[][] online(){
		boolean[][] booArrays = new boolean[][]{{true,true,true},
				{true,false,false}};
		for (int i=0; i<2; i++){
			for (int j=0; j<3; j++){
				if (booArrays[i][j]) {
					System.out.print("*");
				}
			}
			System.out.println();
		}
		return booArrays;
	}

	public boolean[][] under(){
		boolean[][] booArrays = new boolean[][]{{true,false},
				{true,false},{true,true}};
		for (int i=0; i<3; i++){
			for (int j=0; j<2; j++){
				if (booArrays[i][j]) {
					System.out.print("*");
				}
			}
			System.out.println();
		}
		return booArrays;
	}


	public boolean[][] left() {
		boolean[][] booArrays = new boolean[][]{{true,true,false},
				{false,false,false}};
		for (int i=0; i<2; i++){
			for (int j=0; j<3; j++){
				if (booArrays[i][j]) {
					System.out.println("\t*");
				}
			}
			System.out.print("*\t");
		}
		return booArrays;
	}


	public boolean[][] right() {
		boolean[][] booArrays = new boolean[][]{{true,true,false},
				{false,false,false}};
		for (int i=0; i<2; i++){
			for (int j=0; j<3; j++){
				if (booArrays[i][j]) {
					System.out.print("*");
				}
			}
			System.out.println("\t*");
		}
		return booArrays;
	}



	public static void main(String[] args) {
		BlockL blockL = new BlockL();
		blockL.online();
		System.out.println("============================");
		blockL.under();
		System.out.println("============================");
		System.out.println();
		blockL.left();
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		blockL.right();
	}
}
