package xyz.jninja.ds.impl;
import java.util.EmptyStackException;
import java.util.StringTokenizer;


public class StackClient {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		/*		Stack stack = new Stack();
		System.out.println(stack.isEmpty());

		IntList lst = new IntList();
		lst.addFirst(3);
		lst.addFirst(2);
		lst.addFirst(1);
		lst.wrapTraverseList();

		for (Node node = lst.getHead(); node != null; node = node.getNext()) {
			stack.push(node);
		}

		System.out.println(stack.isEmpty());
		while (!stack.isEmpty()) {
			Node node = (Node)stack.pop();
			System.out.println(node);
		}*/
		String expr = "1 2 3 * +";
		double result = StackClient.evalPostfix(expr);
		System.out.println(result);

		/*		int[] arr = {-1,0,1,2,3,4,5};
		for (int i=0; i < arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.println();
		int[] rev = reverse(arr);
		for (int i=0; i < rev.length; i++) {
			System.out.print(arr[i]);
		}*/

	}

	private static int[] reverse(int[] arr) {
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < arr.length; i++) {
			stack.push(arr[i]);
		}

		for (int i = 0; i < arr.length; i++) {
			arr[i] = (Integer) stack.pop();
		}
		return arr;
	}

	private static double evalPostfix(String expr) throws RuntimeException {
		Stack<Double> stack = new Stack<Double>();
		StringTokenizer tokenizer = new StringTokenizer(expr, " +-*/%", true);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			char currentChar = token.charAt(0);
			if (currentChar == ' ') {
				continue;
			}
			try {
				if (Character.isDigit(currentChar)) {
					stack.push(Double.parseDouble(token));
				} else {
					double a = (Double) stack.pop();
					double b = (Double) stack.pop();
					double c = 0.0;
					switch (currentChar) {
					case '+' : c = a+b;break;
					case '-' : c = a-b;break;
					case '*' : c = a*b;break;
					case '/' : c = a/b;break;
					case '%' : c = a%b;break;
					default:break;
					}
					stack.push(c);
				}
			} catch (EmptyStackException e) {
				throw new RuntimeException("Incorrect PostFix Expression : " + expr);
			}
		}
		return (Double) stack.pop();
	}
}
