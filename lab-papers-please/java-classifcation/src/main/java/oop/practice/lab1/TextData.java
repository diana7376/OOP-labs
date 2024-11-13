package oop.practice.lab1;

public class TextData {
    private String fileName;
    private String text;
    private int numberOfVowels;
    private int numberOfConsonants;

    public void setNumberOfLetters(int numberOfLetters) {
        this.numberOfLetters = numberOfLetters;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNumberOfVowels(int numberOfVowels) {
        this.numberOfVowels = numberOfVowels;
    }

    public void setNumberOfConsonants(int numberOfConsonants) {
        this.numberOfConsonants = numberOfConsonants;
    }

    public void setNumberOfSentences(int numberOfSentences) {
        this.numberOfSentences = numberOfSentences;
    }

    public void setLongestWord(String longestWord) {
        this.longestWord = longestWord;
    }

    private int numberOfLetters;
    private int numberOfSentences;
    private String longestWord;

    public TextData(String fileName, String text){
        this.fileName=fileName;
        this.text=text;
        analyzeText();
    }

    private void analyzeText() {
        numberOfVowels = 0;
        numberOfConsonants = 0;
        numberOfLetters = 0;
        numberOfSentences = 0;
        longestWord = "";

        String[] words = text.split("\\s+");
        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", ""); // Remove non-alphabet characters
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        for (char c : text.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                numberOfLetters++;
                if ("aeiou".indexOf(c) != -1) {
                    numberOfVowels++;
                } else {
                    numberOfConsonants++;
                }
            } else if (c == '.' || c == '!' || c == '?') {
                numberOfSentences++;
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getText() {
        return text;
    }

    public int getNumberOfVowels() {
        return numberOfVowels;
    }

    public int getNumberOfConsonants() {
        return numberOfConsonants;
    }

    public int getNumberOfLetters() {
        return numberOfLetters;
    }

    public int getNumberOfSentences() {
        return numberOfSentences;
    }

    public String getLongestWord() {
        return longestWord;
    }

    @Override
    public String toString() {
        return "File Name: " + fileName + "\n" +
                "Text: " + text + "\n" +
                "Number of Vowels: " + numberOfVowels + "\n" +
                "Number of Consonants: " + numberOfConsonants + "\n" +
                "Number of Letters: " + numberOfLetters + "\n" +
                "Number of Sentences: " + numberOfSentences + "\n" +
                "Longest Word: " + longestWord;
    }

}
