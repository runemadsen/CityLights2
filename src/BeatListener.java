import ddf.minim.*;

public class BeatListener implements AudioListener
{
	private BeatDetectPlus beat;
	private AudioPlayer source;

	BeatListener(BeatDetectPlus beat, AudioPlayer source)
	{
		this.source = source;
		this.source.addListener(this);
		this.beat = beat;
	}

	public void samples(float[] samps)
	{
		beat.detect(source.mix);
	}

	public void samples(float[] sampsL, float[] sampsR)
	{
		beat.detect(source.mix);
	}
}
