namespace DTAPINET;

public class DtDemodParsQam : IDemodPars
{
	public int m_Annex;

	public int m_Interleaving;

	public int m_SymRate;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_Annex = *(int*)pDemodPars;
		m_Interleaving = ((int*)pDemodPars)[1];
		m_SymRate = ((int*)pDemodPars)[2];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_Annex;
		((int*)pDemodPars)[1] = m_Interleaving;
		((int*)pDemodPars)[2] = m_SymRate;
	}
}
