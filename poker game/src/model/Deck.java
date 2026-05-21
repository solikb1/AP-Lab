package model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> list = new ArrayList<Card>();

    public void create() {
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] numbers = { "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "Jack", "Queen", "King", "Ace" };

        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                Card c = new Card();
                c.setCard(suits[i], numbers[j]);
                list.add(c);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(list);
    }

    public Card giveCard() {
        return list.remove(0);
    }
}