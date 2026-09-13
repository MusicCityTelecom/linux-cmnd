using Dtapi;

namespace DTAPINET;

public struct DtTransFuncPars
{
	public int m_Period;

	public int m_Channel;

	internal unsafe void ConvertToUnmgd(Dtapi.DtTransFuncPars* umSelPars)
	{
		*(int*)umSelPars = m_Period;
		((int*)umSelPars)[1] = m_Channel;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtTransFuncPars* umSelPars)
	{
		m_Period = *(int*)umSelPars;
		m_Channel = ((int*)umSelPars)[1];
	}
}
