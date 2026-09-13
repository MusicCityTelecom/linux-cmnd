namespace DTAPINET;

public class DtDemodParsIsdbt : IDemodPars
{
	public int m_Bandwidth;

	public int m_SubChannel;

	public int m_NumberOfSegments;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_Bandwidth = *(int*)pDemodPars;
		m_SubChannel = ((int*)pDemodPars)[1];
		m_NumberOfSegments = ((int*)pDemodPars)[2];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_Bandwidth;
		((int*)pDemodPars)[1] = m_SubChannel;
		((int*)pDemodPars)[2] = m_NumberOfSegments;
	}

	public DtDemodParsIsdbt()
	{
		m_SubChannel = 22;
	}
}
