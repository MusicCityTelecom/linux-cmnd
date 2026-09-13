using Dtapi;

namespace DTAPINET;

public struct DtDvbC2StreamSelPars
{
	public int m_DSliceId;

	public int m_PlpId;

	public int m_CommonPlpId;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2StreamSelPars* uC2Sel)
	{
		*(int*)uC2Sel = m_DSliceId;
		((int*)uC2Sel)[1] = m_PlpId;
		((int*)uC2Sel)[2] = m_CommonPlpId;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2StreamSelPars* uC2Sel)
	{
		m_DSliceId = *(int*)uC2Sel;
		m_PlpId = ((int*)uC2Sel)[1];
		m_CommonPlpId = ((int*)uC2Sel)[2];
	}
}
