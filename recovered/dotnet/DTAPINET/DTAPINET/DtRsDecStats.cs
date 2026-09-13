using Dtapi;

namespace DTAPINET;

public struct DtRsDecStats
{
	public bool m_Locked;

	public long m_ByteSkipCount;

	public long m_PacketCount;

	public long m_UncorrPacketCount;

	public long m_ByteErrorCount;

	public long m_BitErrorCount;

	internal unsafe void ConvertToUnmgd(Dtapi.DtRsDecStats* umPars)
	{
		*(bool*)umPars = m_Locked;
		((long*)umPars)[1] = m_ByteSkipCount;
		((long*)umPars)[2] = m_PacketCount;
		((long*)umPars)[3] = m_UncorrPacketCount;
		((long*)umPars)[4] = m_ByteErrorCount;
		((long*)umPars)[5] = m_BitErrorCount;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtRsDecStats* umPars)
	{
		m_Locked = *(bool*)umPars;
		m_ByteSkipCount = ((long*)umPars)[1];
		m_PacketCount = ((long*)umPars)[2];
		m_UncorrPacketCount = ((long*)umPars)[3];
		m_ByteErrorCount = ((long*)umPars)[4];
		m_BitErrorCount = ((long*)umPars)[5];
	}
}
