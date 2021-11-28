package game;


import cards.Card;
import cards.DeckGenerator;
import enums.CardSuit;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import players.ComputerPlayer;
import players.Player;
import players.UserPlayer;
import utils.FormOpener;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;


public class Game66 implements Initializable {


    /////////////////////////////////////////////////////////////////////////////////
    // Член-променливи

    private Stack<Card> cardStack;     // тестето с карти
    private UserPlayer userPlayer;
    private ComputerPlayer computerPlayer;
    private Player winnerOfLastRound;  // запазва победителя от последното разиграване; нужно за проверки в някои методи
    private Card trumpCard;            // картата-коз
    private List<Button> buttonsList;  // списък с 6те бутона, визуализиращи 6-те карти на User, улеснява действия над 6те бутона
    private static final int winCondition = 66;  // константа за условието за победа - събрани 66 точки


    /////////////////////////////////////////////////////////////////////////////////
    // JavaFX член-променливи от интерфейса

    @FXML
    private Label labelUserScore;      // показва точките на User
    @FXML
    private Label labelDummyScore;     // показва точките на Computer
    @FXML
    private Button buttonOpponentCard; // визуализира картата, изиграна от Computer
    @FXML
    private Button buttonTrumpCard;    // визуализира коза
    @FXML
    private Button buttonPlayAgain;    // когато играта приключи дава възможност за започване на нова

    // изобразяват картите на потребителя
    @FXML
    private Button buttonCard1;
    @FXML
    private Button buttonCard2;
    @FXML
    private Button buttonCard3;
    @FXML
    private Button buttonCard4;
    @FXML
    private Button buttonCard5;
    @FXML
    private Button buttonCard6;



