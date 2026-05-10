
public class Filter {

    private static final String[] PROHIBITED = {
        "damn", "crap", "bad",
        "i will kill", "bomb", "b0mb"
    };

    //Banned word
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
