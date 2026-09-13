using Dtapi;

namespace DTAPINET;

public struct DtMerPars
{
	public int m_Period;

	internal unsafe void ConvertToUnmgd(Dtapi.DtMerPars* umSelPars)
	{
		*(int*)umSelPars = m_Period;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtMerPars* umSelPars)
	{
		m_Period = *(int*)umSelPars;
	}
}
