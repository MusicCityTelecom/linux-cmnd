using Dtapi;

namespace DTAPINET;

public struct DtDvbT2ModStatus
{
	public int m_MplpModFlags;

	public long m_PlpNumBlocksOverflows;

	public long m_BitrateOverflows;

	public long m_TtoErrorCount;

	public long m_T2MiOutputRateOverFlows;

	public int m_T2MiOutputRate;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2ModStatus* uModStatus)
	{
		*(int*)uModStatus = m_MplpModFlags;
		((long*)uModStatus)[1] = m_PlpNumBlocksOverflows;
		((long*)uModStatus)[2] = m_BitrateOverflows;
		((long*)uModStatus)[3] = m_TtoErrorCount;
		((long*)uModStatus)[4] = m_T2MiOutputRateOverFlows;
		((int*)uModStatus)[10] = m_T2MiOutputRate;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2ModStatus* uModStatus)
	{
		m_MplpModFlags = *(int*)uModStatus;
		m_PlpNumBlocksOverflows = ((long*)uModStatus)[1];
		m_BitrateOverflows = ((long*)uModStatus)[2];
		m_TtoErrorCount = ((long*)uModStatus)[3];
		m_T2MiOutputRateOverFlows = ((long*)uModStatus)[4];
		m_T2MiOutputRate = ((int*)uModStatus)[10];
	}
}
