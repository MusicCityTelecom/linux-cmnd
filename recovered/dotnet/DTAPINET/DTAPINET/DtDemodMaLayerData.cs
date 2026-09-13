using Dtapi;

namespace DTAPINET;

public struct DtDemodMaLayerData
{
	public bool m_Hem;

	public bool m_Npd;

	public int m_Issy;

	public int m_IssyBufs;

	public int m_IssyTto;

	public int m_IssyBufStat;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDemodMaLayerData* umPars)
	{
		*(bool*)umPars = m_Hem;
		((sbyte*)umPars)[1] = (m_Npd ? ((sbyte)1) : ((sbyte)0));
		((int*)umPars)[1] = m_Issy;
		((int*)umPars)[2] = m_IssyBufs;
		((int*)umPars)[3] = m_IssyTto;
		((int*)umPars)[4] = m_IssyBufStat;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDemodMaLayerData* umPars)
	{
		m_Hem = *(bool*)umPars;
		m_Npd = ((bool*)umPars)[1];
		m_Issy = ((int*)umPars)[1];
		m_IssyBufs = ((int*)umPars)[2];
		m_IssyTto = ((int*)umPars)[3];
		m_IssyBufStat = ((int*)umPars)[4];
	}
}
