namespace DTAPINET;

public class DtDemodParsDvbS : IDemodPars
{
	public int m_CodeRate;

	public int m_SpecInv;

	public int m_SymRate;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_CodeRate = *(int*)pDemodPars;
		m_SpecInv = ((int*)pDemodPars)[1];
		m_SymRate = ((int*)pDemodPars)[2];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_CodeRate;
		((int*)pDemodPars)[1] = m_SpecInv;
		((int*)pDemodPars)[2] = m_SymRate;
	}
}
