using Dtapi;

namespace DTAPINET;

public struct DtDvbTTpsInfo
{
	public int m_LengthIndicator;

	public int m_Constellation;

	public int m_HpCodeRate;

	public int m_LpCodeRate;

	public int m_Guard;

	public int m_Interleaving;

	public int m_Mode;

	public int m_Hierarchy;

	public int m_CellId;

	public int m_HpS48S49;

	public int m_LpS48S49;

	public int m_OddS50_S53;

	public int m_EvenS50_S53;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbTTpsInfo* umPars)
	{
		*(int*)umPars = m_LengthIndicator;
		((int*)umPars)[1] = m_Constellation;
		((int*)umPars)[2] = m_HpCodeRate;
		((int*)umPars)[3] = m_LpCodeRate;
		((int*)umPars)[4] = m_Guard;
		((int*)umPars)[5] = m_Interleaving;
		((int*)umPars)[6] = m_Mode;
		((int*)umPars)[7] = m_Hierarchy;
		((int*)umPars)[8] = m_CellId;
		((int*)umPars)[9] = m_HpS48S49;
		((int*)umPars)[10] = m_LpS48S49;
		((int*)umPars)[11] = m_OddS50_S53;
		((int*)umPars)[12] = m_EvenS50_S53;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbTTpsInfo* umPars)
	{
		m_LengthIndicator = *(int*)umPars;
		m_Constellation = ((int*)umPars)[1];
		m_HpCodeRate = ((int*)umPars)[2];
		m_LpCodeRate = ((int*)umPars)[3];
		m_Guard = ((int*)umPars)[4];
		m_Interleaving = ((int*)umPars)[5];
		m_Mode = ((int*)umPars)[6];
		m_Hierarchy = ((int*)umPars)[7];
		m_CellId = ((int*)umPars)[8];
		m_HpS48S49 = ((int*)umPars)[9];
		m_LpS48S49 = ((int*)umPars)[10];
		m_OddS50_S53 = ((int*)umPars)[11];
		m_EvenS50_S53 = ((int*)umPars)[12];
	}
}
