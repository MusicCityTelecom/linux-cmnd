using Dtapi;

namespace DTAPINET;

public class DtDvbC2L1UpdatePlpPars
{
	public bool m_Enable;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2L1UpdatePlpPars* uPlpUpdPars)
	{
		*(bool*)uPlpUpdPars = m_Enable;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2L1UpdatePlpPars* uPlpUpdPars)
	{
		m_Enable = *(bool*)uPlpUpdPars;
	}
}
