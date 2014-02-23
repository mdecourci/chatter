package org.chatline;

import java.util.concurrent.TimeUnit;

public abstract class TimeLineTestHelper {

	@SuppressWarnings("static-access")
	protected void delay(int seconds) {
		try {
			Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(seconds));
		} catch (InterruptedException e) {
		}
	}

}
