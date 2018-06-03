package com.hawkesa.lyricrecorderws.rest;

import java.io.InputStream;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.hawkesa.lyricrecorderws.application.AudioFile;
import com.hawkesa.lyricrecorderws.application.AudioWorkflow;
import com.hawkesa.lyricrecorderws.application.LyricRecorderFile;

@Path("/form")
public class MultiPartResource {

	@POST
	@Path("part-file-name")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String post(@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition formDataContentDisposition) {
		System.out.println(formDataContentDisposition.toString());

		AudioFile audioFile = new AudioFile();
		audioFile.setFileInputStream(inputStream);
		audioFile.setFileName(formDataContentDisposition.getFileName());

		AudioWorkflow audioWorkflow = new AudioWorkflow();
		String returnString = "";

		try {

			LyricRecorderFile lyricRecorderFile = audioWorkflow.processAudioFile(audioFile);
			returnString = lyricRecorderFile.toJSON();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnString;
	}

}
