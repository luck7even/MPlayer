package com.MP3Player;

import java.util.ArrayList;

public class RandomNumber {
	/**
	 * @uml.property  name="randomNumbers"
	 */
	int randomNumbers;
	/**
	 * @uml.property  name="i"
	 */
	int i = 0;
	static ArrayList<Integer> numbers;
	static RandomNumber instance;

	private RandomNumber(int randomNumbers) {
		this.randomNumbers = randomNumbers;
	}

	/*SingleTon패턴적용*/
	public static RandomNumber getInstance(int randomNumbers) {
		if (instance == null) {
			instance = new RandomNumber(randomNumbers);
			numbers = new ArrayList<Integer>();
		} else{
			instance.randomNumbers = randomNumbers;
		}
		return instance;
	}
	/*곡수에 따른 랜덤으로 분류*/
	void sort() {
		removeList();
		int number = (int) (Math.random() * randomNumbers);
		numbers.add(number);
		for (int i = 0; i < randomNumbers - 1; i++) {
			do {
				number = (int) (Math.random() * randomNumbers);
			} while (numbers.indexOf(number) != -1);
			numbers.add(number);
		}
	}
	/*arraylist 초기화*/
	void removeList(){
		numbers.clear();
	}
	
	ArrayList<Integer> getArrayList(){
		return numbers;
	}
	
	int RegetNumber() {
		return numbers.get(--i);
	}
	
	int getNumber() {
		return numbers.get(i++);
	}
}