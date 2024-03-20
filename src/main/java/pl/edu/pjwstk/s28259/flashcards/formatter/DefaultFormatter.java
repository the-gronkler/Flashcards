package pl.edu.pjwstk.s28259.flashcards.formatter;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Primary
@Profile("default")
@Service
public class DefaultFormatter implements Formatter {
    @Override
    public String format(String input) {
        return input;
    }
}
