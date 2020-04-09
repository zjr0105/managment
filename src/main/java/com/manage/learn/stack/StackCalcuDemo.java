package com.manage.controller.stack;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/4/8
 */
public class StackCalcuDemo {


	public static void main(String[] args) {
		String str = "7000+6000";
		System.out.println(calcu(str));
	}

	/**
	 * 计算逻辑
	 * todo 创建两个 (一个存放符号栈， 一个存放计算的值)
	 * todo 1.中缀转后缀
	 * todo   数字直接输出到后缀表达式
	 * todo   栈为空时，遇到运算符，直接入栈
	 * todo   遇到运算符，弹出所有优先级大于或等于该运算符的栈顶元素，并将该运算符入栈
	 * todo   将栈中元素一次出栈
	 * todo 2. 计算后缀
	 * todo    遇到数字，入栈
	 * todo    遇到运算符，弹出栈顶两个元素，做运算，并将结果入栈
	 * todo    重复上述步骤，直到表达式最右端
	 */

	public static int calcu (String str) {

		//todo 存放符号栈
		Stack<String> operStack = new Stack<>();

		LinkedList<String> operList = new LinkedList<>();
		for (int i=0; i<str.length(); i++) {
			//todo 遇到了数字
			if(Character.isDigit(str.charAt(i))) {
				int j = i + 1;
				for (; j < str.length() && Character.isDigit(str.charAt(j)); j++) {

				}
				operList.add(str.substring(i,j));
				i = j - 1;
				continue;
			}

			//遇到了乘除运算符
			if (str.charAt(i) == '/' || str.charAt(i) == '*') {
				while (!operStack.isEmpty() && (operStack.lastElement().equals("/")) || operStack.lastElement().equals("*")) {
					operList.add(operStack.pop());
				}
				operStack.add(String.valueOf(str.charAt(i)));
				continue;
			}

			//遇到了加减运算符
			if (str.charAt(i) == '+' || str.charAt(i) == '-') {
				while (!operStack.isEmpty() && !isNumeric(operStack.lastElement())) {
					operList.add(operStack.pop());
				}
				operStack.add(String.valueOf(str.charAt(i)));
				continue;
			}

		}

		//弹出栈内所有元素到表达式
		while (operStack.size() > 0) {
			operList.add(operStack.pop());
		}

		//todo 存放计算的值
		Stack<Integer> numStack = new Stack<>();
		for (String c : operList) {
			if(isNumeric(c)) {
				numStack.push(Integer.valueOf(c));
				continue;
			} else {
				int a = numStack.pop();
				int b = numStack.pop();
				cal(numStack, c, a, b);

			}

		}

		return numStack.pop();

	}


	/**
	 * 计算
	 * @param numStack
	 * @param c
	 * @param a
	 * @param b
	 */
	private static void cal(Stack<Integer> numStack, String c, int a, int b) {
		switch (c.toCharArray()[0]) {
			case '+': numStack.push(b + a);
				break;
			case '-': numStack.push(b - a);
				break;
			case '*': numStack.push(b * a);
				break;
			case '/': numStack.push(b / a);
				break;
		}
	}


	public static boolean isNumeric (String str) {
		for (int i=0; i< str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}



}
