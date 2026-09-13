using Dtapi;

namespace DTAPINET;

public struct DtVitDecStats
{
	public long m_BitCount;

	public long m_BitErrorCount;

	internal unsafe void ConvertToUnmgd(Dtapi.DtVitDecStats* umPars)
	{
		*(long*)umPars = m_BitCount;
		((long*)umPars)[1] = m_BitErrorCount;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtVitDecStats* umPars)
	{
		m_BitCount = *(long*)umPars;
		m_BitErrorCount = ((long*)umPars)[1];
	}
}
