using System.Collections.Generic;

namespace DTAPINET;

public class DtAtsc3SubframeInfo
{
	public int m_TotalNumDataCells;

	public int m_NumCellsInDataSym;

	public int m_NumCellsInSbsSym;

	public List<DtAtsc3PlpInfo> m_Plps;

	public DtAtsc3SubframeInfo()
	{
		m_Plps = new List<DtAtsc3PlpInfo>();
	}
}
