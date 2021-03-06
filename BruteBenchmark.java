package AndroidKeystoreBrute;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 26.11.2011
 * @author
 */

public class BruteBenchmark extends Thread {
  long lastCall = 0;
  int lastCount = 0;

  public void run() {
    while (!BrutePasswd.found) {
      if ((System.nanoTime() - lastCall) > 1000000000) {
        Output.println("Current Pass: " + String.copyValueOf(BrutePasswd.currPass) + " || est. "
            + ((BrutePasswd.testedPwds - lastCount)) + " Pass/Sec");
        Output.println();

        lastCall = System.nanoTime();
        lastCount = BrutePasswd.testedPwds;

        try {
          Thread.sleep(1000);
        } catch (Exception e) {

        }
      } else {
        // Output.println("Too much");
      }
    }
  }
}
