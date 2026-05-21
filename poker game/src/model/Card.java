package model;

public class Card {
    String suit;
    String number;

    public void setCard(String s, String n) {
        suit = s;
        number = n;
    }

    public void show() {
        System.out.println(suit + " " + number);
    }
}