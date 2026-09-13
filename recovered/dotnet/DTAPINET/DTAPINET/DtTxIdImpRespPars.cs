using Dtapi;

namespace DTAPINET;

public struct DtTxIdImpRespPars
{
	public int m_Period;

	public int m_TxIdAddress;

	public int m_AveragePeriod;

	internal unsafe void ConvertToUnmgd(Dtapi.DtTxIdImpRespPars* umSelPars)
	{
		*(int*)umSelPars = m_Period;
		((int*)umSelPars)[1] = m_TxIdAddress;
		((int*)umSelPars)[2] = m_AveragePeriod;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtTxIdImpRespPars* umSelPars)
	{
		m_Period = *(int*)umSelPars;
		m_TxIdAddress = ((int*)umSelPars)[1];
		m_AveragePeriod = ((int*)umSelPars)[2];
	}
}
