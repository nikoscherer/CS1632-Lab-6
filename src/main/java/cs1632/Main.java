package cs1632;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        PlayingCardDeck deck = new PlayingCardDeck();

        Queue<PlayingCard> player1hand = new LinkedList<>();
        Queue<PlayingCard> player2hand = new LinkedList<>();
        while (!deck.isEmpty()) {
            player1hand.add(deck.nextCard());
            player2hand.add(deck.nextCard());
        }

        while(!player1hand.isEmpty() && !player2hand.isEmpty()) {
            PlayingCard player1card = player1hand.poll();
            PlayingCard player2card = player2hand.poll();

            int winner = compareCards(player1card, player2card);
            if (winner > 0) { // Player 1 won
                player1hand.add(player1card);
                player1hand.add(player2card);
            } else if (winner < 0) {// Player 2 won
                player2hand.add(player1card);
                player2hand.add(player2card);
            } else { // Declare war
                Queue<PlayingCard> warQueue = new LinkedList<>();

                int warWinner = 0;
                while (warWinner == 0) {
                    // Add players 3 cards to the war queue
                    for (int i = 0; i < 3; i++) {
                        if (hasWinner(player1hand, player2hand)) {
                            warWinner = 1; // Can set this to anything, it's
                            // unneeded here.
                            break;
                        }

                        PlayingCard p1card = player1hand.poll();
                        PlayingCard p2card = player2hand.poll();
                        warQueue.add(p1card);
                        warQueue.add(p2card);
                    }


                }
            }
        }

        if (player1hand.isEmpty() && player2hand.isEmpty()) {
            // Tie
        } else if (player1hand.isEmpty()) {
            // P2 wins
        } else {
            // P1 wins
        }

    }

    public static boolean hasWinner(Queue<PlayingCard> p1deck,
                                 Queue<PlayingCard> p2deck) {
        return p1deck.isEmpty() || p2deck.isEmpty();
    }

    public static int compareCards(PlayingCard p1card, PlayingCard p2card) {
        return p1card.playingCardValue() - p2card.playingCardValue();
    }
}