using Dtapi;

namespace DTAPINET;

public struct DtDabTransmitterId
{
	public int m_TxMainId;

	public int m_TxSubId;

	public double m_RelativePowerdB;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabTransmitterId* uDabId)
	{
		*(int*)uDabId = m_TxMainId;
		((int*)uDabId)[1] = m_TxSubId;
		((double*)uDabId)[1] = m_RelativePowerdB;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabTransmitterId* uDabId)
	{
		m_TxMainId = *(int*)uDabId;
		m_TxSubId = ((int*)uDabId)[1];
		m_RelativePowerdB = ((double*)uDabId)[1];
	}
}
