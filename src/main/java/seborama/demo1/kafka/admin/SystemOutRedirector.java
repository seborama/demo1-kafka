package seborama.demo1.kafka.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class SystemOutRedirector {
    private static ByteArrayOutputStream baos;
    private static PrintStream ps;
    private static PrintStream old;

    static synchronized void invoke() {
        if (isRedirected()) return;

        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        old = System.out;

        System.setOut(ps);
    }

    private synchronized static boolean isRedirected() {
        return baos != null && ps != null && old != null;
    }

    static synchronized String revoke() {
        if (!isRedirected()) return null;

        System.out.flush();
        System.setOut(old);
        String output = baos.toString();

        try {
            baos.close();
        } catch (IOException e) {
        }
        ps.close();

        baos = null;
        ps = null;
        old = null;

        return output;
    }
}
