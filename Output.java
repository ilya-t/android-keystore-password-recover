package AndroidKeystoreBrute;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Output {
    interface Printer {
        void println(String s);
        void println();
    }

    public static class TailedFilePrinter implements Printer {
        private final List<String> lines = new ArrayList();
        private final List<String> tmp = new ArrayList();
        private final String fileName;
        private final int tailLength;
        
        public TailedFilePrinter(String fileName, int tailLength) {
            this.fileName = fileName;
            this.tailLength = tailLength;
        }

        @Override
        public void println(String s) {
            lines.add(s);

            if (lines.size() > tailLength) {
                writeToFile();
                lines.clear();
            }
        }

        @Override
        public void println() {
            println("");
        }

        public void finish() {
            writeToFile();
            lines.clear();
        }

        private void writeToFile() {
            tmp.clear();
            tmp.addAll(lines);

            try {
                Files.write(Paths.get(fileName), tmp, Charset.defaultCharset());
            } catch (IOException e) {
                System.out.println("ERROR: Failed to save tail: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public static final Printer DEFAULT_OUTPUT = new Printer() {
        @Override
        public void println(String s) {
            System.out.println(s);
        }

        @Override
        public void println() {
            System.out.println();
        }
    };

    public static Printer composite(final Printer... printers) {
        return new Printer() {
            @Override
            public void println(String s) {
                for (Printer printer : printers) {
                    printer.println(s);
                }
            }

            @Override
            public void println() {
                for (Printer printer : printers) {
                    printer.println();
                }
            }
        };
    }

    private static Printer INSTANCE = DEFAULT_OUTPUT;

    public static void init(Printer instance) {
        Output.INSTANCE = instance;
    }

    public static void println(String s) {
        INSTANCE.println(s);
    }

    public static void println() {
        INSTANCE.println();
    }
}
