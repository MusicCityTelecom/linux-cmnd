using Dtapi;

namespace DTAPINET;

public struct DtDvbS2StreamSelPars
{
	public int m_Isi;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbS2StreamSelPars* umSelPars)
	{
		*(int*)umSelPars = m_Isi;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbS2StreamSelPars* umSelPars)
	{
		m_Isi = *(int*)umSelPars;
	}
}
