using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2DemodL1Part2Data
{
	public int m_NetworkId;

	public int m_C2SystemId;

	public int m_StartFrequency;

	public int m_C2Bandwidth;

	public int m_GuardInterval;

	public int m_C2FrameLength;

	public int m_L1P2ChangeCtr;

	public int m_ReservedTone;

	public bool m_EarlyWarningSystem;

	public int m_C2Version;

	public int m_NumDSlices;

	public List<DtDvbC2DemodL1Part2DSlice> m_DSlices;

	public int m_NumNotches;

	public List<DtDvbC2NotchPars> m_Notches;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2DemodL1Part2Data* uC2L1Pars)
	{
		*(int*)uC2L1Pars = m_NetworkId;
		((int*)uC2L1Pars)[1] = m_C2SystemId;
		((int*)uC2L1Pars)[2] = m_StartFrequency;
		((int*)uC2L1Pars)[3] = m_C2Bandwidth;
		((int*)uC2L1Pars)[4] = m_GuardInterval;
		((int*)uC2L1Pars)[5] = m_C2FrameLength;
		((int*)uC2L1Pars)[6] = m_L1P2ChangeCtr;
		((int*)uC2L1Pars)[7] = m_ReservedTone;
		((sbyte*)uC2L1Pars)[32] = (m_EarlyWarningSystem ? ((sbyte)1) : ((sbyte)0));
		((int*)uC2L1Pars)[9] = m_C2Version;
		((int*)uC2L1Pars)[10] = m_NumDSlices;
		Dtapi.DtDvbC2DemodL1Part2Data* ptr = (Dtapi.DtDvbC2DemodL1Part2Data*)((byte*)uC2L1Pars + 44);
		vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*)ptr;
		Dtapi.DtDvbC2DemodL1Part2DSlice* last = (Dtapi.DtDvbC2DemodL1Part2DSlice*)(int)((uint*)ptr2)[1];
		global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020Dtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E((Dtapi.DtDvbC2DemodL1Part2DSlice*)(int)(*(uint*)ptr2), last, (allocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E*)ptr2);
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_DSlices.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1Part2DSlice dtDvbC2DemodL1Part2DSlice);
			do
			{
				global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2DSlice_002E_007Bctor_007D(&dtDvbC2DemodL1Part2DSlice);
				try
				{
					m_DSlices[num].ConvertToUnmgd(&dtDvbC2DemodL1Part2DSlice);
					global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2DemodL1Part2DSlice_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*)ptr, &dtDvbC2DemodL1Part2DSlice);
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2DemodL1Part2DSlice*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2DSlice_002E_007Bdtor_007D), &dtDvbC2DemodL1Part2DSlice);
					throw;
				}
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2DSlice, 40)));
				num++;
			}
			while (num < m_DSlices.Count);
		}
		((int*)uC2L1Pars)[14] = m_NumNotches;
		Dtapi.DtDvbC2DemodL1Part2Data* ptr3 = (Dtapi.DtDvbC2DemodL1Part2Data*)((byte*)uC2L1Pars + 60);
		vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E* ptr4 = (vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E*)ptr3;
		((int*)ptr4)[1] = *(int*)ptr4;
		int num2 = 0;
		if (0 < m_DSlices.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2NotchPars dtDvbC2NotchPars2);
			do
			{
				DtDvbC2NotchPars dtDvbC2NotchPars = m_Notches[num2];
				*(int*)(&dtDvbC2NotchPars2) = dtDvbC2NotchPars.m_Start;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2NotchPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2NotchPars2, 4)) = dtDvbC2NotchPars.m_Width;
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2NotchPars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E*)ptr3, &dtDvbC2NotchPars2);
				num2++;
			}
			while (num2 < m_DSlices.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2DemodL1Part2Data* uC2L1Pars)
	{
		m_NetworkId = *(int*)uC2L1Pars;
		m_C2SystemId = ((int*)uC2L1Pars)[1];
		m_StartFrequency = ((int*)uC2L1Pars)[2];
		m_C2Bandwidth = ((int*)uC2L1Pars)[3];
		m_GuardInterval = ((int*)uC2L1Pars)[4];
		m_C2FrameLength = ((int*)uC2L1Pars)[5];
		m_L1P2ChangeCtr = ((int*)uC2L1Pars)[6];
		m_ReservedTone = ((int*)uC2L1Pars)[7];
		m_EarlyWarningSystem = ((bool*)uC2L1Pars)[32];
		m_C2Version = ((int*)uC2L1Pars)[9];
		m_NumDSlices = ((int*)uC2L1Pars)[10];
		m_DSlices.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*)((byte*)uC2L1Pars + 44);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 52))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*)((byte*)uC2L1Pars + 44);
			vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtDvbC2DemodL1Part2DSlice dtDvbC2DemodL1Part2DSlice = new DtDvbC2DemodL1Part2DSlice();
				dtDvbC2DemodL1Part2DSlice.ConvertFromUnmgd((Dtapi.DtDvbC2DemodL1Part2DSlice*)(num2 + ((int*)uC2L1Pars)[11]));
				m_DSlices.Add(dtDvbC2DemodL1Part2DSlice);
				num++;
				num2 += 52;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 52));
		}
		m_NumNotches = ((int*)uC2L1Pars)[14];
		m_Notches.Clear();
		uint num3 = 0u;
		vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E*)((byte*)uC2L1Pars + 60);
		if (0u < (uint)(((int*)ptr3)[1] - *(int*)ptr3 >> 3))
		{
			ptr3 = (vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E*)((byte*)uC2L1Pars + 60);
			vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E* ptr4 = (vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E*)((byte*)ptr3 + 4);
			do
			{
				DtDvbC2NotchPars dtDvbC2NotchPars = new DtDvbC2NotchPars();
				Dtapi.DtDvbC2NotchPars* ptr5 = (Dtapi.DtDvbC2NotchPars*)(int)(num3 * 8 + (uint)((int*)uC2L1Pars)[15]);
				dtDvbC2NotchPars.m_Start = *(int*)ptr5;
				dtDvbC2NotchPars.m_Width = ((int*)ptr5)[1];
				m_Notches.Add(dtDvbC2NotchPars);
				num3++;
			}
			while (num3 < (uint)(*(int*)ptr4 - *(int*)ptr3 >> 3));
		}
	}

	public unsafe DtDvbC2DemodL1Part2Data()
	{
		m_DSlices = new List<DtDvbC2DemodL1Part2DSlice>();
		m_Notches = new List<DtDvbC2NotchPars>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1Part2Data dtDvbC2DemodL1Part2Data);
		global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2Data_002E_007Bctor_007D(&dtDvbC2DemodL1Part2Data);
		try
		{
			ConvertFromUnmgd(&dtDvbC2DemodL1Part2Data);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2DemodL1Part2Data*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2Data_002E_007Bdtor_007D), &dtDvbC2DemodL1Part2Data);
			throw;
		}
		try
		{
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2Data, 60)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2Data, 44)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2Data, 44)));
	}
}
