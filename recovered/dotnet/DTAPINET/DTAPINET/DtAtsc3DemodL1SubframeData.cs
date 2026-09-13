using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtAtsc3DemodL1SubframeData
{
	public bool m_Mimo;

	public int m_Miso;

	public int m_FftSize;

	public int m_ReducedCarriers;

	public int m_GuardInterval;

	public int m_NumOfdmSymbols;

	public int m_PilotPattern;

	public int m_PilotBoost;

	public bool m_SbsFirst;

	public bool m_SbsLast;

	public int m_Multiplex;

	public bool m_FreqInterleaver;

	public List<DtAtsc3DemodL1PlpData> m_Plps;

	public DtAtsc3DemodL1SubframeData()
	{
		m_Plps = new List<DtAtsc3DemodL1PlpData>();
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3DemodL1SubframeData* uFrameData)
	{
		*(bool*)uFrameData = m_Mimo;
		((int*)uFrameData)[1] = m_Miso;
		((int*)uFrameData)[2] = m_FftSize;
		((int*)uFrameData)[3] = m_ReducedCarriers;
		((int*)uFrameData)[4] = m_GuardInterval;
		((int*)uFrameData)[5] = m_NumOfdmSymbols;
		((int*)uFrameData)[6] = m_PilotPattern;
		((int*)uFrameData)[7] = m_PilotBoost;
		((sbyte*)uFrameData)[32] = (m_SbsFirst ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uFrameData)[33] = (m_SbsLast ? ((sbyte)1) : ((sbyte)0));
		((int*)uFrameData)[9] = m_Multiplex;
		((sbyte*)uFrameData)[40] = (m_FreqInterleaver ? ((sbyte)1) : ((sbyte)0));
		Dtapi.DtAtsc3DemodL1SubframeData* ptr = (Dtapi.DtAtsc3DemodL1SubframeData*)((byte*)uFrameData + 48);
		vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Plps.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3DemodL1PlpData dtAtsc3DemodL1PlpData);
			do
			{
				m_Plps[num].ConvertToUnmgd(&dtAtsc3DemodL1PlpData);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtAtsc3DemodL1PlpData_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E*)ptr, &dtAtsc3DemodL1PlpData);
				num++;
			}
			while (num < m_Plps.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3DemodL1SubframeData* uFrameData)
	{
		m_Mimo = *(bool*)uFrameData;
		m_Miso = ((int*)uFrameData)[1];
		m_FftSize = ((int*)uFrameData)[2];
		m_ReducedCarriers = ((int*)uFrameData)[3];
		m_GuardInterval = ((int*)uFrameData)[4];
		m_NumOfdmSymbols = ((int*)uFrameData)[5];
		m_PilotPattern = ((int*)uFrameData)[6];
		m_PilotBoost = ((int*)uFrameData)[7];
		m_SbsFirst = ((bool*)uFrameData)[32];
		m_SbsLast = ((bool*)uFrameData)[33];
		m_Multiplex = ((int*)uFrameData)[9];
		m_FreqInterleaver = ((bool*)uFrameData)[40];
		m_Plps.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E*)((byte*)uFrameData + 48);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 200))
		{
			ptr = (vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E*)((byte*)uFrameData + 48);
			vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtAtsc3DemodL1PlpData dtAtsc3DemodL1PlpData = new DtAtsc3DemodL1PlpData();
				dtAtsc3DemodL1PlpData.ConvertFromUnmgd((Dtapi.DtAtsc3DemodL1PlpData*)(((int*)uFrameData)[12] + num2));
				m_Plps.Add(dtAtsc3DemodL1PlpData);
				num++;
				num2 += 200;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 200));
		}
	}
}
