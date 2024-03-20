package pl.edu.pjwstk.s28259.flashcards.formatter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("lowercase")
@Service
public class LowercaseFormatter implements Formatter {
    @Override
    public String format(String input) {
        return input.toLowerCase();
    }
}
