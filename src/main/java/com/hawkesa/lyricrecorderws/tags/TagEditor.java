package com.hawkesa.lyricrecorderws.tags;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class TagEditor {

	public HashMap<String, String> readAllTags(File file)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		AudioFile audioFile = AudioFileIO.read(file);
		Tag tag = audioFile.getTag();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (FieldKey fieldKey : FieldKey.values()) {
			hashMap.put(fieldKey.toString(), tag.getFirst(fieldKey));
		}

		return hashMap;
	}
}
