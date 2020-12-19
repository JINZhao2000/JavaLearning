package com.ayy.createthread.implcallable;

import java.util.concurrent.*;

/**
 * @ ClassName StartThreadTest
 * @ Description the use of implements Callable
 * @ Author Zhao JIN
 * @ Date 09/11/2020 15:31
 * @ Version 1.0
 */

public class TDownloaderTest implements Callable<Boolean> {
	private String url;
	private String name;

	public TDownloaderTest (String url, String name) {
		this.url = url;
		this.name = name;
	}

	public static void main (String[] args) throws ExecutionException, InterruptedException {
		TDownloaderTest td1 = new TDownloaderTest("http://upload.news.cecb2b.com/2014/0511/1399775432250.jpg","1.jpg");
		TDownloaderTest td2 = new TDownloaderTest("http://upload.news.cecb2b.com/2014/0511/1399775432250.jpg","2.jpg");
		TDownloaderTest td3 = new TDownloaderTest("https://scontent-mrs2-2.xx.fbcdn.net/v/t1.0-9/14925809_2146251615599263_4380003036525609377_n.png?_nc_cat=106&_nc_sid=730e14&_nc_ohc=uPV2CNqybUgAX_RNRto&_nc_ht=scontent-mrs2-2.xx&oh=ac68a3f89944fab0b710004bee13c7c2&oe=5F869F65","3.jpg");
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		Future<Boolean> booleanFuture1 = executorService.submit(td1);
		Future<Boolean> booleanFuture2 = executorService.submit(td2);
		Future<Boolean> booleanFuture3 = executorService.submit(td3);
		boolean result1 = booleanFuture1.get();
		boolean result2 = booleanFuture2.get();
		boolean result3 = booleanFuture3.get();
		executorService.shutdownNow();
	}

	@Override
	public Boolean call () throws Exception {
		WebDownloaderTest wd = new WebDownloaderTest();
		wd.Download(url,name);
		System.out.println(name);
		return true;
	}
}
