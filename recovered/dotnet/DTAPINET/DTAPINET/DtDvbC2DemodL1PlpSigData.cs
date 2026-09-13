using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2DemodL1PlpSigData
{
	public int m_NumPlps;

	public List<DtDvbC2DemodL1PlpSigDataPlp> m_Plps;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2DemodL1PlpSigData* uC2L1Pars)
	{
		*(int*)uC2L1Pars = m_NumPlps;
		Dtapi.DtDvbC2DemodL1PlpSigData* ptr = (Dtapi.DtDvbC2DemodL1PlpSigData*)((byte*)uC2L1Pars + 4);
		vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Plps.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1PlpSigDataPlp dtDvbC2DemodL1PlpSigDataPlp);
			do
			{
				global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1PlpSigDataPlp_002E_007Bctor_007D(&dtDvbC2DemodL1PlpSigDataPlp);
				m_Plps[num].ConvertToUnmgd(&dtDvbC2DemodL1PlpSigDataPlp);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E*)ptr, &dtDvbC2DemodL1PlpSigDataPlp);
				num++;
			}
			while (num < m_Plps.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2DemodL1PlpSigData* uC2L1Pars)
	{
		m_NumPlps = *(int*)uC2L1Pars;
		m_Plps.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E*)((byte*)uC2L1Pars + 4);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 20))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E*)((byte*)uC2L1Pars + 4);
			vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtDvbC2DemodL1PlpSigDataPlp dtDvbC2DemodL1PlpSigDataPlp = new DtDvbC2DemodL1PlpSigDataPlp();
				dtDvbC2DemodL1PlpSigDataPlp.ConvertFromUnmgd((Dtapi.DtDvbC2DemodL1PlpSigDataPlp*)(((int*)uC2L1Pars)[1] + num2));
				m_Plps.Add(dtDvbC2DemodL1PlpSigDataPlp);
				num++;
				num2 += 20;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 20));
		}
	}

	public unsafe DtDvbC2DemodL1PlpSigData()
	{
		m_Plps = new List<DtDvbC2DemodL1PlpSigDataPlp>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1PlpSigData dtDvbC2DemodL1PlpSigData);
		global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1PlpSigData_002E_007Bctor_007D(&dtDvbC2DemodL1PlpSigData);
		try
		{
			ConvertFromUnmgd(&dtDvbC2DemodL1PlpSigData);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2DemodL1PlpSigData*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1PlpSigData_002E_007Bdtor_007D), &dtDvbC2DemodL1PlpSigData);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1PlpSigData, 4)));
	}
}
