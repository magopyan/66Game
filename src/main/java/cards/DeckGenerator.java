package cards;

import enums.CardRank;
import enums.CardSuit;

import java.util.Collections;
import java.util.Stack;

public class DeckGenerator {

    public static Stack<Card> generateCardDeck() {  // генератор на ново тесте

        Stack<Card> cardStack = new Stack<>();
        for(CardSuit suit : CardSuit.values()) {      // за всеки от 4те цвята се генерира по 1 карта от всеки CardRank
            for(CardRank rank : CardRank.values()) {
                cardStack.push(new Card(suit, rank));
            }
        }

        Collections.shuffle(cardStack); // разбъркване на стека
        return cardStack;
    }
}
