namespace DTAPINET;

public class DtDemodParsDvbC2 : IDemodPars
{
	public int m_Bandwidth;

	public bool m_ScanL1Part2Data;

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_Bandwidth = *(int*)pDemodPars;
		m_ScanL1Part2Data = ((bool*)pDemodPars)[4];
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_Bandwidth;
		((sbyte*)pDemodPars)[4] = (m_ScanL1Part2Data ? ((sbyte)1) : ((sbyte)0));
	}
}
