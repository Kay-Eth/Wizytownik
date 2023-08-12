package main;

public class Main {
	
	public static void main(String[] args) {
		// Some useless code to prove that I can make generic methods in JAVA
		System.out.println(MaxFormArray(new Integer[] {1, 4, 5, 1, 11, 15, 0, 12}));
		System.out.println(MaxFormArray(new Float[] {1.0f, 4.2f, 5.3f, 1.12f, 11.23f, 15.12f, 12.02f}));
		System.out.println(MaxFormArray(new String[] {"Naleśnik", "Truskawka", "Timbuktu", "Kra krę mija", "Płaskoziemcy"}));

		new Application().Run();
	}

	public static <T extends Comparable<T>> T MaxFormArray(T[] array) {
		T max = array[0];

		for (int i = 1; i < array.length; i++) {
			if (array[i].compareTo(max) > 0) {
				max = array[i];
			}
		}

		return max;
	}
}
