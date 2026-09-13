using Dtapi;

namespace DTAPINET;

public class DtDvbC2XFecFrameHeader
{
	private int m_FecType;

	private int m_Modulation;

	private int m_CodeRate;

	private int m_HdrCntr;

	private int m_XFecFrameCount;

	internal DtDvbC2XFecFrameHeader()
	{
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2XFecFrameHeader* uFecHdr)
	{
		*(int*)uFecHdr = m_FecType;
		((int*)uFecHdr)[1] = m_Modulation;
		((int*)uFecHdr)[2] = m_CodeRate;
		((int*)uFecHdr)[3] = m_HdrCntr;
		((int*)uFecHdr)[4] = m_XFecFrameCount;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2XFecFrameHeader* uFecHdr)
	{
		m_FecType = *(int*)uFecHdr;
		m_Modulation = ((int*)uFecHdr)[1];
		m_CodeRate = ((int*)uFecHdr)[2];
		m_HdrCntr = ((int*)uFecHdr)[3];
		m_XFecFrameCount = ((int*)uFecHdr)[4];
	}
}
