package src.glitch.Audio;

public class AudioController {

	public static AudioUnit bgMusic;
	public static AudioUnit bgMusicIntro;
	public static AudioUnit jump;
	public static AudioUnit rec;
	public static AudioUnit pause;
	private int musicPosition;
	private boolean playingMusic = false;
	
	private final String MUSICPATH = "Assets/Sound/Music/";
	private final static String SFXPATH = "Assets/Sound/SFX/";
	
	public AudioController() {
		bgMusic = new AudioUnit(MUSICPATH + "song2.wav", -20f);
		bgMusicIntro = new AudioUnit(MUSICPATH + "song2Intro.wav",-20);
		jump = new AudioUnit(SFXPATH+"jump.wav", -35f);
		rec = new AudioUnit(SFXPATH+"rec.wav", -15f);
		pause = new AudioUnit(SFXPATH+"pause.wav", -20f);
		System.out.println(bgMusic.printLength());
		System.out.println(bgMusic.pringLengthMillis());
		
	}
	
	
	public void playMusic() {
		bgMusic.play();
	}
	
	public void stopMusic() {
		bgMusic.stop();
	}
	
	public static void jump() {
		AudioUnit temp = new AudioUnit("Assets/Sound/SFX/jump.wav", -20f);
		temp.playOnce();
	}
	
	public static void pause() {
		AudioUnit temp = new AudioUnit(SFXPATH+"pause.wav", -20f);
		temp.playOnce();
	}
	
	public void pauseMusic(boolean b) {
		if(b) {
			musicPosition = bgMusic.getMusicPosition();
			System.out.println("Music Poisitoin" + bgMusic.getMusicPosition());
			bgMusic.stop();
		} else if(!b) {
			bgMusic.playFromPosition(musicPosition);
			//bgMusic.play();
		}
	}
}
