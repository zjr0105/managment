package com.manage.common.demo;

/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/4/7
 */
public class BlockUnder implements block {


	public void secondArray(int num1, int num2){
		Boolean[][] boArrays = new Boolean[num1][num2];
		boArrays[0][0] = true;
		boArrays[0][1] = true;
		boArrays[1][1] = true;
		boArrays[1][0] = false;

		for (int i=0 ; i<2; i++){
			for (int j=0; j<2; j++){
				if(boArrays[i][j] == true){
					System.out.println("\t*");
				}
			}
		}

		Boolean[][] booArrays = new Boolean[num1][num2];
		booArrays[0][0] = true;
		booArrays[0][1] = true;
		booArrays[1][1] = true;
		booArrays[1][0] = false;

		for (int i=0; i<2; i++){
			for (int j=0; j<2; j++){
				if (booArrays[i][j] == true) {
					System.out.print("*");
				}
			}
		}
	}

	public static void main(String[] args) {
		BlockUnder blocks = new BlockUnder();
		blocks.secondArray(2,2);

	}
}
