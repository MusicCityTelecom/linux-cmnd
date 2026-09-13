/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.misc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Stats {
    public static final String ANTLRWORKS_DIR = "antlrworks";

    public static double stddev(int[] X) {
        int m = X.length;
        if (m <= 1) {
            return 0.0;
        }
        double xbar = Stats.avg(X);
        double s2 = 0.0;
        for (int i = 0; i < m; ++i) {
            s2 += ((double)X[i] - xbar) * ((double)X[i] - xbar);
        }
        return Math.sqrt(s2 /= (double)(m - 1));
    }

    public static double avg(int[] X) {
        double xbar = 0.0;
        int m = X.length;
        if (m == 0) {
            return 0.0;
        }
        for (int i = 0; i < m; ++i) {
            xbar += (double)X[i];
        }
        if (xbar >= 0.0) {
            return xbar / (double)m;
        }
        return 0.0;
    }

    public static int min(int[] X) {
        int min = Integer.MAX_VALUE;
        int m = X.length;
        if (m == 0) {
            return 0;
        }
        for (int i = 0; i < m; ++i) {
            if (X[i] >= min) continue;
            min = X[i];
        }
        return min;
    }

    public static int max(int[] X) {
        int max = Integer.MIN_VALUE;
        int m = X.length;
        if (m == 0) {
            return 0;
        }
        for (int i = 0; i < m; ++i) {
            if (X[i] <= max) continue;
            max = X[i];
        }
        return max;
    }

    public static double avg(List<Integer> X) {
        double xbar = 0.0;
        int m = X.size();
        if (m == 0) {
            return 0.0;
        }
        for (int i = 0; i < m; ++i) {
            xbar += (double)X.get(i).intValue();
        }
        if (xbar >= 0.0) {
            return xbar / (double)m;
        }
        return 0.0;
    }

    public static int min(List<Integer> X) {
        int min = Integer.MAX_VALUE;
        int m = X.size();
        if (m == 0) {
            return 0;
        }
        for (int i = 0; i < m; ++i) {
            if (X.get(i) >= min) continue;
            min = X.get(i);
        }
        return min;
    }

    public static int max(List<Integer> X) {
        int max = Integer.MIN_VALUE;
        int m = X.size();
        if (m == 0) {
            return 0;
        }
        for (int i = 0; i < m; ++i) {
            if (X.get(i) <= max) continue;
            max = X.get(i);
        }
        return max;
    }

    public static int sum(int[] X) {
        int s = 0;
        int m = X.length;
        if (m == 0) {
            return 0;
        }
        for (int i = 0; i < m; ++i) {
            s += X[i];
        }
        return s;
    }

    public static void writeReport(String filename, String data) throws IOException {
        String absoluteFilename = Stats.getAbsoluteFileName(filename);
        File f = new File(absoluteFilename);
        File parent = f.getParentFile();
        parent.mkdirs();
        FileOutputStream fos = new FileOutputStream(f, true);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        PrintStream ps = new PrintStream(bos);
        ps.println(data);
        ps.close();
        bos.close();
        fos.close();
    }

    public static String getAbsoluteFileName(String filename) {
        return System.getProperty("user.home") + File.separator + ANTLRWORKS_DIR + File.separator + filename;
    }
}

