package com.ayy.createthread.extendsthread;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ ClassName StartThreadTest
 * @ Description what the TDownloadTest runs
 * @ Author Zhao JIN
 * @ Date 09/11/2020 15:31
 * @ Version 1.0
 */

public class WebDownloaderTest {
	public void Download(String url, String name){
		try {
			FileUtils.copyURLToFile(new URL(url),new File(
					System.getProperty("user.dir")+"/src/com/ayy/createthread/extendsthread/"+name));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
