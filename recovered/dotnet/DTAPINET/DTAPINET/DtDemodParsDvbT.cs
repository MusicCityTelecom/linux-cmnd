namespace DTAPINET;

public class DtDemodParsDvbT : IDemodPars
{
	public int m_CodeRate;

	public int m_Bandwidth;

	public int m_Constellation;

	public int m_Guard;

	public int m_Interleaving;

	public int m_Mode;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_CodeRate = *(int*)pDemodPars;
		m_Bandwidth = ((int*)pDemodPars)[1];
		m_Constellation = ((int*)pDemodPars)[2];
		m_Guard = ((int*)pDemodPars)[3];
		m_Interleaving = ((int*)pDemodPars)[4];
		m_Mode = ((int*)pDemodPars)[5];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_CodeRate;
		((int*)pDemodPars)[1] = m_Bandwidth;
		((int*)pDemodPars)[2] = m_Constellation;
		((int*)pDemodPars)[3] = m_Guard;
		((int*)pDemodPars)[4] = m_Interleaving;
		((int*)pDemodPars)[5] = m_Mode;
	}
}
