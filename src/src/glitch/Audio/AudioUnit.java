package src.glitch.Audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;


public class AudioUnit {
	
	private Clip clip;
	private float volume;
	private float defVolume;
	private AudioInputStream audioIn;
	
	public AudioUnit(String s){
		volume = -10.0f;
		defVolume = volume;
		try{
			File soundFile = new File(s);
			audioIn = AudioSystem.getAudioInputStream(soundFile);

			clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	public AudioUnit(String s, float volume){
		this.volume = volume;
		defVolume = volume;
		try{
			File soundFile = new File(s);
			audioIn = AudioSystem.getAudioInputStream(soundFile);
				
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume); // Reduce volume by 10 decibels.
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
		
	public void play() {
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.loop(clip.LOOP_CONTINUOUSLY);
		
	}
	
	public void playOnce() {
		clip.loop(0);
	}
	
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
	public void playFromPosition(int p){
		clip.loop(clip.LOOP_CONTINUOUSLY);
		clip.setFramePosition(p);
	}
	
	public int getMusicPosition() {
		return clip.getFramePosition();
	}
	
	public void resetVolume() {
		FloatControl gainControl = 
			    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(defVolume); // Reduce volume by 10 decibels.
	}
	
	public String printLength() {
		return "frames : " +clip.getFrameLength();
	}
	
	public String pringLengthMillis() {
		return "frames in millis : " + clip.getMicrosecondLength();
	}
	
	public boolean isAtLastFrame() {
		if(clip.getFramePosition() > clip.getFrameLength() - 57000)
			return true;
		else return false;
	}
	
}
