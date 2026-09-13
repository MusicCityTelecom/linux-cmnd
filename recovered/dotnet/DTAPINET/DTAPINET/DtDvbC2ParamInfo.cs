using Dtapi;

namespace DTAPINET;

public struct DtDvbC2ParamInfo
{
	public int m_L1Part2Length;

	public int m_NumL1Symbols;

	public int m_NumSymbols;

	public int m_PilotSpacing;

	public int m_FftSize;

	public int m_MinCarrierOffset;

	public int m_CenterFrequency;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2ParamInfo* uC2Info)
	{
		*(int*)uC2Info = m_L1Part2Length;
		((int*)uC2Info)[1] = m_NumL1Symbols;
		((int*)uC2Info)[2] = m_NumSymbols;
		((int*)uC2Info)[3] = m_PilotSpacing;
		((int*)uC2Info)[4] = m_FftSize;
		((int*)uC2Info)[5] = m_MinCarrierOffset;
		((int*)uC2Info)[6] = m_CenterFrequency;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2ParamInfo* uC2Info)
	{
		m_L1Part2Length = *(int*)uC2Info;
		m_NumL1Symbols = ((int*)uC2Info)[1];
		m_NumSymbols = ((int*)uC2Info)[2];
		m_PilotSpacing = ((int*)uC2Info)[3];
		m_FftSize = ((int*)uC2Info)[4];
		m_MinCarrierOffset = ((int*)uC2Info)[5];
		m_CenterFrequency = ((int*)uC2Info)[6];
	}
}
