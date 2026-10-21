package cs1632;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        PlayingCardDeck deck = new PlayingCardDeck();

        Queue<PlayingCard> player1hand = new LinkedList<>();
        Queue<PlayingCard> player2hand = new LinkedList<>();

        // Deal 26 cards to each player
        while (!deck.isEmpty() && player1hand.size() < 26) {
            player1hand.add(deck.nextCard());
            player2hand.add(deck.nextCard());
        }

        int rounds = 0;

        while (!player1hand.isEmpty() && !player2hand.isEmpty()) {
            rounds++;
            PlayingCard player1card = player1hand.poll();
            PlayingCard player2card = player2hand.poll();

            System.out.println("Round " + rounds + ": Player 1 plays " + player1card + " vs Player 2 plays " + player2card);

            int winner = compareCards(player1card, player2card);
            if (winner > 0) { // Player 1 won
                System.out.println("-> Player 1 wins the round.");
                player1hand.add(player1card);
                player1hand.add(player2card);
            } else if (winner < 0) { // Player 2 won
                System.out.println("-> Player 2 wins the round.");
                player2hand.add(player1card);
                player2hand.add(player2card);
            } else { // Declare war
                System.out.println("-> WAR!");
                Queue<PlayingCard> warPot = new LinkedList<>();
                warPot.add(player1card);
                warPot.add(player2card);

                boolean warResolved = false;
                boolean tie = false;

                while (!warResolved) {
                    // Each player puts down 3 cards (or as many as they have if < 3)
                    for (int i = 0; i < 3; i++) {
                        if (player1hand.isEmpty() || player2hand.isEmpty()) {
                            break;
                        }
                        warPot.add(player1hand.poll());
                        warPot.add(player2hand.poll());
                    }

                    // Check if either player ran out of cards during the 3 face-down cards
                    if (player1hand.isEmpty() && player2hand.isEmpty()) {
                        tie = true;
                        warResolved = true;
                        break;
                    } else if (player1hand.isEmpty()) {
                        warResolved = true;
                        break;
                    } else if (player2hand.isEmpty()) {
                        warResolved = true;
                        break;
                    }

                    // Each player plays their next card for the war battle
                    PlayingCard p1WarCard = player1hand.poll();
                    PlayingCard p2WarCard = player2hand.poll();
                    warPot.add(p1WarCard);
                    warPot.add(p2WarCard);

                    System.out.println("   War battle: Player 1 plays " + p1WarCard + " vs Player 2 plays " + p2WarCard);

                    int warWinner = compareCards(p1WarCard, p2WarCard);
                    if (warWinner > 0) {
                        System.out.println("-> Player 1 wins the War!");
                        while (!warPot.isEmpty()) {
                            player1hand.add(warPot.poll());
                        }
                        warResolved = true;
                    } else if (warWinner < 0) {
                        System.out.println("-> Player 2 wins the War!");
                        while (!warPot.isEmpty()) {
                            player2hand.add(warPot.poll());
                        }
                        warResolved = true;
                    } else {
                        System.out.println("-> Another War!");
                        // Loop continues for another war iteration
                    }
                }

                if (tie) {
                    System.out.println("Both players ran out of cards simultaneously during War. It's a tie!");
                    break;
                }
            }
        }

        System.out.println("\n=== GAME OVER ===");
        if (player1hand.isEmpty() && player2hand.isEmpty()) {
            System.out.println("Result: Tie game!");
        } else if (player1hand.isEmpty()) {
            System.out.println("Result: Player 2 wins the game!");
        } else {
            System.out.println("Result: Player 1 wins the game!");
        }
        System.out.println("Total rounds played: " + rounds);
    }

    public static boolean hasWinner(Queue<PlayingCard> p1deck, Queue<PlayingCard> p2deck) {
        return p1deck.isEmpty() || p2deck.isEmpty();
    }

    public static int compareCards(PlayingCard p1card, PlayingCard p2card) {
        return p1card.playingCardValue() - p2card.playingCardValue();
    }
}