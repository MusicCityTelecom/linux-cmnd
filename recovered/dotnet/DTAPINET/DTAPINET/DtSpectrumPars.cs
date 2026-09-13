using Dtapi;

namespace DTAPINET;

public struct DtSpectrumPars
{
	public int m_Period;

	public int m_FftLength;

	public int m_AverageLength;

	internal unsafe void ConvertToUnmgd(Dtapi.DtSpectrumPars* umSelPars)
	{
		*(int*)umSelPars = m_Period;
		((int*)umSelPars)[1] = m_FftLength;
		((int*)umSelPars)[2] = m_AverageLength;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtSpectrumPars* umSelPars)
	{
		m_Period = *(int*)umSelPars;
		m_FftLength = ((int*)umSelPars)[1];
		m_AverageLength = ((int*)umSelPars)[2];
	}
}
