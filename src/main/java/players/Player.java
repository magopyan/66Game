package players;

import cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    protected int points;
    protected List<Card> cardsInHand = new ArrayList<>(6);
    protected List<Card> wonCards = new ArrayList<>(); // съхранява списъка с карти от спечелените разигравания

    public Player() {}

    public void drawCard(Card card) {
        cardsInHand.add(card);
    } // "теглене" на карта

    public int getPoints() {
        return points;
    }

    public int getNumberOfCards() {
        return cardsInHand.size();
    }

    public int addWonTrick(Card card1, Card card2) {  // добавяне на 2те карти от спечелено разиграване
        wonCards.add(card1);
        wonCards.add(card2);
        int pointsWon = card1.getStrength() + card2.getStrength();
        points += pointsWon; // увеличава точките в член-променливата

        return points; // и ги връща
    }
}
