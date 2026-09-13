using Dtapi;

namespace DTAPINET;

public struct DtAtsc3StreamSelPars
{
	public int m_PlpId;

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3StreamSelPars* uA3Sel)
	{
		*(int*)uA3Sel = m_PlpId;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3StreamSelPars* uA3Sel)
	{
		m_PlpId = *(int*)uA3Sel;
	}
}
