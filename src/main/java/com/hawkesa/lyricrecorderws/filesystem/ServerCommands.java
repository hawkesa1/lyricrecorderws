package com.hawkesa.lyricrecorderws.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.hawkesa.lyricrecorderws.application.Constants;

public class ServerCommands {

	public static final String INPUT_DIRECTORY = "audioInput";
	public static final String OUTPUT_DIRECTORY = "audioOutput";
	public static final String AUDIO_CONVET_SCRIPT = "audioConvert";

	public File convertAudioFileToWAV(String filePath, String fileName) throws IOException, InterruptedException {
		String scriptToExecute = Constants.SCRIPTS_ROOT + System.getProperty("file.separator") + AUDIO_CONVET_SCRIPT
				+ "." + Constants.OPERATING_SYSTEM_EXTENSION;
		String ffmpegLocation = Constants.FFMPEG_ROOT + System.getProperty("file.separator") + "ffmpeg";
		String inputFilePath = filePath;
		String outputFilePath = Constants.SERVER_RESOURCES_LOCATION + System.getProperty("file.separator")
				+ OUTPUT_DIRECTORY + System.getProperty("file.separator") + "sample.wav";
		return convertAudioFileToWAVScript(scriptToExecute, ffmpegLocation, inputFilePath, outputFilePath);
	}

	private File convertAudioFileToWAVScript(String scriptPath, String ffmpegLocation, String inputFilePath,
			String outputFilePath) throws IOException, InterruptedException {
		executeScript(scriptPath, ffmpegLocation, inputFilePath, outputFilePath);
		return readFileFromDisk(outputFilePath);
	}

	public String writeToFile(InputStream inputStream, String fileName) {
		String path = null;
		try {
			path = Constants.SERVER_RESOURCES_LOCATION + System.getProperty("file.separator") + INPUT_DIRECTORY
					+ System.getProperty("file.separator") + fileName;
			OutputStream outpuStream = new FileOutputStream(new File(path));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(path));
			while ((read = inputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	private String executeScript(String scriptPath, String... scriptArguments) throws IOException {

		StringBuilder commandToExecute = new StringBuilder();
		commandToExecute.append(scriptPath).append(" ");
		if (scriptArguments.length > 0) {
			for (String text : scriptArguments) {
				commandToExecute.append("\"" + text + "\" ");
			}
		}

		System.out.println("About to execute script:" + commandToExecute.toString());
		// String[] command = { commandToExecute.toString(), "suspend" };

		Process process = Runtime.getRuntime().exec(commandToExecute.toString());
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		System.out.println("Script output1: " + sb.toString());
		return sb.toString();
	}

	private File readFileFromDisk(String filePath) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		long elapsedTime = -1;
		File file = new File(filePath);
		while (!file.renameTo(file)) {
			Thread.sleep(100);
			elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("Waiting for file: " + file.getPath() + ". Elapsed Time=" + elapsedTime + " sec");
			if (elapsedTime > 5) {
				System.out.println(
						"File took too long to generate: " + file.getPath() + ". Elapsed Time=" + elapsedTime + " sec");
				return null;
			}
		}
		return file;
	}

}
