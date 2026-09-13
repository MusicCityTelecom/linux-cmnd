using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbT2DemodRfPars
{
	public int m_RfIdx;

	public int m_Frequency;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2DemodRfPars* uRfPars)
	{
		*(int*)uRfPars = m_RfIdx;
		((int*)uRfPars)[1] = m_Frequency;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2DemodRfPars* uRfPars)
	{
		m_RfIdx = *(int*)uRfPars;
		m_Frequency = ((int*)uRfPars)[1];
	}

	public DtDvbT2DemodRfPars()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int rfIdx);
		m_RfIdx = rfIdx;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int frequency);
		m_Frequency = frequency;
	}
}
