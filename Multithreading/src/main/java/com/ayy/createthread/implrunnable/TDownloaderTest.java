package com.ayy.createthread.implrunnable;

/**
 * @ ClassName StartThreadTest
 * @ Description the use of implements Runnable
 * @ Author Zhao JIN
 * @ Date 09/11/2020 15:31
 * @ Version 1.0
 */

public class TDownloaderTest implements Runnable {
	private String url;
	private String name;

	public TDownloaderTest (String url, String name) {
		this.url = url;
		this.name = name;
	}

	@Override
	public void run () {
		WebDownloaderTest wd = new WebDownloaderTest();
		wd.Download(url,name);
		System.out.println(name);
	}

	public static void main (String[] args) {
		new Thread(new TDownloaderTest("http://upload.news.cecb2b.com/2014/0511/1399775432250.jpg","1.jpg")).start();
		new Thread(new TDownloaderTest("http://upload.news.cecb2b.com/2014/0511/1399775432250.jpg","2.jpg")).start();
		new Thread(new TDownloaderTest("https://scontent-mrs2-2.xx.fbcdn.net/v/t1.0-9/14925809_2146251615599263_4380003036525609377_n.png?_nc_cat=106&_nc_sid=730e14&_nc_ohc=uPV2CNqybUgAX_RNRto&_nc_ht=scontent-mrs2-2.xx&oh=ac68a3f89944fab0b710004bee13c7c2&oe=5F869F65","3.jpg")).start();
	}
}
