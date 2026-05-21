package model;

public class Player {
    String name;
    Card c1;
    Card c2;

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void takeCards(Card a, Card b) {
        c1 = a;
        c2 = b;
    }

    public void showCards() {
        System.out.println(name + " cards:");
        c1.show();
        c2.show();
    }
}