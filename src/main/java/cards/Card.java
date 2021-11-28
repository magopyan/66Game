package cards;

import enums.CardRank;
import enums.CardSuit;


public class Card {

    private final CardSuit suit;
    private final String color;
    private final CardRank rank;
    private final int strength;


    public Card(CardSuit suit, CardRank rank) {
        this.suit = suit;
        this.color = suit.getCardColor().toString();
        this.rank = rank;
        this.strength = rank.getStrength();
    }

    public CardSuit getSuit() {
        return suit;
    }

    public String getColor() {
        return color;
    }

    public CardRank getRank() {
        return rank;
    }

    public int getStrength() {
        return strength;
    }


    public int compareTo(Card opponentCard, CardSuit firstCardSuit, CardSuit trumpSuit) {

        if(suit == trumpSuit ^ opponentCard.getSuit() == trumpSuit) {  // ако само една от картите е в козов цвят, тя автоматично печели
            if(suit == trumpSuit)
                return 1;
            if(opponentCard.getSuit() == trumpSuit)
                return 0;
        }

        if(suit == opponentCard.getSuit()) { // ако двете карти са от еднакъв цвят, единствено силата им определя победителя
            if(strength > opponentCard.getStrength())
                return 1;
            if(strength < opponentCard.getStrength())
                return 0;
        }
        else { // ако имат различни цветове, то картата с цвета на първата изиграна карта печели
            if(suit == firstCardSuit)
                return 1;
            if(opponentCard.getSuit() == firstCardSuit)
                return 0;
        }
        return -1;
    }

    @Override
    public String toString() {

        String strRank;
        if(rank == CardRank.nine)
            strRank = "9";
        else if(rank == CardRank.ten)
            strRank = "10";
        else
            strRank = rank.toString();

        return strRank + " " + suit.toString();
    }
}
