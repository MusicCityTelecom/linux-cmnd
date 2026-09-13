namespace DTAPINET;

public class DtDemodParsAtsc : IDemodPars
{
	public int m_Constellation;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_Constellation = *(int*)pDemodPars;
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_Constellation;
	}
}
