package app;

import model.*;
import java.util.Scanner;

public class Main {

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Cannot clear screen");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Deck d = new Deck();
        d.create();
        d.shuffle();

        Player p1 = new Player();
        Player p2 = new Player();

        System.out.print("Enter name for Player 1: ");
        p1.setName(sc.nextLine());

        System.out.print("Enter name for Player 2: ");
        p2.setName(sc.nextLine());

        p1.takeCards(d.giveCard(), d.giveCard());
        p2.takeCards(d.giveCard(), d.giveCard());

        System.out.println("\n" + p1.getName() + ", press Enter to see your cards...");
        sc.nextLine();
        p1.showCards();

        System.out.println("\nPass to the next player and press Enter...");
        sc.nextLine();

        clearScreen();

        System.out.println(p2.getName() + ", press Enter to see your cards...");
        sc.nextLine();
        p2.showCards();

        sc.close();
    }
}