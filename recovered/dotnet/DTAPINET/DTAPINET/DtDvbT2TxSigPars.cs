using Dtapi;

namespace DTAPINET;

public class DtDvbT2TxSigPars
{
	public bool m_TxSigAuxEnabled;

	public int m_TxSigAuxId;

	public int m_TxSigAuxP;

	public int m_TxSigAuxQ;

	public int m_TxSigAuxR;

	public bool m_TxSigFefEnabled;

	public int m_TxSigFefId1;

	public int m_TxSigFefId2;

	internal DtDvbT2TxSigPars()
	{
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2TxSigPars* uT2TxSigPars)
	{
		*(bool*)uT2TxSigPars = m_TxSigAuxEnabled;
		((int*)uT2TxSigPars)[1] = m_TxSigAuxId;
		((int*)uT2TxSigPars)[2] = m_TxSigAuxP;
		((int*)uT2TxSigPars)[3] = m_TxSigAuxQ;
		((int*)uT2TxSigPars)[4] = m_TxSigAuxR;
		((sbyte*)uT2TxSigPars)[20] = (m_TxSigFefEnabled ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2TxSigPars)[6] = m_TxSigFefId1;
		((int*)uT2TxSigPars)[7] = m_TxSigFefId2;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2TxSigPars* uT2TxSigPars)
	{
		m_TxSigAuxEnabled = *(bool*)uT2TxSigPars;
		m_TxSigAuxId = ((int*)uT2TxSigPars)[1];
		m_TxSigAuxP = ((int*)uT2TxSigPars)[2];
		m_TxSigAuxQ = ((int*)uT2TxSigPars)[3];
		m_TxSigAuxR = ((int*)uT2TxSigPars)[4];
		m_TxSigFefEnabled = ((bool*)uT2TxSigPars)[20];
		m_TxSigFefId1 = ((int*)uT2TxSigPars)[6];
		m_TxSigFefId2 = ((int*)uT2TxSigPars)[7];
	}
}
