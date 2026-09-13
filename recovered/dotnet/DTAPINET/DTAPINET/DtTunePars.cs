using Dtapi;

namespace DTAPINET;

public abstract class DtTunePars
{
	internal unsafe virtual void ConvertFromUnmgd(Dtapi.DtTunePars* uTunePars)
	{
	}

	internal unsafe virtual void ConvertToUnmgd(Dtapi.DtTunePars* uTunePars)
	{
	}

	public DtTunePars()
	{
	}
}
