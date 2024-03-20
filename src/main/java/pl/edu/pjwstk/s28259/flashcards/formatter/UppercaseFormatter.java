package pl.edu.pjwstk.s28259.flashcards.formatter;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile("uppercase")
@Service
public class UppercaseFormatter implements Formatter {
    @Override
    public String format(String input) {
        return input.toUpperCase();
    }
}
