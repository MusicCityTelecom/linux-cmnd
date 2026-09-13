import java.util.Arrays;

/** Offline contract probe: only invokes a dummy main method, never CMND. */
public class LauncherProbe {
    public static class Target {
        public static String[] received;
        public static void main(String[] args) { received = args; }
    }
    public static void main(String[] args) throws Exception {
        Class<?> runner = Class.forName("org.springframework.boot.loader.MainMethodRunner");
        for (String[] values : new String[][] {null, new String[0], new String[] {"alpha", "two words"}}) {
            String[] original = values == null ? null : values.clone();
            Object instance = runner.getConstructor(String.class, String[].class)
                .newInstance(Target.class.getName(), values);
            if (values != null && values.length > 0) values[0] = "mutated-after-construction";
            runner.getMethod("run").invoke(instance);
            if (!Arrays.equals(original, Target.received)) throw new AssertionError("Arguments not preserved");
        }
        System.out.println("PASS: null, empty, multiple and defensive-copy launcher arguments");
    }
}
