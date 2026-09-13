using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2L1UpdateDSlicePars
{
	public bool m_Enable;

	public int m_OffsetLeft;

	public int m_OffsetRight;

	public List<DtDvbC2L1UpdatePlpPars> m_Plps;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2L1UpdateDSlicePars* uL1UpdPars)
	{
		*(bool*)uL1UpdPars = m_Enable;
		((int*)uL1UpdPars)[1] = m_OffsetLeft;
		((int*)uL1UpdPars)[2] = m_OffsetRight;
		Dtapi.DtDvbC2L1UpdateDSlicePars* ptr = (Dtapi.DtDvbC2L1UpdateDSlicePars*)((byte*)uL1UpdPars + 12);
		vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Plps.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2L1UpdatePlpPars dtDvbC2L1UpdatePlpPars2);
			do
			{
				DtDvbC2L1UpdatePlpPars dtDvbC2L1UpdatePlpPars = m_Plps[num];
				*(bool*)(&dtDvbC2L1UpdatePlpPars2) = dtDvbC2L1UpdatePlpPars.m_Enable;
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2L1UpdatePlpPars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E*)ptr, &dtDvbC2L1UpdatePlpPars2);
				num++;
			}
			while (num < m_Plps.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2L1UpdateDSlicePars* uL1UpdPars)
	{
		m_Enable = *(bool*)uL1UpdPars;
		m_OffsetLeft = ((int*)uL1UpdPars)[1];
		m_OffsetRight = ((int*)uL1UpdPars)[2];
		m_Plps.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E*)((byte*)uL1UpdPars + 12);
		if (0u < (uint)(((int*)ptr)[1] - *(int*)ptr))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E*)((byte*)uL1UpdPars + 12);
			vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E*)((byte*)ptr + 4);
			do
			{
				DtDvbC2L1UpdatePlpPars dtDvbC2L1UpdatePlpPars = new DtDvbC2L1UpdatePlpPars();
				dtDvbC2L1UpdatePlpPars.m_Enable = *(bool*)(int)((uint)((int*)uL1UpdPars)[3] + num);
				m_Plps.Add(dtDvbC2L1UpdatePlpPars);
				num++;
			}
			while (num < (uint)(*(int*)ptr2 - *(int*)ptr));
		}
	}

	public DtDvbC2L1UpdateDSlicePars()
	{
		m_Plps = new List<DtDvbC2L1UpdatePlpPars>();
	}
}
