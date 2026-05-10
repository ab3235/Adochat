Admin Brdarevic
AdoChat - IT114 - 008

How to compile your program
 - javac all Files
 - javac *.javac

How To Run the server?
In one terminal run the server code;
 - java Server.java

You should run the GUI in multiple Different Terminals to get multiple clients
To actually run the program use; 
 - java GUI.java

To actually get a client to join the server.
 - You must set up a username for each client first.


How to include more filtered words?
 -     private static final String[] PROHIBITED = {
        "damn", "crap", "bad",
        "i will kill", "bomb", "b0mb"
    };
Change this array and fit more words of your liking to get them filtered on the server.


How to change the amount of warns? 
 -    private int warnings = 0;
      private final int maxWarnings = 3;
Edit this line of code and increase the maxWarnings to your likings.
EX: private final int maxWarnings = 10; 



