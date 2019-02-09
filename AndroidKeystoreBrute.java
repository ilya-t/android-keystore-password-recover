/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AndroidKeystoreBrute;

import java.lang.Math;

public class AndroidKeystoreBrute {
  static final int BRUTE = 1;
  static final int WORD = 2;
  static final int SWORD = 3;
  static final String VERSION = "1.07";
  public static boolean saveNewKeystore = false;
  public static boolean onlyLowerCase = false;
  public static boolean disableSpecialChars = false;
  public static boolean permutations = false;
  public static int minlength = 0;
  public static int minpieces = 1;
  public static int maxpieces = 64;
  public static String firstchars = null;
  public static String tailFile = null;
  public static int tailLength;
  public static Output.TailedFilePrinter TAIL_PRINTER;

  public static void main(String[] args) throws Exception {
    String start = "A";
    int method = 0;

    String keystore = "";
    String dict = "";

    if (args.length == 0) {
      printhelp();
      return;
    }
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
      case "-h":
        printhelp();
        return;
      case "-m":
        i++;
        method = Integer.parseInt(args[i]);
        break;
      case "-k":
        i++;
        keystore = args[i];
        break;
      case "-d":
        i++;
        dict = args[i];
        break;
      case "-p":
        permutations = true;
        break;
      case "-w":
        saveNewKeystore = true;
        break;
      case "-start":
        i++;
        start = args[i];
        break;
      case "-l":
        i++;
        minlength = Integer.parseInt(args[i]);
        break;
      case "-onlylower":
        onlyLowerCase = true;
        break;
      case "-nospecials":
        disableSpecialChars = true;
        break;
      case "-firstchars":
        i++;
        firstchars = args[i];
        break;
      case "-pieces":
        i++;
        minpieces = Integer.parseInt(args[i]);
        i++;
        maxpieces = Integer.parseInt(args[i]);
        break;
      case "-savetail":
        i++;
        tailFile = args[i];
        i++;
        tailLength = Integer.parseInt(args[i]);
        break;
      default:
        Output.println("Unknown argument: " + args[i]);
        return;
      }
    }

    if (tailFile != null) {
      TAIL_PRINTER = new Output.TailedFilePrinter(tailFile, Math.min(tailLength, 10));
      Output.init(Output.composite(
        Output.DEFAULT_OUTPUT,
        TAIL_PRINTER
      ));
    }

    // Prevent restart from a when using only lowercase with a defined start string
    if (start == "A" && onlyLowerCase == true)
      start = "a";

    if ((method == 0) || (method > 3)) {
      printhelp();
      return;
    }

    if (method == 1) {
      BrutePasswd.doit(keystore, start);
    }

    if (method == 2) {
      WordlistPasswd.doit(keystore, dict);
    }

    if (method == 3) {
      SmartWordlistPasswd.doit(keystore, dict);
    }
  }

  public static void quit() {
    Output.println("\r\nFor updates visit http://code.google.com/p/android-keystore-password-recover/");
    if (TAIL_PRINTER != null) {
      TAIL_PRINTER.finish();
    }
    
    System.exit(0);
  }

  static void printhelp() {
    Output.println("AndroidKeystorePasswordRecoveryTool by M@xiking");
    Output.println("Version " + VERSION + "\r\n");
    Output.println("There are 3 Methods to recover the key for your Keystore:\r\n");
    Output.println("1: simply bruteforce - good luck");
    Output.println("2: dictionary attack - your password has to be in the dictionary");
    Output.println("3: smart dictionary attack - you specify a dictionary with regular pieces you use in your passwords. Numbers are automaticly added and first letter will tested uppercase and lowercase. This method can resume when interrupted as long as you specify the same arguments.\r\n");
    Output.println("args:");
    Output.println("-m <1..3> Method");
    Output.println("-k <path> path to your keystore");
    Output.println("-d <path> dictionary (for method 2 and 3)");
    Output.println("-l <min> sets min password length in characters (for method 3)");
    Output.println("-start <String> sets start String of the word (for method 1)");
    Output.println("-firstchars <String> specify first characters of the password (for method 3)");
    Output.println("-pieces <min> <max> specify the min and max number of pieces to use when building passwords (for method 3)\r\n");

    Output.println("-nospecials to not try special characters in password (makes cracking faster for simple passwords)");
    Output.println("-onlylower for only lowercase letters");
    Output.println("-w saves the certificate in a new Keystore with same password as key");
    Output.println("-p use common replacements like '@' for 'a'(for method 3) WARNING: This is very slow. Do not use on dictionaries with more than 250 entries.\r\n");
    Output.println("-savetail <file> <max> specify file where output tail should be stored and tail length\r\n");
    Output.println("-h prints this helpscreen\r\n");

    long maxBytes = Runtime.getRuntime().maxMemory();
    Output.println("Max memory: " + maxBytes / 1024L / 1024L + "M\r\n");

    Output.println("v1.06 updated by rafaelwbr; v1.07 updated by ravensbane");
    Output.println("For updates visit http://code.google.com/p/android-keystore-password-recover/");
  }
}
