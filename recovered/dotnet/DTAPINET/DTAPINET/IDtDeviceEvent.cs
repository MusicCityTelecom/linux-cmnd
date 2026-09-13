namespace DTAPINET;

public abstract class IDtDeviceEvent
{
	public virtual void EventCallback(int Event, DtEventArgs rArgs)
	{
	}

	public IDtDeviceEvent()
	{
	}
}
