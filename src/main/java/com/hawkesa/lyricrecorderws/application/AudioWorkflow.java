package com.hawkesa.lyricrecorderws.application;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.hawkesa.lyricrecorderws.filesystem.ServerCommands;
import com.hawkesa.lyricrecorderws.tags.TagEditor;
import com.hawkesa.lyricrecorderws.wavconversion.AudioWaveFormCreator2;
import com.hawkesa.lyricrecorderws.wavconversion.Coordinate;

public class AudioWorkflow {
	public static String SERVER_RESOURCES_LOCATION = null;
	ServerCommands serverCommands = null;

	public LyricRecorderFile processAudioFile(AudioFile audioFile)
			throws InterruptedException, UnsupportedAudioFileException, CannotReadException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException {
		serverCommands = new ServerCommands();

		LyricRecorderFile lyricRecorderFile = new LyricRecorderFile();

		try {

			System.out.println("About to write file:" + audioFile.getFileName());
			writeToFile(audioFile);
			System.out.println("About to convert:" + audioFile.getFilePath());

			// TimeUnit.SECONDS.sleep(5);
			File wavFile = convertToWAV(audioFile);

			Vector<Coordinate> coordinates = generateWaveCoordinatesFromWavFile(wavFile);

			StringBuilder stringBuilder = new StringBuilder();

			for (Coordinate coordinate : coordinates) {
				stringBuilder.append(coordinate.getX()).append(",").append(coordinate.getY_min()).append(",")
						.append(coordinate.getY_max()).append("|");
			}
			lyricRecorderFile.setCoordinates(stringBuilder.toString());

			TagEditor tagEditor = new TagEditor();
			HashMap <String, String>tagMap = tagEditor.readAllTags(new File(audioFile.getFilePath()));

			lyricRecorderFile.setArtist(tagMap.get("ALBUM"));
			lyricRecorderFile.setAlbum(tagMap.get("ARTIST"));
			lyricRecorderFile.setAlbum(tagMap.get("TITLE"));

			System.out.println("Do something else");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lyricRecorderFile;
	}

	private void writeToFile(AudioFile audioFile) {
		String filePath = serverCommands.writeToFile(audioFile.getFileInputStream(), "sample.mp3");
		audioFile.setFilePath(filePath);
	}

	private File convertToWAV(AudioFile audioFile) throws IOException, InterruptedException {
		return serverCommands.convertAudioFileToWAV(audioFile.getFilePath(), audioFile.getFileName());
	}

	private Vector<Coordinate> generateWaveCoordinatesFromWavFile(File file)
			throws UnsupportedAudioFileException, IOException {
		AudioWaveFormCreator2 awc = new AudioWaveFormCreator2();
		int samplesPerSecond = 100;
		return awc.createWaveForm(file, samplesPerSecond);
	}

}
