package pl.edu.pjwstk.s28259.flashcards.formatter;


import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s28259.flashcards.Entry;

@Service
public interface Formatter {
    String format(String input);
    default Entry format(Entry entry){
        return new Entry(
                format( entry.eng() ),
                format( entry.deu() ),
                format( entry.pol() )
        );
    }
}
