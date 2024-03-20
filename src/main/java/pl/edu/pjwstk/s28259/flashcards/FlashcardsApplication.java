
package pl.edu.pjwstk.s28259.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FlashcardsApplication{

    @Autowired
    public FlashcardsApplication(ApplicationContext context, ApplicationArguments args) {

        FlashcardsController controller = context.getBean(FlashcardsController.class);
        try {
            controller.run(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        SpringApplication.run(FlashcardsApplication.class, args);
    }
}