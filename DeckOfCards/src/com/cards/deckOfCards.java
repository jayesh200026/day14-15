package com.cards;

public class deckOfCards {

	public static void main(String[] args) {

		String suits[] = { "Clubs", "Daimonds", "Heart", "Spades" };
		String rank[] = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };

		String card[] = new String[52];

		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < rank.length; j++) {
				card[rank.length * i + j] = rank[j] + " of " + suits[i];
			}
		}
		// System.out.println(card.length);
		String temp;
		for (int i = 0; i < card.length; i++) {
			int randomValue = (int) (Math.random() * card.length);
			for (int j = 0; j < card.length; j++) {
				temp = card[j];
				card[j] = card[randomValue];
				card[randomValue] = temp;
			}
		}

		int k = 0;
		String disp[][] = new String[4][9];

		for (int i = 0; i < 4; i++) {
			// System.out.println("Player ::"+(i+1));
			for (int j = 0; j < 9; j++) {
				// System.out.print("["+card[k]+"]");
				disp[i][j] = card[k];

				k++;
			}

		}
		for (int i = 0; i < 4; i++) {
			System.out.println("Player ::" + (i + 1));
			for (int j = 0; j < 9; j++) {
				System.out.print("[" + disp[i][j] + "]");

			}
			System.out.println();

		}

	}

}
