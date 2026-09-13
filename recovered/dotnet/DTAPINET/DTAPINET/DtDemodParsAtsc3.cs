namespace DTAPINET;

public class DtDemodParsAtsc3 : IDemodPars
{
	public int m_Bandwidth;

	public bool m_AlpLenIncludesAhSi;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_Bandwidth = *(int*)pDemodPars;
		m_AlpLenIncludesAhSi = ((bool*)pDemodPars)[4];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_Bandwidth;
		((sbyte*)pDemodPars)[4] = (m_AlpLenIncludesAhSi ? ((sbyte)1) : ((sbyte)0));
	}
}
