// Made By ForbiddenKiwis
// 2024/11/03

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {

    private ArrayList<Card> deck;
    Random random = new Random(); // Shuffling Deck

    //dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    // Player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    // Window
    int boardWidth = 600;
    int boardHeight = 600;

    int cardWidth = 100; // Ratio in case want to change size must be 1 / 1,4
    int cardHeight = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                //draw hiddenCard
                Image hiddenCardImage = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                if (!stayButton.isEnabled()){
                    hiddenCardImage = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                }
                g.drawImage(hiddenCardImage, 20, 20, cardWidth, cardHeight, null);

                //Draw dealer's hand
                for(int i = 0; i < dealerHand.size(); i++){
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
                }

                //Draw Player hand
                for(int i = 0; i < playerHand.size(); i++){
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);
                }

                if (!stayButton.isEnabled()){
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY:");
                    System.out.println("DEALER SUM: " + dealerSum);
                    System.out.println("PLAYER SUM: " + playerSum);

                    String message = "";
                    if (playerSum > 21){
                        message = "You Lose!";
                    } else if (dealerSum > 21){
                        message = "You Win!";
                    } else if (dealerSum > playerSum){
                        message = "You Lose!";
                    } else if (dealerSum < playerSum){
                        message = "You Win!";
                    } else if (dealerSum == playerSum) {
                        message = "You Draw!";
                    }

                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    g.drawString(message, 220, 250);

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton resetButton = new JButton("Play Again");

    public BlackJack() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel, BorderLayout.CENTER);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        resetButton.setFocusable(false);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               Card card = deck.remove(deck.size()-1);
               playerSum += card.getValue();
               playerAceCount += card.isAce() ? 1 : 0;
               playerHand.add(card);
               if (reducePlayerAce() > 21){ // A + 2 + J --> 1 + 2 + J
                   hitButton.setEnabled(false);
               }

               gamePanel.repaint();
           }
        });

        stayButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               hitButton.setEnabled(false);
               stayButton.setEnabled(false);
               while (dealerSum < 17){
                   Card card = deck.remove(deck.size()-1);
                   dealerSum += card.getValue();
                   dealerAceCount += card.isAce() ? 1 : 0;
                   dealerHand.add(card);
               }
               gamePanel.repaint();
           }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerHand.clear();
                dealerHand.clear();

                playerSum = 0;
                playerAceCount = 0;
                dealerSum = 0;
                dealerAceCount = 0;

                buildDeck();
                shuffleDeck();

                startGame();

                hitButton.setEnabled(true);
                stayButton.setEnabled(true);

                gamePanel.repaint();
            }
        });

        gamePanel.repaint();
    }

    public void startGame() {
        //deck
        buildDeck();
        shuffleDeck();

        //Dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size() - 1); // Remove Card at last index
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("DEALER\n");
        System.out.println("DEALER HAND: " + dealerHand);
        System.out.println("DEALER SUM: " + dealerSum);
        System.out.println("DEALER ACE: " + dealerAceCount);
        System.out.println("DEALER HIDDEN CARDS: " + hiddenCard);
        System.out.println("DEALER ACE COUNT: " + dealerAceCount);

        //Player

        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("\nPLAYER \n");
        System.out.println("PLAYER HAND: " + playerHand);
        System.out.println("PLAYER SUM: " + playerSum);
        System.out.println("PLAYER ACE COUNT: " + playerAceCount);

    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);

    }

    public void shuffleDeck() {
        for(int i = 0; i < deck.size(); i++){
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }

        System.out.println("AFTER SHUFFLE:");
        System.out.println(deck);
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0){
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0){
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }
}
