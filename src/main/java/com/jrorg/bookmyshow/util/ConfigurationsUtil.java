package com.jrorg.bookmyshow.util;

import java.io.File;

public class ConfigurationsUtil {
	public static String getServerHome() {
		String path = ConfigurationsUtil.class.getClassLoader().getResource("").getPath();
		return new File(path).getParent();
	}
}
