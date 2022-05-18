package DicePoker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class dicePokerProperties {
    //declaration of variables
    private final int gameRound = 0, startingBank = 6, defaultRounds = 6, bet = 0;
    private final String[] roundHistory = new String[2 ^ 31 - 1]
                           ,mainMenu = {"Start Game", "Game Info", "Leader Board", "Exit"}
                           ,betMenu = {"Bet £1", "Make a Custom Bet"/*, "All In" Option*/};

    public int getGameRound(){
        return this.gameRound;
    }

    public int getBank(){
        return this.startingBank;
    }

    public int getRounds(){
        return this.defaultRounds;
    }

    public int getBet(){
        return this.bet;
    }

    public String [] getRoundHistory(){
        return this.roundHistory;
    }

    public String [] getMenu(){
        return this.betMenu;
    }

    public String [] getMainMenu(){
        return this.mainMenu;
    }


    //method use to provide game details
    public void gameInfo(){
        //declaration of variables
        String gameRules =
                """
                        How do you play Dice Poker?
                        You start with £6 in your bank. You play a number of rounds, where for each
                        round you place a bet and the computer rolls the dice, checks results and modifies your
                        bank balance.
                        
                        The game will end if you run out of money, or you have no more attempts left.
                                   
                        How do you score?
                        • If the numbers rolled are sequential (e.g. 1 and 2, 5 and 4, etc. Not 6 and 1), you earn double your bet.
                        • If the numbers rolled are identical (rolling doubles), you earn triple your bet.
                        • Otherwise, you win nothing therefore you lose the amount of money you bet.

                        """;//detail game information
        JOptionPane.showMessageDialog(null , gameRules, "Dice Poker: Game Information"
                ,JOptionPane.INFORMATION_MESSAGE);
    }//end method


    //method use to construct leaderBoard
    public void leaderBoard(List<String> gameHistory){
        //declaration of variables & object rounds from class dicePokerGame
        String leaderBoard = "Scores:\n";
        dicePokerGame gameLog = new dicePokerGame();

        List<String> highScores = new ArrayList<>();

        highScores.add("19 Nicole");
        highScores.add("211 Tim");
        highScores.add("89 Michael");
        highScores.add("11 Joe");

        //loop adds new game score on ArrayList
        for(int i = 0; i < gameLog.getGameExecution(); i++) {
            highScores.add(gameHistory.get(i));
        }

        //list package used to sort highScore data in reversed order
        highScores.sort(new scoreComparator().reversed());

        //loop constructs gameResults of each round (roundHistory)
        for(int i = 0; i < highScores.size(); i++) leaderBoard = leaderBoard + highScores.get(i) + "\n";
        
        JOptionPane.showMessageDialog(null, leaderBoard, "Dice Poker: Leader Board"
                ,JOptionPane.INFORMATION_MESSAGE);
    }//end method


    //Super class created to split string entries
    public static class scoreComparator implements Comparator<String>
    {
        @Override//Method used to split userScore, covert to int and compare
        public int compare (String score1, String score2) {
            int p1 = Integer.parseInt(score1.split(" ")[0]);
            int p2 = Integer.parseInt(score2.split(" ")[0]);
            int cmp = Integer.compare(p1, p2);
            if (cmp != 0) {
                return cmp;
            }
            return score1.compareTo(score2);
        }
    }//end class
}

