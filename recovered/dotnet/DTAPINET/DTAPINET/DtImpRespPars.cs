using Dtapi;

namespace DTAPINET;

public struct DtImpRespPars
{
	public int m_Period;

	public int m_Channel;

	internal unsafe void ConvertToUnmgd(Dtapi.DtImpRespPars* umSelPars)
	{
		*(int*)umSelPars = m_Period;
		((int*)umSelPars)[1] = m_Channel;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtImpRespPars* umSelPars)
	{
		m_Period = *(int*)umSelPars;
		m_Channel = ((int*)umSelPars)[1];
	}
}
