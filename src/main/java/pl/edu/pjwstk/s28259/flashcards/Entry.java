package pl.edu.pjwstk.s28259.flashcards;

import java.io.InputStream;
import java.io.OutputStream;

public record Entry(String eng, String deu, String pol){
    @Override
    public String toString() {
        return "Entry{" +
                "eng='" + eng + '\'' +
                ", deu='" + deu + '\'' +
                ", pol='" + pol + '\'' +
                '}';
    }

    public String getWord(Language lang){
        return switch (lang){
            case eng -> eng();
            case deu -> deu();
            case pol -> pol();
        };
    }

    public String getAsCSV(){
        return eng + ";" + deu + ";" + pol;
    }
}
