package com.hawkesa.lyricrecorderws.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hawkesa.lyricrecorderws.application.Constants;

public class StartupListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		String serverResourcesLocation = System.getProperty("SERVER_RESOURCES_LOCATION");
		String operatingSystem = System.getProperty("OPERATING_SYSTEM");

		String lyricRecorderScripts = servletContextEvent.getServletContext()
				.getRealPath("/WEB-INF/lyricRecorderScripts");

		Constants.SERVER_RESOURCES_LOCATION = serverResourcesLocation;
		Constants.OPERATING_SYSTEM = operatingSystem;
		Constants.SCRIPTS_ROOT = lyricRecorderScripts + System.getProperty("file.separator") + operatingSystem;
		Constants.FFMPEG_ROOT = serverResourcesLocation + System.getProperty("file.separator") + "ffmpeg";

		if (operatingSystem.equals("WINDOWS")) {
			Constants.OPERATING_SYSTEM_EXTENSION = "bat";
		} else if (operatingSystem.equals("UNIX")) {
			Constants.OPERATING_SYSTEM_EXTENSION = "sh";
		}

		System.out.println("SERVER_RESOURCES_LOCATION=" + Constants.SERVER_RESOURCES_LOCATION);
		System.out.println("OPERATING_SYSTEM=" + Constants.OPERATING_SYSTEM);
		System.out.println("SCRIPTS_ROOT=" + Constants.SCRIPTS_ROOT);
		System.out.println("FFMPEG_ROOT=" + Constants.FFMPEG_ROOT);
		System.out.println("OPERATING_SYSTEM_EXTENSION=" + Constants.OPERATING_SYSTEM_EXTENSION);

	}
}
