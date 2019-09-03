package io.github.pleuvoir.manager.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.env.Environment;

public class UtilMix {

	public static boolean isDev() {
		return isSpecifiedEnvironment("dev");
	}

	private static boolean isSpecifiedEnvironment(String environment) {
		Environment env = ApplicationContextUtils.getBean(Environment.class);
		String[] profiles = env.getActiveProfiles();
		return ArrayUtils.contains(profiles, environment);
	}

}
