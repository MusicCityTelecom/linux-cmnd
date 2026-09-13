namespace DTAPINET;

public class DtDemodParsDvbS2 : IDemodPars
{
	public int m_CodeRate;

	public int m_Pilots;

	public int m_SpecInv;

	public int m_FecFrame;

	public int m_SymRate;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_CodeRate = *(int*)pDemodPars;
		m_Pilots = ((int*)pDemodPars)[1];
		m_SpecInv = ((int*)pDemodPars)[2];
		m_FecFrame = ((int*)pDemodPars)[3];
		m_SymRate = ((int*)pDemodPars)[4];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_CodeRate;
		((int*)pDemodPars)[1] = m_Pilots;
		((int*)pDemodPars)[2] = m_SpecInv;
		((int*)pDemodPars)[3] = m_FecFrame;
		((int*)pDemodPars)[4] = m_SymRate;
	}
}
