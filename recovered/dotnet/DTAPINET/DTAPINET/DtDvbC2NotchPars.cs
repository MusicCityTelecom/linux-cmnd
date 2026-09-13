using Dtapi;

namespace DTAPINET;

public class DtDvbC2NotchPars
{
	public int m_Start;

	public int m_Width;

	internal DtDvbC2NotchPars()
	{
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2NotchPars* uNotchPars)
	{
		*(int*)uNotchPars = m_Start;
		((int*)uNotchPars)[1] = m_Width;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2NotchPars* uNotchPars)
	{
		m_Start = *(int*)uNotchPars;
		m_Width = ((int*)uNotchPars)[1];
	}
}
