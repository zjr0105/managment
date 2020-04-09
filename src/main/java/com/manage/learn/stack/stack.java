package com.manage.learn.stack;

import java.util.Stack;

/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/4/7
 */
public class stack {


	public void check(String str) {
		Stack<Character> stack = new Stack<>();

		//如果当前String长度为奇数， 则不匹配
		if(str.length() % 2 ==1 ) {
			throw new RuntimeException("当前字符长度是为奇数");
		} else {
			stack = new Stack<>();
			for (int i = 0; i<str.length(); i++){
				//如果栈是为空的，则就插入一条
				if(stack.isEmpty()){
					stack.push(str.charAt(i));
				} else if ((stack.peek() == '{' && str.charAt(i) == '}') ||
						(stack.peek() == '(' && str.charAt(i) == ')') ||
						(stack.peek() == '[' && stack.peek() == ']')) {
					//如果满足以上两个条件，表示相邻的两个字符是一对匹配的括号， 则可以出栈了
					stack.pop();
				} else {
					stack.push(str.charAt(i));
				}
			}
			if(stack.isEmpty()){
				System.out.println("已全部匹配完成");
			}else{
				System.out.println("未能全部匹配完成");
			}

		}

	}

	public static void main(String[] args) {
		stack stack = new stack();
		stack.check("{}");
		System.out.println(stack);
	}

}
