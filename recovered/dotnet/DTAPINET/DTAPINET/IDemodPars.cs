namespace DTAPINET;

public abstract class IDemodPars
{
	internal unsafe virtual void ConvertFromUnmgd(void* pDemodPars)
	{
	}

	internal unsafe virtual void ConvertToUnmgd(void* pDemodPars)
	{
	}

	public IDemodPars()
	{
	}
}
