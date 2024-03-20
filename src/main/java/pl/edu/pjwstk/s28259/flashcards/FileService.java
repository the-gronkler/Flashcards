package pl.edu.pjwstk.s28259.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@PropertySource("classpath:application.yaml")
public class FileService extends EntryRepository {
    private final EntryRepository er;
    private final String dataFileName;
    
    @Autowired
    public FileService(@Value("${pl.edu.pja.tpo02.filename}") String dataFileName,
                       EntryRepository er )
    {
        this.dataFileName = dataFileName;
        this.er = er;
    }

    public File getDataFile(String fileName){
        Resource dataResource = new ClassPathResource(fileName);
        try {
            return dataResource.getFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveData() {
        File file = new File(System.getProperty("user.dir") +
                        "\\src\\main\\resources\\" +
                        dataFileName );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Entry e : er.entries) {
                writer.write(e.getAsCSV());
                writer.newLine();
            }
        }
        catch (Exception ignored){}
    }

    public void addEntriesFromFile(){
        File dataFile = getDataFile(dataFileName);
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                er.add(fields[0], fields[1], fields[2] );
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
