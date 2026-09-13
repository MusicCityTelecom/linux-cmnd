using Dtapi;

namespace DTAPINET;

public class DtDvbT2AuxPars
{
	private int m_NumDummyStreams;

	internal DtDvbT2AuxPars()
	{
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2AuxPars* uAuxPars)
	{
		*(int*)uAuxPars = m_NumDummyStreams;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2AuxPars* uAuxPars)
	{
		m_NumDummyStreams = *(int*)uAuxPars;
	}
}
