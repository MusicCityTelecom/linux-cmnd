namespace DTAPINET;

public abstract class IDtDemodEvent
{
	public virtual void TuningFreqHasChanged(long OldFreqHz, long NewFreqHz)
	{
	}

	public virtual void TuningParsHaveChanged(long OldFreqHz, int OldModType, int[] OldParXtra, long NewFreqHz, int NewModType, int[] NewParXtra)
	{
	}

	public IDtDemodEvent()
	{
	}
}
