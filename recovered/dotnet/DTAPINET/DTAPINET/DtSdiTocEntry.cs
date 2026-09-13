using Dtapi;

namespace DTAPINET;

public class DtSdiTocEntry
{
	internal unsafe Dtapi.DtSdiTocEntry* m_pSdiTocEntry;

	public unsafe int TocType => ((int*)m_pSdiTocEntry)[1];

	public unsafe int StartOffset => ((int*)m_pSdiTocEntry)[4];

	public unsafe int NumSymbols => ((int*)m_pSdiTocEntry)[5];

	public unsafe int Line => ((int*)m_pSdiTocEntry)[2];

	public unsafe int Field => ((int*)m_pSdiTocEntry)[3];

	public unsafe int AncType => ((int*)m_pSdiTocEntry)[6];

	public unsafe int AncSecDataId
	{
		get
		{
			Dtapi.DtSdiTocEntry* pSdiTocEntry = m_pSdiTocEntry;
			return (((int*)pSdiTocEntry)[6] == 2) ? ((int*)pSdiTocEntry)[8] : (-1);
		}
	}

	public unsafe int AncNumUserWords => ((int*)m_pSdiTocEntry)[9];

	public unsafe int AncDataId => ((int*)m_pSdiTocEntry)[7];

	public unsafe int AncDataBlockNum
	{
		get
		{
			Dtapi.DtSdiTocEntry* pSdiTocEntry = m_pSdiTocEntry;
			return (((int*)pSdiTocEntry)[6] == 1) ? ((int*)pSdiTocEntry)[8] : (-1);
		}
	}

	private DtSdiTocEntry()
	{
	}

	internal unsafe DtSdiTocEntry(Dtapi.DtSdiTocEntry* pSdiTocEntry)
	{
		m_pSdiTocEntry = pSdiTocEntry;
	}
}
