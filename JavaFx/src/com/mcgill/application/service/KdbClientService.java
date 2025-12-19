package com.mcgill.application.service;

import com.kx.c;
import javafx.application.Platform;
import java.util.function.BiConsumer;

public class KdbClientService {
    private c conn;

    public void connect(String host, int port) throws Exception {
        conn = new c(host, port);
    }

    public Object exec(String query) throws Exception {
        return conn.k(query);
    }
    public void subscribeTrades(BiConsumer<String, Double> onTick) {
        try {
            // register as subscriber to all trades
            System.out.println("KDB: Subscribing to RDB...");
            conn.k(".u.sub", "trade", new String[]{});
            System.out.println("KDB: Subscription successful");
        } catch (Exception e) {
            System.err.println("KDB: Subscription failed: " + e.getMessage());
            e.printStackTrace();
        }

        Thread t = new Thread(() -> {
            try {
                System.out.println("KDB: Listening for updates...");
                while (true) {
                    Object msg = conn.k();
                    System.out.println("KDB: Received message type: " + msg.getClass().getName());
                    if (msg instanceof Object[] outer && "upd".equals(outer[0].toString())) {
                        String table = outer[1].toString();
                        System.out.println("KDB: Update for table: " + table);
                        if ("trade".equals(table)) {
                            c.Flip f = (c.Flip) outer[2];
                            String[] sym = (String[]) f.y[0];
                            double[] px  = (double[]) f.y[2];
                            System.out.println("KDB: Processing " + sym.length + " ticks");
                            for (int i = 0; i < sym.length; i++) {
                                final String s = sym[i]; final double p = px[i];
                                System.out.println("KDB: " + s + " @ " + p);
                                Platform.runLater(() -> onTick.accept(s, p));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("KDB: Error in listener: " + e.getMessage());
                e.printStackTrace();
            }
        }, "kdb-sub");
        t.setDaemon(true);
        t.start();
    }
    public void close() { try { if (conn != null) conn.close(); } catch (Exception ignored) {} }
}