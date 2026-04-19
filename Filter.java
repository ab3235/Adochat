
public class Filter {

    private static final String[] PROHIBITED = {"badword", "spam", "scam"};

    public static boolean isFlagged(String message) {
        String lower = message.toLowerCase();
        for (String word : PROHIBITED) {
            if (lower.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
