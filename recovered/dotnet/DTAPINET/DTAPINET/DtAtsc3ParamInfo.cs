using System.Collections.Generic;
using Dtapi;
using std;

namespace DTAPINET;

public class DtAtsc3ParamInfo
{
	public int m_L1BasicNumDataCells;

	public int m_L1DetailNumDataCells;

	public int m_L1DetailNumBytes;

	public int m_PreambleNumSymbols;

	public int m_NumCellsInFirstPreamble;

	public int m_NumCellsInNextPreamble;

	public int m_NumPlpCellsInPreambles;

	public int m_FrameLength;

	public List<DtAtsc3SubframeInfo> m_Subframes;

	public DtAtsc3ParamInfo()
	{
		m_Subframes = new List<DtAtsc3SubframeInfo>();
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3ParamInfo* uA3Info)
	{
		m_L1BasicNumDataCells = *(int*)uA3Info;
		m_L1DetailNumDataCells = ((int*)uA3Info)[1];
		m_L1DetailNumBytes = ((int*)uA3Info)[2];
		m_PreambleNumSymbols = ((int*)uA3Info)[3];
		m_NumCellsInFirstPreamble = ((int*)uA3Info)[4];
		m_NumCellsInNextPreamble = ((int*)uA3Info)[5];
		m_NumPlpCellsInPreambles = ((int*)uA3Info)[6];
		m_FrameLength = ((int*)uA3Info)[7];
		m_Subframes.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtAtsc3SubframeInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframeInfo_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtAtsc3SubframeInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframeInfo_003E_0020_003E*)((byte*)uA3Info + 32);
		if (0u >= (uint)((((int*)ptr)[1] - *(int*)ptr) / 24))
		{
			return;
		}
		int num2 = 0;
		do
		{
			DtAtsc3SubframeInfo dtAtsc3SubframeInfo = new DtAtsc3SubframeInfo();
			dtAtsc3SubframeInfo.m_TotalNumDataCells = *(int*)(num2 + ((int*)uA3Info)[8]);
			dtAtsc3SubframeInfo.m_NumCellsInDataSym = *(int*)(((int*)uA3Info)[8] + num2 + 4);
			dtAtsc3SubframeInfo.m_NumCellsInSbsSym = *(int*)(((int*)uA3Info)[8] + num2 + 8);
			uint num3 = 0u;
			vector_003CDtapi_003A_003ADtAtsc3PlpInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpInfo_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3PlpInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpInfo_003E_0020_003E*)(((int*)uA3Info)[8] + num2 + 12);
			if (0u < (uint)((((int*)ptr2)[1] - *(int*)ptr2) / 12))
			{
				int num4 = 0;
				do
				{
					DtAtsc3PlpInfo dtAtsc3PlpInfo = new DtAtsc3PlpInfo();
					dtAtsc3PlpInfo.m_BbFramerate = *(int*)(*(int*)(((int*)uA3Info)[8] + num2 + 12) + num4);
					dtAtsc3PlpInfo.m_PlpBitrate = *(int*)(num4 + *(int*)(((int*)uA3Info)[8] + num2 + 12) + 4);
					dtAtsc3PlpInfo.m_PlpSize = *(int*)(num4 + *(int*)(((int*)uA3Info)[8] + num2 + 12) + 8);
					dtAtsc3SubframeInfo.m_Plps.Add(dtAtsc3PlpInfo);
					num3++;
					num4 += 12;
					ptr2 = (vector_003CDtapi_003A_003ADtAtsc3PlpInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpInfo_003E_0020_003E*)(((int*)uA3Info)[8] + num2 + 12);
				}
				while (num3 < (uint)((((int*)ptr2)[1] - *(int*)ptr2) / 12));
			}
			m_Subframes.Add(dtAtsc3SubframeInfo);
			num++;
			num2 += 24;
			ptr = (vector_003CDtapi_003A_003ADtAtsc3SubframeInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframeInfo_003E_0020_003E*)((byte*)uA3Info + 32);
		}
		while (num < (uint)((((int*)ptr)[1] - *(int*)ptr) / 24));
	}
}
