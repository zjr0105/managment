package com.manage.controller;


/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/3/27
 */
public class demo {


	public boolean isTureOrfalse(){


		return false;
	}

	public static void main(String[] args) {
		Boolean[][] str = new Boolean[2][2];
		str[0][0] = true;
		str[1][0] = true;
		str[0][1] = false;
		str[1][1] = false;

		for(int i=0; i<2; i++){
			for (int j=0; j<2; j++){
				if(str[i][j] == true){
					System.out.println("*");
				}
			}
		}
//		============================

		Boolean[][] str2 = new Boolean[2][2];
		str2[0][0] = true;
		str2[0][1] = true;
		str2[1][1] = true;
		str2[1][0] = false;

		for (int i = 0 ; i<2; i++){
			for (int j=0 ; j<2 ; j++){
				if(str2[i][j] == true){
					System.out.print("*");
				}

			}
		}

	}
}
