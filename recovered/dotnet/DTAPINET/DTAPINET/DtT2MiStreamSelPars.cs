using Dtapi;

namespace DTAPINET;

public struct DtT2MiStreamSelPars
{
	public int m_T2MiOutPid;

	public int m_T2MiTsRate;

	internal unsafe void ConvertToUnmgd(Dtapi.DtT2MiStreamSelPars* uT2Sel)
	{
		*(int*)uT2Sel = m_T2MiOutPid;
		((int*)uT2Sel)[1] = m_T2MiTsRate;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtT2MiStreamSelPars* uT2Sel)
	{
		m_T2MiOutPid = *(int*)uT2Sel;
		m_T2MiTsRate = ((int*)uT2Sel)[1];
	}
}
