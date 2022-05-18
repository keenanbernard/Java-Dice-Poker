package DicePoker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.text.MessageFormat;
import java.util.Random;

public class dicePokerGame {
    //declaration of global variables
    public String userName;
    public int userScore;
    public static int gameExecution =  0;
    private static int userBank;
    List<String> gameHistory = new ArrayList<>();

    //public method to execute dicePoker Game
    public void dicePoker(){
        //declaration of variables & objects
        dicePokerProperties properties = new dicePokerProperties();
        int gameRound = properties.getGameRound(), bank = properties.getBank(),
            rounds = properties.getRounds(), menuChoice, bet = properties.getBet(), playersBet, roundResult;
        String  playerName, roundResults, gameResults = "";
        String[] roundHistory = properties.getRoundHistory(), betMenu = properties.getMenu();


        playerName = JOptionPane.showInputDialog(null,
                "Welcome to the game of Dice Poker!\n\nEnter your player name."
                , "Dice Poker", JOptionPane.INFORMATION_MESSAGE);

        //input validation for playerName. playerName cannot be empty
        while (playerName == null || playerName.isEmpty()) {
            playerName = JOptionPane.showInputDialog(null,
                    "The Player Name you entered was empty!\n\nKindly enter your player name."
                    , "Error: Player name invalid", JOptionPane.ERROR_MESSAGE);
        }


        String playersPotInformation = "Hi " + playerName + ", you will be starting with a Bank Balance of £" + bank;
        JOptionPane.showMessageDialog(null, playersPotInformation
                , "Dice Poker: Bank Info", JOptionPane.INFORMATION_MESSAGE);


        //do while loop handles all functions (menu option, round information, dice results and bank accumulation)
        do {

            menuChoice = JOptionPane.showOptionDialog(null, "Choose a bet option.",
                    "Dice Poker: Round " + (gameRound + 1) + " Bet Options"
                    , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, betMenu, betMenu[0]);
            userBank = bank;

            if (menuChoice == 0) {
                bet = 1;
            } else if (menuChoice == 1) {
                bet = betChecker();
            } /*else if (menuChoice == 2) {
                bet = bank;
            }*///Option would be used for an ALL IN high stakes bet.

            playersBet = bet;
            bank = bank - playersBet;

            //array simulates dice roll using method
            int[] diceRoll = {diceRoll(), diceRoll()};

            if (diceRoll[0] == diceRoll[1] - 1 || diceRoll[0] == diceRoll[1] + 1) {
                roundResult = playersBet * 2;
                bank = bank + roundResult;
                roundResults = "This was a sequential roll, you won £" + roundResult;
            } else if (diceRoll[0] == diceRoll[1]) {
                roundResult = playersBet * 3;
                bank = bank + roundResult;
                roundResults = "This was a identical roll, you won £" + roundResult;
            } else {
                roundResult = playersBet;
                roundResults = "This roll was neither sequential nor Identical, you lost £" + roundResult;
            }


            roundHistory[gameRound] = "You made a bet of: £" + playersBet
                    + ". Dice results: [" + diceRoll[0] + "] [" + diceRoll[1] + "]. "
                    + roundResults + ".";


            String roundInformation = "Hi " + playerName + ", below are the results of your bet:\n" + roundHistory[gameRound];
            String playersBankInformation = "Your current Bank balance is £" + bank + ".";

            JOptionPane.showMessageDialog(null, roundInformation,
                    "Dice Poker: Round " + (gameRound + 1), JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, playersBankInformation,
                    "Dice Poker: Bank Info", JOptionPane.INFORMATION_MESSAGE);

            rounds--;
            gameRound++;

        } while (bank > 0 && rounds > 0);//repeats block until the  player's bank is empty or until the game is finished


        //loop constructs gameResults of each round (roundHistory)
        for (int loop = 0; loop < gameRound; loop++) {
            gameResults = MessageFormat.format("{0}Round: {1}\n{2}\n", gameResults, loop + 1, roundHistory[loop]);
        }

        String gameResultMessage = "GAME RESULTS\n\n" + gameResults + "\n\n";

        JOptionPane.showMessageDialog(null, gameResultMessage + "\nFinal Bank Balance £"
                                            + bank, "Dice Poker", JOptionPane.INFORMATION_MESSAGE);

        userName =  playerName;
        userScore = bank;
        //adding user data to gameHistory array list
        gameHistory.add(userScore + " " + userName);
        gameExecution ++;

        //passing gameHistory data into leaderBoard
        properties.leaderBoard(gameHistory);
    }


    public int getGameExecution() {
        return gameExecution;
    }

    //public get to return gameHistory for mainMenu
    public List<String> getGameHistory() {
        return gameHistory;
    }


    //input validation method for betAmount. betAmount can only contain positive integer values
    public static int betChecker() {
        boolean error = true;
        int gameRound = 0, bet = 0;
        String numeric = "^[a-zA-Z]+", decimal = "^[0-9]\\.[0-9]+", betAmount;
        Pattern numericPattern = Pattern.compile(numeric);//pattern function compiles the variable numeric as new pattern (REGEX)
        Pattern decimalPattern = Pattern.compile(decimal);//pattern function compiles the variable decimal as new pattern (REGEX)


        do {
            betAmount = JOptionPane.showInputDialog(null, "Enter a bet between £1 & £4.",
                    "Dice Poker: Round " + (gameRound + 1), JOptionPane.INFORMATION_MESSAGE);


            Matcher numerical = numericPattern.matcher(betAmount); //matcher compares numeric regex pattern with betAmount
            Matcher decimalisation = decimalPattern.matcher(betAmount); //matcher compares decimal regex pattern with betAmount

            if (betAmount.isEmpty()) {
                JOptionPane.showMessageDialog(null, """
                                        You've made an empty Bet!!

                                        Enter a valid number of pennies."""
                        , "Error: Invalid entry", JOptionPane.ERROR_MESSAGE);
            } else if (numerical.matches() || decimalisation.matches()) {
                JOptionPane.showMessageDialog(null, "Enter a valid number of pennies."
                        , "Error: Invalid Bet", JOptionPane.ERROR_MESSAGE);
            } else if (Integer.parseInt(betAmount) < 1 || Integer.parseInt(betAmount) > 4) {
                JOptionPane.showMessageDialog(null, "Only bets between £1 & £4 can be placed."
                        , "Error: Invalid Bet", JOptionPane.ERROR_MESSAGE);
            } else if (Integer.parseInt(betAmount) > userBank ) {
                JOptionPane.showMessageDialog(null, "You have insufficient funds.\nYou currently have £"
                                + userBank,"Error: Invalid Bet", JOptionPane.ERROR_MESSAGE);
            } else if (!numerical.matches() || !decimalisation.matches()) {
                error = false;
                bet = Integer.parseInt(betAmount);
            }
        } while (error);

        return bet;
    }//end method


    //method used to simulate dice roll
    public static int diceRoll() {
        //declaration of variables
        Random side = new Random(); //to invoke the random command
        return side.nextInt(7);//return random number
    }//end method
}//end class