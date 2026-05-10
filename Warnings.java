
import java.io.*;

public class Warnings {

    private int warnings = 0;
    private final int maxWarnings = 3;

    // Warning Sys, loops until true until client is kicked
    public boolean addWarning(PrintWriter out) {
        warnings++;
        int remaining = maxWarnings - warnings; // Remaining warning to max warnings // how many warns the user has
        // kick if warnings get to a maxWarning Amount
        if (warnings >= maxWarnings) {
            out.println("WARNING! Threat Detected. [KICKED]");
            return true;

        } //  Else if warned shows amounts of remaining warns left
        else {
            out.println("[WARNING " + warnings + "/" + maxWarnings + "] Prohibited message blocked. " + remaining + " warning(s) remaining.");
            return false;
        }
    }

    public int getCount() {
        return warnings;
    }
}
