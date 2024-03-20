package pl.edu.pjwstk.s28259.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


@Repository
public class EntryRepository {
    public final ArrayList<Entry> entries;
    public EntryRepository(){
        entries = new ArrayList<>();
    }

    public Entry getRandom(){
        return entries.get( new Random().nextInt(entries.size()) );
    }


    public void add(Entry entry) {
        entries.add(entry);
    }
    public void add(String eng, String deu, String pol) {
        entries.add(new Entry(eng, deu, pol));
    }

    public boolean contains(Entry entry) {
        return entries.contains(entry);
    }

}