    /////////////////////////////////////////////////////////////////////////////////
    // Методи за инициализация на игралното поле и член-променливите (изпълняват се веднъж в началото на играта)

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePlayingField();
    }

    // инициализация на играта и полето
    private void initializePlayingField() {
        userPlayer = new UserPlayer();
        computerPlayer = new ComputerPlayer();
        initializeButtonsList();
        cardStack = DeckGenerator.generateCardDeck();  // генериране на случайно разбъркано тесте
        dealStartingHands();
        determineTrumpCard();
        bindUserStartingHandToButtons();
        setButtonsTextAndColor();
        labelDummyScore.setText("0");
        labelUserScore.setText("0");
        buttonOpponentCard.setVisible(false);
        buttonTrumpCard.setDisable(false);
        buttonPlayAgain.setVisible(false);
        winnerOfLastRound = userPlayer; // прави така, че при първото разиграване във всяка игра, User ще изиграе първи карта
        for(Button button : buttonsList) {
            button.setDisable(false);
        }
    }

    // добавя 6те бутона-карти на User в списък
    private void initializeButtonsList() {
        buttonsList = new ArrayList<>(6);
        buttonsList.add(buttonCard1);
        buttonsList.add(buttonCard2);
        buttonsList.add(buttonCard3);
        buttonsList.add(buttonCard4);
        buttonsList.add(buttonCard5);
        buttonsList.add(buttonCard6);
    }

    // раздаване на по 6 карти на всеки играч
    private void dealStartingHands() {
        for(int i = 0; i < 6; i++) {
            userPlayer.drawCard(cardStack.pop());
            computerPlayer.drawCard(cardStack.pop());
        }
    }

    // определяне на картата-коз и графично представяне в съответния й бутон
    private void determineTrumpCard() {
        trumpCard = cardStack.pop();
        buttonTrumpCard.setText(trumpCard.toString());
        buttonTrumpCard.setStyle("-fx-text-fill: " + trumpCard.getColor());
    }

    // обвързване на началната ръка на User с 6те бутона-карти
    private void bindUserStartingHandToButtons() {
        List<Card> userPlayerStartingHand = userPlayer.getCardsInHand();
        for(int i=0; i<userPlayerStartingHand.size(); i++) {
            Card card = userPlayerStartingHand.get(i);
            buttonsList.get(i).setUserData(card);   // запазване на всеки Card на User в съответен бутон
        }
    }

    // задаване на текст на бутоните-карти на User, както и цвят на текста
    private void setButtonsTextAndColor() {
        List<Card> userPlayerStartingHand = userPlayer.getCardsInHand();
        for(int i=0; i<userPlayerStartingHand.size(); i++) {
            Card card = userPlayerStartingHand.get(i);
            buttonsList.get(i).setText(card.toString());
            buttonsList.get(i).setStyle("-fx-text-fill: " + card.getColor());
        }
    }



    /////////////////////////////////////////////////////////////////////////////////
    // Методи, изпълнявани по време на играта


    // изпълнява се при натискане на един от 6те бутона-карти на User
    public void onCardPlay(ActionEvent event) throws InterruptedException {

        // ако победителят от последното разиграване е User, то той ще изиграе първата карта в този рунд
        if(winnerOfLastRound instanceof UserPlayer)    // винаги е true при първото разиграване
            userPlaysFirst(event);
        // ако победителят от последното разиграване е Computer, то той вече е изиграл първата карта в този рунд
        if(winnerOfLastRound instanceof ComputerPlayer)
            userPlaysSecond(event);
    }


    // потребителят изиграва първата карта, а после Computer
    private void userPlaysFirst(ActionEvent event) throws InterruptedException {

        Button buttonClicked = (Button) event.getSource();         // определя кой бутон-карта е бил натиснат
        Card userCardToPlay = (Card) buttonClicked.getUserData();  // извлича обвързаната с този бутон карта
        userPlayer.playChosenCard(userCardToPlay);                 // "изиграва" тази карта

        Card opponentCardToPlay = computerPlayer.playRandomCard(); // определя картата, която Computer ще изиграе
        buttonOpponentCard.setVisible(true);                       // прави бутона, показващ изиграната от Computer карта видим
        buttonOpponentCard.setText(opponentCardToPlay.toString()); // и му задава текст и цвят на текста
        buttonOpponentCard.setStyle("-fx-text-fill: " + opponentCardToPlay.getColor());

        // пауза от 2 секунди преди да продължи разиграването, с цел потребителят да може да види изиграната от Computer карта,
        // визуализирана в buttonOpponentCard бутона
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event1 -> {
                    try {
                        processTrick(buttonClicked, userCardToPlay, opponentCardToPlay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        });
        pause.play();
    }


    // Computer вече е изиграл първата карта и потребителят изиграва втората
    private void userPlaysSecond(ActionEvent event) throws InterruptedException {

        // извлича от ComputerPlayer вече изиграната от него в метода computerPlaysFirstCard карта
        Card opponentCardToPlay = computerPlayer.getCardToPlay();

        Button buttonClicked = (Button) event.getSource();         // определя кой бутон-карта е бил натиснат
        Card userCardToPlay = (Card) buttonClicked.getUserData();  // извлича обвързаната с този бутон карта
        userPlayer.playChosenCard(userCardToPlay);                 // "изиграва" тази карта

        processTrick(buttonClicked, userCardToPlay, opponentCardToPlay);
    }


    // обработва процеса на разиграването, след като и двамата потребители са избрали карта
    private void processTrick(Button buttonClicked, Card userCardToPlay, Card opponentCardToPlay) throws InterruptedException {

        // съдържа цвета на първата изиграна карта, т.к. от това зависи как ще се съпоставят 2те карти
        // за да се определи коя карта е била изиграна първа, се използва winnerOfLastRound, т.к от него зависи кой е изиграл първата карта
        CardSuit startingSuit = null;
        if(winnerOfLastRound instanceof UserPlayer)
            startingSuit = userCardToPlay.getSuit();
        else if(winnerOfLastRound instanceof ComputerPlayer)
            startingSuit = opponentCardToPlay.getSuit();
        else {
            System.out.println("Error when detecting this round's starting suit!");
            return;
        }
        determineTrickWinnerAndAddPoints(userCardToPlay, opponentCardToPlay, startingSuit);

        buttonOpponentCard.setVisible(false);    // бутонът, визуализиращ изиграната от Computer карта отново става невидим

        if(checkIfGameOver() == true) { // проверка дали след разиграването има победител
            buttonPlayAgain.setVisible(true);
            for(Button button : buttonsList) {
                button.setDisable(true);
            }
        }
        else {
            drawNewCardsAndUpdateButtons(buttonClicked);  // "теглене" на нови карти

            // ако компютърът е спечелил този рунд, то той ще изиграе първата карта в следващия рунд
            if(winnerOfLastRound instanceof ComputerPlayer)
                computerPlaysFirstCard();
        }
    }


    // изиграване на първата карта от Computer
    private void computerPlaysFirstCard() {
        Card computerCardToPlay = computerPlayer.playRandomCard();
        buttonOpponentCard.setVisible(true); // визуализиране на изиграната от Computer карта
        buttonOpponentCard.setText(computerCardToPlay.toString());
        buttonOpponentCard.setStyle("-fx-text-fill: " + computerCardToPlay.getColor());
    }


    private void determineTrickWinnerAndAddPoints(Card userCard, Card computerCard, CardSuit firstCardSuit) {
        int resultOfTrick = userCard.compareTo(computerCard, firstCardSuit, trumpCard.getSuit());
        switch(resultOfTrick) {
            case 1:
                int userPoints = userPlayer.addWonTrick(userCard, computerCard);
                labelUserScore.setText(String.valueOf(userPoints));       // опресняване на показаните точки
                winnerOfLastRound = userPlayer;       // задаване на печелившия от този рунд
                break;
            case 0:
                int computerPoints = computerPlayer.addWonTrick(userCard, computerCard);
                labelDummyScore.setText(String.valueOf(computerPoints));  // опресняване на показаните точки
                winnerOfLastRound = computerPlayer;   // задаване на печелившия от този рунд
                break;
            default:
                System.out.println("Error when comparing card strengths!");
        }
    }


    // проверка дали има победител
    private boolean checkIfGameOver() {

        if(winnerOfLastRound.getPoints() >= winCondition) {     // ако победителят от последния рунд е достигнал 66 точки
            if(winnerOfLastRound instanceof UserPlayer) {
                FormOpener.openAlert("You won", "You won the game!");
                return true;
            }
            if(winnerOfLastRound instanceof ComputerPlayer) {
                FormOpener.openAlert("You lost", "You lost the game!");
                return true;
            }
        }
        // ако никой не е достигнал 66 точки, но вече няма карти за разигравания
        if(winnerOfLastRound.getPoints() < winCondition && userPlayer.getCardsInHand().size() == 0) {
            FormOpener.openAlert("No winner", "There is no winner!");
            return true;
        }
        return false;
    }


    // рестартира игралното поле
    public void onPlayAgain(ActionEvent event) {
        initializePlayingField();
    }


    // изтегля по една нова карта от тестето за User и Computer
    private void drawNewCardsAndUpdateButtons(Button buttonClicked) {

        switch(cardStack.size()) {
            case 0: // ако вече няма карти за теглене, бутона на изиграната карта става неактивен
                buttonClicked.setText("");
                buttonClicked.setDisable(true);
                break;
            case 1: // ако е останала 1 карта в тестето и коза, то:
                if (winnerOfLastRound instanceof ComputerPlayer) {      // *** ако Computer е спечелил последния рунд, то User взема коза
                    userPlayer.drawCard(trumpCard);
                    updateClickedButtonData(buttonClicked, trumpCard); // бутона-карта, който потребителят е изиграл бива заменен с коза
                    computerPlayer.drawCard(cardStack.pop());          // Computer получава последната карта от тестето
                } else if (winnerOfLastRound instanceof UserPlayer) {     // *** ако User е спечелил последния рунд, то Computer взема коза
                    computerPlayer.drawCard(trumpCard);                // Computer получава коза
                    Card cardForPlayer = cardStack.pop();              // User получава последната карта от тестето
                    userPlayer.drawCard(cardForPlayer);
                    updateClickedButtonData(buttonClicked, cardForPlayer); // бутона-карта, който User е изиграл бива заменен с последната от тестето
                }
                buttonTrumpCard.setText(trumpCard.getSuit().toString());
                break;
            default: // ако в тестето има повече от 1 карта (реално 3/5/7/9/11)
                computerPlayer.drawCard(cardStack.pop());
                Card cardForPlayer = cardStack.pop();
                userPlayer.drawCard(cardForPlayer);
                updateClickedButtonData(buttonClicked, cardForPlayer); // бутона-карта, който потребителят е изиграл бива заменен с новата карта
        }
    }


    // свързва натиснатия от User бутон с новата изтеглена карта, която ще замести изиграната на същия бутон
    private void updateClickedButtonData(Button buttonClicked, Card drawnCard) {
        buttonClicked.setUserData(drawnCard);
        buttonClicked.setText(drawnCard.toString());
        buttonClicked.setStyle("-fx-text-fill: " + drawnCard.getColor());
    }
}
