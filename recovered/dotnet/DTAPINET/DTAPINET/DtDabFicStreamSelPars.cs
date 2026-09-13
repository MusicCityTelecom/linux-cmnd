using Dtapi;

namespace DTAPINET;

public struct DtDabFicStreamSelPars
{
	public int m_CifIndex;

	public int m_FibIndex;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabFicStreamSelPars* uStremSel)
	{
		*(int*)uStremSel = m_CifIndex;
		((int*)uStremSel)[1] = m_FibIndex;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabFicStreamSelPars* uStremSel)
	{
		m_CifIndex = *(int*)uStremSel;
		m_FibIndex = ((int*)uStremSel)[1];
	}
}
