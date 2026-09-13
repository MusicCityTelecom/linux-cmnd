using Dtapi;

namespace DTAPINET;

public struct DtDvbT2ParamInfo
{
	public int m_TotalCellsPerFrame;

	public int m_L1CellsPerFrame;

	public int m_AuxCellsPerFrame;

	public int m_BiasBalCellsPerFrame;

	public int m_BiasBalCellsMax;

	public int m_DummyCellsPerFrame;

	public int m_SamplesPerFrame;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2ParamInfo* uT2Info)
	{
		*(int*)uT2Info = m_TotalCellsPerFrame;
		((int*)uT2Info)[1] = m_L1CellsPerFrame;
		((int*)uT2Info)[2] = m_AuxCellsPerFrame;
		((int*)uT2Info)[3] = m_BiasBalCellsPerFrame;
		((int*)uT2Info)[4] = m_BiasBalCellsMax;
		((int*)uT2Info)[5] = m_DummyCellsPerFrame;
		((int*)uT2Info)[6] = m_SamplesPerFrame;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2ParamInfo* uT2Info)
	{
		m_TotalCellsPerFrame = *(int*)uT2Info;
		m_L1CellsPerFrame = ((int*)uT2Info)[1];
		m_AuxCellsPerFrame = ((int*)uT2Info)[2];
		m_BiasBalCellsPerFrame = ((int*)uT2Info)[3];
		m_BiasBalCellsMax = ((int*)uT2Info)[4];
		m_DummyCellsPerFrame = ((int*)uT2Info)[5];
		m_SamplesPerFrame = ((int*)uT2Info)[6];
	}
}
