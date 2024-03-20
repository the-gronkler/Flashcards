package pl.edu.pjwstk.s28259.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;
import pl.edu.pjwstk.s28259.flashcards.formatter.Formatter;

import java.util.Random;
import java.util.Scanner;

@Controller
public class FlashcardsController implements ApplicationRunner {
    private final Printer printer;
    private final EntryRepository er;
    private final FileService fileService;
    Scanner scanner;

    @Autowired
    public FlashcardsController(Printer printer, EntryRepository entryRepository, FileService fileService) {
        this.printer = printer;
        this.er = entryRepository;
        this.fileService = fileService;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fileService.addEntriesFromFile();

        while (true) {
            System.out.println("""
                        Main Menu:
                        1. Add a new word to the dictionary
                        2. Display all words from the dictionary
                        3. Take a test
                        4. Save data
                        Choose an option:\s"""
            );
            String choice = scanner.next();
            scanner.nextLine();

            switch (choice.trim()) {
                case "1" -> addNewWord();
                case "2" -> displayAllWords();
                case "3" -> takeTest();
                case "4" -> saveData();
                default -> System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private void saveData(){
        System.out.println("Saving...");
        fileService.saveData();
        System.out.println("Saved successfully");
    }

    private void takeTest() {
        Formatter formatter = printer.getFormatter();

        Entry entry = er.getRandom();
        Language givenLang = Language.values()[new Random().nextInt(0, 3)];

        System.out.println("Your word is: '" +
                formatter.format( entry.getWord(givenLang) ) +
                "', in " + givenLang );


        for(Language l : Language.values())
            if(l != givenLang){
                String input = getWordFromUser(l);
                String answer = entry.getWord(l);

                if(answer.equalsIgnoreCase(input))
                    System.out.println("Correct!");
                else
                    System.out.println("Wrong, correct answer is " +
                            formatter.format(answer) );
            }
        System.out.println("Thanks for Playing!");
    }

    private String getWordFromUser( Language lang ){
        System.out.println("Enter " + lang + " word: ");
        String res = scanner.next();
        scanner.nextLine();
        return res;
    }

    private void displayAllWords() {
        printer.printAsTable(er);
    }
    private void addNewWord() {
        Entry newEntry = createEntry();
        if( newEntry != null ){
            er.add(newEntry);
            System.out.println("New entry added.");
        }
        else
            System.out.println("No changes saved.");
    }
    public Entry createEntry() {
        System.out.println("Creating new entry... ");

        String eng =  getWordFromUser(Language.eng);
        String deu =  getWordFromUser(Language.deu);
        String pol =  getWordFromUser(Language.pol);

        Entry entry = new Entry(eng, deu, pol);
        if( er.contains(entry) ) {
            System.out.println("This entry already exists.");
            return null;
        }

        System.out.println("Your new entry: ");
        printer.printAsTable(entry);

        while (true){
            System.out.println("Confirm? (y/n): ");
            String response =  scanner.next();
            scanner.nextLine();
            switch (response){
                case "Y", "y" -> { return entry; }
                case "N", "n" -> { return null; }
            }
        }
    }
}



