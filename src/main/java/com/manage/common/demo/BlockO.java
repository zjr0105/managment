package com.manage.common.demo;

/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/4/7
 */
public class BlockO implements block {

	public static void main(String[] args) {

		boolean[][] bos = new boolean[][]{{true,true},{true,true}};

		for (int i=0; i<2; i++){
			for (int j=0; j<2; j++) {
				if (bos[i][j]) {
					System.out.print("*");
				}
			}
			System.out.println();
		}

	}
}
