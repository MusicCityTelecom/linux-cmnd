using Dtapi;

namespace DTAPINET;

public struct DtDvbC2ModStatus
{
	public int m_MplpModFlags;

	public long m_DjbOverflows;

	public long m_DjbUnderflows;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2ModStatus* uModStatus)
	{
		*(int*)uModStatus = m_MplpModFlags;
		((long*)uModStatus)[1] = m_DjbOverflows;
		((long*)uModStatus)[2] = m_DjbUnderflows;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2ModStatus* uModStatus)
	{
		m_MplpModFlags = *(int*)uModStatus;
		m_DjbOverflows = ((long*)uModStatus)[1];
		m_DjbUnderflows = ((long*)uModStatus)[2];
	}
}
