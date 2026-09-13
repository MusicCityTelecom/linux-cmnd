using Dtapi;

namespace DTAPINET;

public class DtAtsc3DemodBootstrapData
{
	public int m_MinorVersion;

	public int m_NumSymbols;

	public int m_EasWakeup;

	public int m_MinTimeToNext;

	public int m_SystemBandwidth;

	public int m_BsrCoefficient;

	public int m_PreambleStructure;

	public int m_L1BasicFecMode;

	public int m_PreambleFftSize;

	public int m_PreambleGuardInterval;

	public int m_PreamblePilotDx;

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3DemodBootstrapData* uBsData)
	{
		*(int*)uBsData = m_MinorVersion;
		((int*)uBsData)[1] = m_NumSymbols;
		((int*)uBsData)[2] = m_EasWakeup;
		((int*)uBsData)[3] = m_MinTimeToNext;
		((int*)uBsData)[4] = m_SystemBandwidth;
		((int*)uBsData)[5] = m_BsrCoefficient;
		((int*)uBsData)[6] = m_PreambleStructure;
		((int*)uBsData)[7] = m_L1BasicFecMode;
		((int*)uBsData)[8] = m_PreambleFftSize;
		((int*)uBsData)[9] = m_PreambleGuardInterval;
		((int*)uBsData)[10] = m_PreamblePilotDx;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3DemodBootstrapData* uBsData)
	{
		m_MinorVersion = *(int*)uBsData;
		m_NumSymbols = ((int*)uBsData)[1];
		m_EasWakeup = ((int*)uBsData)[2];
		m_MinTimeToNext = ((int*)uBsData)[3];
		m_SystemBandwidth = ((int*)uBsData)[4];
		m_BsrCoefficient = ((int*)uBsData)[5];
		m_PreambleStructure = ((int*)uBsData)[6];
		m_L1BasicFecMode = ((int*)uBsData)[7];
		m_PreambleFftSize = ((int*)uBsData)[8];
		m_PreambleGuardInterval = ((int*)uBsData)[9];
		m_PreamblePilotDx = ((int*)uBsData)[10];
	}
}
