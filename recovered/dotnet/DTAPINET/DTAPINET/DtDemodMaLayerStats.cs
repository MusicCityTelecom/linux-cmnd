using Dtapi;

namespace DTAPINET;

public struct DtDemodMaLayerStats
{
	public long m_HdrCrc8ErrorCount;

	public long m_PckCrc8ErrorCount;

	public long m_FramingErrorCount;

	public long m_CommonPlpResyncCount;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDemodMaLayerStats* umPars)
	{
		*(long*)umPars = m_HdrCrc8ErrorCount;
		((long*)umPars)[1] = m_PckCrc8ErrorCount;
		((long*)umPars)[2] = m_FramingErrorCount;
		((long*)umPars)[3] = m_CommonPlpResyncCount;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDemodMaLayerStats* umPars)
	{
		m_HdrCrc8ErrorCount = *(long*)umPars;
		m_PckCrc8ErrorCount = ((long*)umPars)[1];
		m_FramingErrorCount = ((long*)umPars)[2];
		m_CommonPlpResyncCount = ((long*)umPars)[3];
	}
}
