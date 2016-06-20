package com.bookislife.flow.data;

import com.bookislife.flow.core.Env;

/**
 * Created by SidneyXu on 2016/06/19.
 */
public abstract class DBContext {

    private long cleanerInterval;
    private long expires;

    public DBContext() {
        expires = Long.parseLong(System.getProperty(Env.Config.DB_CONNECTION_EXPIRES,
                "" + Env.Default.dbConnectionExpires));
        cleanerInterval = Long.parseLong(System.getProperty(Env.Config.DB_CLEANER_INTERVAL,
                "" + Env.Default.dbCleanerInterval));
    }

    public DBContext(long cleanerInterval, long expires){
        this.cleanerInterval=cleanerInterval;
        this.expires=expires;
    }

    protected abstract void clean();

    public void setCleanerInterval(long cleanerInterval) {
        this.cleanerInterval = cleanerInterval;
    }

    protected long getExpires() {
        return expires;
    }

    protected void startCleaner() {
        new Cleaner().start();
    }

    private class Cleaner extends Thread {
        @Override
        public void run() {
            while (true) {
                clean();
                try {
                    sleep(cleanerInterval);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
