package pl.edu.pjwstk.s28259.flashcards;

public enum Language {
    eng("english"), deu("german"), pol("polish");
    private final String displayName;

    @Override
    public String toString() {
        return displayName;
    }

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
