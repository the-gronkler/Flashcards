package pl.edu.pjwstk.s28259.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s28259.flashcards.formatter.Formatter;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;


@Service
public class Printer{

    private final Formatter formatter;

    @Autowired
    public Printer(Formatter formatter){
        this.formatter = formatter;
    }
    public Formatter getFormatter(){
        return formatter;
    }

    public void print(Entry entry){
        entry = formatter.format(entry);
        System.out.println("english : " + entry.eng());
        System.out.println("deutsch : " + entry.deu());
        System.out.println("polski  : " + entry.pol());
    }

    public void printAsTable(EntryRepository er){
        int     maxLengthEng = Math.max(7 ,getFieldMaxLength(er, Entry::eng)),
                maxLengthDeu = Math.max(7 ,getFieldMaxLength(er, Entry::deu)),
                maxLengthPol = Math.max(6 ,getFieldMaxLength(er, Entry::pol));
        String line =   "_".repeat(maxLengthEng) + "_|_" +
                "_".repeat(maxLengthDeu) + "_|_" +
                "_".repeat(maxLengthPol);

        //header
        System.out.println( "_".repeat(maxLengthEng + maxLengthDeu + maxLengthPol + 7) );
        System.out.printf(
                "%-"+maxLengthEng+"s | %-"+maxLengthDeu+"s | %-"+maxLengthPol+"s",
                "ENGLISH", "DEUTSCH", "POLSKI"
        );
        System.out.println();

        System.out.println(line);
        for( Entry e : er.entries){
            e = formatter.format(e);
            System.out.printf("%-"+maxLengthEng+"s | %-"+maxLengthDeu+"s | %-"+maxLengthPol+"s",
                    e.eng(), e.deu(), e.pol());
            System.out.println();
        }
        System.out.println( line );
        System.out.println();
    }
    public void printAsTable(Entry entry){
        int     maxLengthEng = Math.max(7, entry.eng().length()),
                maxLengthDeu = Math.max(7, entry.deu().length()),
                maxLengthPol = Math.max(6, entry.pol().length());

        String line =   "_".repeat(maxLengthEng) + "_|_" +
                "_".repeat(maxLengthDeu) + "_|_" +
                "_".repeat(maxLengthPol);

        //header
        System.out.println( "_".repeat(maxLengthEng + maxLengthDeu + maxLengthPol + 7) );
        System.out.printf(
                "%-"+maxLengthEng+"s | %-"+maxLengthDeu+"s | %-"+maxLengthPol+"s",
                "ENGLISH", "DEUTSCH", "POLSKI"
        );

        System.out.println();

        System.out.println(line);

        entry = formatter.format(entry);
        System.out.printf("%-"+maxLengthEng+"s | %-"+maxLengthDeu+"s | %-"+maxLengthPol+"s",
                entry.eng(), entry.deu(), entry.pol());
        System.out.println();

        System.out.println( line );
    }

    private int getFieldMaxLength(EntryRepository er, Function<Entry, String> fieldGetter) {
        if (er == null || er.entries.isEmpty())
            return 0;
        return er.entries.stream()
                .map(fieldGetter)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }
    public void printBanner(){
        // banner type thing
        Resource logo = new ClassPathResource("logo.app");
        try( Stream<String> lines = Files.lines(logo.getFile().toPath()) ) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
