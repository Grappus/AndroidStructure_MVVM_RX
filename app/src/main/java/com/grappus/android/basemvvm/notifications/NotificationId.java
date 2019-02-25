package com.grappus.android.basemvvm.notifications;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aman on 26/12/18.
 */

public class NotificationId {

    private final static AtomicInteger c = new AtomicInteger(0);

    public static int getID() {
        return c.incrementAndGet();
    }
}
