using Dtapi;

namespace DTAPINET;

public struct DtDemodLdpcStats
{
	public long m_FecBlocksCount;

	public long m_UncorrFecBlocksCount;

	public long m_FecBlocksCount1;

	public long m_FecBlocksItCount;

	public int m_FecBlocksItMin;

	public int m_FecBlocksItMax;

	public long m_BchBitCount;

	public long m_BchBitErrorCount;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDemodLdpcStats* umPars)
	{
		*(long*)umPars = m_FecBlocksCount;
		((long*)umPars)[1] = m_UncorrFecBlocksCount;
		((long*)umPars)[2] = m_FecBlocksCount1;
		((long*)umPars)[3] = m_FecBlocksItCount;
		((int*)umPars)[8] = m_FecBlocksItMin;
		((int*)umPars)[9] = m_FecBlocksItMax;
		((long*)umPars)[5] = m_BchBitCount;
		((long*)umPars)[6] = m_BchBitErrorCount;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDemodLdpcStats* umPars)
	{
		m_FecBlocksCount = *(long*)umPars;
		m_UncorrFecBlocksCount = ((long*)umPars)[1];
		m_FecBlocksCount1 = ((long*)umPars)[2];
		m_FecBlocksItCount = ((long*)umPars)[3];
		m_FecBlocksItMin = ((int*)umPars)[8];
		m_FecBlocksItMax = ((int*)umPars)[9];
		m_BchBitCount = ((long*)umPars)[5];
		m_BchBitErrorCount = ((long*)umPars)[6];
	}
}
