using Dtapi;

namespace DTAPINET;

public struct DtDvbT2TransmitterId
{
	public int m_TxId;

	public double m_RelativePowerdB;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2TransmitterId* uDvbT2Id)
	{
		*(int*)uDvbT2Id = m_TxId;
		((double*)uDvbT2Id)[1] = m_RelativePowerdB;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2TransmitterId* uDvbT2Id)
	{
		m_TxId = *(int*)uDvbT2Id;
		m_RelativePowerdB = ((double*)uDvbT2Id)[1];
	}
}
