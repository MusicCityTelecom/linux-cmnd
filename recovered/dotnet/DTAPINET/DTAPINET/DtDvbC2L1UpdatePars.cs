using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2L1UpdatePars
{
	public int m_NumFrames;

	public List<DtDvbC2L1UpdateDSlicePars> m_DSlices;

	public bool m_EarlyWarningSystem;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2L1UpdatePars* uC2UpdPars)
	{
		*(int*)uC2UpdPars = m_NumFrames;
		Dtapi.DtDvbC2L1UpdatePars* ptr = (Dtapi.DtDvbC2L1UpdatePars*)((byte*)uC2UpdPars + 4);
		vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E*)ptr;
		Dtapi.DtDvbC2L1UpdateDSlicePars* last = (Dtapi.DtDvbC2L1UpdateDSlicePars*)(int)((uint*)ptr2)[1];
		global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020Dtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E((Dtapi.DtDvbC2L1UpdateDSlicePars*)(int)(*(uint*)ptr2), last, (allocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E*)ptr2);
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_DSlices.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2L1UpdateDSlicePars dtDvbC2L1UpdateDSlicePars);
			do
			{
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2L1UpdateDSlicePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdateDSlicePars, 12)) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2L1UpdateDSlicePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdateDSlicePars, 16)) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2L1UpdateDSlicePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdateDSlicePars, 20)) = 0;
				try
				{
					m_DSlices[num].ConvertToUnmgd(&dtDvbC2L1UpdateDSlicePars);
					global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2L1UpdateDSlicePars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E*)ptr, &dtDvbC2L1UpdateDSlicePars);
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2L1UpdateDSlicePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2L1UpdateDSlicePars_002E_007Bdtor_007D), &dtDvbC2L1UpdateDSlicePars);
					throw;
				}
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdateDSlicePars, 12)));
				num++;
			}
			while (num < m_DSlices.Count);
		}
		((sbyte*)uC2UpdPars)[16] = (m_EarlyWarningSystem ? ((sbyte)1) : ((sbyte)0));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2L1UpdatePars* uC2UpdPars)
	{
		m_NumFrames = *(int*)uC2UpdPars;
		m_DSlices.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E*)((byte*)uC2UpdPars + 4);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 24))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E*)((byte*)uC2UpdPars + 4);
			vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtDvbC2L1UpdateDSlicePars dtDvbC2L1UpdateDSlicePars = new DtDvbC2L1UpdateDSlicePars();
				dtDvbC2L1UpdateDSlicePars.ConvertFromUnmgd((Dtapi.DtDvbC2L1UpdateDSlicePars*)(((int*)uC2UpdPars)[1] + num2));
				m_DSlices.Add(dtDvbC2L1UpdateDSlicePars);
				num++;
				num2 += 24;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 24));
		}
		m_EarlyWarningSystem = ((bool*)uC2UpdPars)[16];
	}

	public DtDvbC2L1UpdatePars()
	{
		m_DSlices = new List<DtDvbC2L1UpdateDSlicePars>();
	}
}
