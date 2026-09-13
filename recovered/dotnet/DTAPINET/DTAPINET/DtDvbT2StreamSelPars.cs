using Dtapi;

namespace DTAPINET;

public struct DtDvbT2StreamSelPars
{
	public int m_PlpId;

	public int m_CommonPlpId;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2StreamSelPars* uT2Sel)
	{
		*(int*)uT2Sel = m_PlpId;
		((int*)uT2Sel)[1] = m_CommonPlpId;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2StreamSelPars* uT2Sel)
	{
		m_PlpId = *(int*)uT2Sel;
		m_CommonPlpId = ((int*)uT2Sel)[1];
	}
}
