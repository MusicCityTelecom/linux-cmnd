using Dtapi;

namespace DTAPINET;

public class DtDvbC2PaprPars
{
	public bool m_TrEnabled;

	public double m_TrVclip;

	public int m_TrMaxIter;

	internal DtDvbC2PaprPars()
	{
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2PaprPars* uC2PaprPars)
	{
		*(bool*)uC2PaprPars = m_TrEnabled;
		((double*)uC2PaprPars)[1] = m_TrVclip;
		((int*)uC2PaprPars)[4] = m_TrMaxIter;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2PaprPars* uC2PaprPars)
	{
		m_TrEnabled = *(bool*)uC2PaprPars;
		m_TrVclip = ((double*)uC2PaprPars)[1];
		m_TrMaxIter = ((int*)uC2PaprPars)[4];
	}
}
