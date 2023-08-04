package com.github.zipcodewilmington;


import javax.crypto.spec.PSource;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Destany McClellan
 * @version 1.0.0
 * @date 5/27/21 11:02 AM
 */
public class Hangman {

    static String[] word = {"cat", "dog","snake", "pumpkin"};
    private static String wordToGuess;
    private static char[] guessedWord;
    private static boolean[] guessedLetters;
    public static int attempts;

    public static void main(String[] args) {
        startGame();

        while (attempts > 0 && !isWordGuessed()) {
            displayGuessedWord();
            char guess = getPlayerGuess();
            if (guess == '-') {
                endGame();
            } else {
                processGuess(guess);
            }
        }

        if (isWordGuessed()) {
            playerWins();
        } else {
            playerLoses();
        }
    }
    private static void startGame() {
        // random word chosen from a list
        Random rw = new Random();
        int randomWord = rw.nextInt(word.length);
        wordToGuess = word[randomWord];
        guessedWord = new char[wordToGuess.length()];
        guessedLetters = new boolean[26];
        attempts = wordToGuess.length() + 2;

        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }
    }
    private static void displayGuessedWord() {
        for (char c : guessedWord) {
            System.out.println(c + " ");
        }
        System.out.println();
    }

    private static char getPlayerGuess() {
        Scanner scanner = new Scanner(System.in);
        char guess;
        while (true) {
            System.out.println("Attempts left: " + attempts + ". Guess a letter:");
            String input = scanner.next().toLowerCase();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Enter a valid letter");
            } else {
                guess = input.charAt(0);
                break;
            }
        }
        return guess;
    }

    private static void processGuess(char guess){
        if(guessedLetters[guess - 'a']){
            System.out.println("You already guessed that letter");
            return;
        }
        guessedLetters[guess - 'a'] = true;

        if(wordToGuess.contains(String.valueOf(guess))){
            updateGuessedWord(guess);
            if(isWordGuessed()){
                return;
            }
        } else {
            attempts--;
            if(attempts == 0){
                endGame();
            } else{
                System.out.println("Incorrect guess, try again");
            }
        }
    }

    private static void updateGuessedWord(char guess) {
        for(int i = 0; i < wordToGuess.length(); i++){
            if(wordToGuess.charAt(i) == guess){
                guessedWord[i] = guess;
            }
        }
    }


    private static void playerLoses() {
        System.out.println("You lose..");
    }

    private static void playerWins() {
        System.out.println("You win!");
    }

    private static boolean isWordGuessed() {
        return new String(guessedWord).equals(wordToGuess);
    }

    private static void endGame() {
        System.out.println("Game ended");
        System.exit(0);
    }
}





