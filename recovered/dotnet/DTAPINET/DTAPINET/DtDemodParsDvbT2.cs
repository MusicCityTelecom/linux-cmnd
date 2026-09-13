namespace DTAPINET;

public class DtDemodParsDvbT2 : IDemodPars
{
	public int m_Bandwidth;

	public int m_T2Profile;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_Bandwidth = *(int*)pDemodPars;
		m_T2Profile = ((int*)pDemodPars)[1];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_Bandwidth;
		((int*)pDemodPars)[1] = m_T2Profile;
	}
}
