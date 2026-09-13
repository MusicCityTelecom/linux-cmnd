using Dtapi;

namespace DTAPINET;

public class DtAtsc3DemodL1Data
{
	public DtAtsc3DemodBootstrapData m_Bootstrap;

	public DtAtsc3DemodL1BasicData m_L1Basic;

	public DtAtsc3DemodL1DetailData m_L1Detail;

	public DtAtsc3DemodL1Data()
	{
		m_Bootstrap = new DtAtsc3DemodBootstrapData();
		m_L1Basic = new DtAtsc3DemodL1BasicData();
		m_L1Detail = new DtAtsc3DemodL1DetailData();
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3DemodL1Data* uL1Data)
	{
		m_Bootstrap.ConvertToUnmgd((Dtapi.DtAtsc3DemodBootstrapData*)uL1Data);
		m_L1Basic.ConvertToUnmgd((Dtapi.DtAtsc3DemodL1BasicData*)((byte*)uL1Data + 44));
		m_L1Detail.ConvertToUnmgd((Dtapi.DtAtsc3DemodL1DetailData*)((byte*)uL1Data + 160));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3DemodL1Data* uL1Data)
	{
		m_Bootstrap.ConvertFromUnmgd((Dtapi.DtAtsc3DemodBootstrapData*)uL1Data);
		m_L1Basic.ConvertFromUnmgd((Dtapi.DtAtsc3DemodL1BasicData*)((byte*)uL1Data + 44));
		m_L1Detail.ConvertFromUnmgd((Dtapi.DtAtsc3DemodL1DetailData*)((byte*)uL1Data + 160));
	}
}
