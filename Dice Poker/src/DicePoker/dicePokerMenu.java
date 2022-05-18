/*
 Name         : dicePoker
 Description  : The following program implements the game dice poker
 Amendments   :
 When           What
 ==============   =================================================
 15-AUGUST-2021   Initial Creation
 16-AUGUST-2021   Updated program with input validations
 18-AUGUST-2021   Updated program with menu options & leader Board
 ==============   =================================================
 */
package DicePoker;

import javax.swing.*;

public class dicePokerMenu {

    public static void main(String[] args) {
        //declaration of variables & objects
        dicePokerGame dicePoker = new dicePokerGame();
        dicePokerProperties properties = new dicePokerProperties();
        int menuOption;
        String [] mainMenu = properties.getMainMenu();


        //do while loop to provide a mainMenu for calling methods for starting game, reading game details
        //,checking scoreboard and exit option.
        do {
            menuOption = JOptionPane.showOptionDialog(null, "Select Start to play Dice Poker",
                    "Welcome", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, mainMenu,mainMenu[0]);
            if (menuOption == 0) {
                dicePoker.dicePoker();
            }
            else if (menuOption == 1) {
                properties.gameInfo();
            }
            else if (menuOption == 2) {
                properties.leaderBoard(dicePoker.getGameHistory());
            }
        }
        while (menuOption != 3);//end game menu when exit is selected
    }//end main
}
