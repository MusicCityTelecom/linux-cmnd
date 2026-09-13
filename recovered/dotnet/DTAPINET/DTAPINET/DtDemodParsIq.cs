namespace DTAPINET;

public class DtDemodParsIq : IDemodPars
{
	public int m_Bandwidth;

	public int m_IqDemodType;

	public int m_SampleRate;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_Bandwidth = *(int*)pDemodPars;
		m_IqDemodType = ((int*)pDemodPars)[1];
		m_SampleRate = ((int*)pDemodPars)[2];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_Bandwidth;
		((int*)pDemodPars)[1] = m_IqDemodType;
		((int*)pDemodPars)[2] = m_SampleRate;
	}
}
