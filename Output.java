package AndroidKeystoreBrute;

public class Output {
    interface Printer {
        void println(String s);
        void println();
    }

    public static final Printer DEFAULT_OUTPUT = new Printer() {
        @Override
        public void println(String s) {
            Output.println(s);
        }

        @Override
        public void println() {
            Output.println();
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
