using Dtapi;

namespace DTAPINET;

public struct DtRfLevel
{
	public long m_FreqHz;

	public int m_RfLevel;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtRfLevel* Unmgd)
	{
		m_FreqHz = *(long*)Unmgd;
		m_RfLevel = ((int*)Unmgd)[2];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtRfLevel* Unmgd)
	{
		*(long*)Unmgd = m_FreqHz;
		((int*)Unmgd)[2] = m_RfLevel;
	}
}
