using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2Pars
{
	public int m_Bandwidth;

	public int m_NetworkId;

	public int m_C2SystemId;

	public int m_StartFrequency;

	public int m_C2Bandwidth;

	public int m_GuardInterval;

	public bool m_ReservedTone;

	public bool m_EarlyWarningSystem;

	public int m_C2Version;

	public int m_L1TiMode;

	public int m_NumDSlices;

	public DtDvbC2DSlicePars[] m_DSlices;

	public int m_NumNotches;

	public DtDvbC2NotchPars[] m_Notches;

	public int m_NumPlpInputs;

	public DtPlpInpPars[] m_PlpInputs;

	public DtDvbC2PaprPars m_PaprPars;

	public DtVirtualOutPars m_VirtOutput;

	public DtTestPointOutPars m_TpOutput;

	public int m_OutpFreqOffset;

	public int m_OutpBandwidth;

	public List<DtDvbC2L1UpdatePars> m_L1Updates;

	public int m_L1P2ChangeCtr;

	public bool m_NotchTestEnable;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2Pars* uC2Pars)
	{
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002EInit(uC2Pars);
		*(int*)uC2Pars = m_Bandwidth;
		((int*)uC2Pars)[1] = m_NetworkId;
		((int*)uC2Pars)[2] = m_C2SystemId;
		((int*)uC2Pars)[3] = m_StartFrequency;
		((int*)uC2Pars)[4] = m_C2Bandwidth;
		((int*)uC2Pars)[5] = m_GuardInterval;
		((sbyte*)uC2Pars)[24] = (m_ReservedTone ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uC2Pars)[25] = (m_EarlyWarningSystem ? ((sbyte)1) : ((sbyte)0));
		((int*)uC2Pars)[7] = m_C2Version;
		((int*)uC2Pars)[8] = m_L1TiMode;
		((int*)uC2Pars)[9] = m_NumDSlices;
		((int*)uC2Pars)[2815] = m_NumNotches;
		((int*)uC2Pars)[2848] = m_NumPlpInputs;
		((int*)uC2Pars)[16633] = m_OutpFreqOffset;
		((int*)uC2Pars)[16634] = m_OutpBandwidth;
		((int*)uC2Pars)[16638] = m_L1P2ChangeCtr;
		((sbyte*)uC2Pars)[66556] = (m_NotchTestEnable ? ((sbyte)1) : ((sbyte)0));
		int num = 0;
		Dtapi.DtDvbC2Pars* ptr = (Dtapi.DtDvbC2Pars*)((byte*)uC2Pars + 40);
		do
		{
			m_DSlices[num].ConvertToUnmgd((Dtapi.DtDvbC2DSlicePars*)ptr);
			num++;
			ptr = (Dtapi.DtDvbC2Pars*)((byte*)ptr + 44);
		}
		while (num < 255);
		int num2 = 0;
		Dtapi.DtDvbC2Pars* ptr2 = (Dtapi.DtDvbC2Pars*)((byte*)uC2Pars + 11264);
		do
		{
			DtDvbC2NotchPars dtDvbC2NotchPars = m_Notches[num2];
			*(int*)ptr2 = dtDvbC2NotchPars.m_Start;
			((int*)ptr2)[1] = dtDvbC2NotchPars.m_Width;
			num2++;
			ptr2 = (Dtapi.DtDvbC2Pars*)((byte*)ptr2 + 8);
		}
		while (num2 < 16);
		int num3 = 0;
		Dtapi.DtDvbC2Pars* ptr3 = (Dtapi.DtDvbC2Pars*)((byte*)uC2Pars + 11396);
		do
		{
			m_PlpInputs[num3].ConvertToUnmgd((Dtapi.DtPlpInpPars*)ptr3);
			num3++;
			ptr3 = (Dtapi.DtDvbC2Pars*)((byte*)ptr3 + 216);
		}
		while (num3 < 255);
		Dtapi.DtDvbC2Pars* ptr4 = (Dtapi.DtDvbC2Pars*)((byte*)uC2Pars + 66540);
		vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E* ptr5 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E*)ptr4;
		Dtapi.DtDvbC2L1UpdatePars* last = (Dtapi.DtDvbC2L1UpdatePars*)(int)((uint*)ptr5)[1];
		global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020Dtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E((Dtapi.DtDvbC2L1UpdatePars*)(int)(*(uint*)ptr5), last, (allocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E*)ptr5);
		((int*)ptr5)[1] = *(int*)ptr5;
		int num4 = 0;
		if (0 < m_L1Updates.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2L1UpdatePars dtDvbC2L1UpdatePars);
			do
			{
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2L1UpdatePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdatePars, 4)) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2L1UpdatePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdatePars, 8)) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2L1UpdatePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdatePars, 12)) = 0;
				try
				{
					m_L1Updates[num4].ConvertToUnmgd(&dtDvbC2L1UpdatePars);
					global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2L1UpdatePars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E*)ptr4, &dtDvbC2L1UpdatePars);
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2L1UpdatePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2L1UpdatePars_002E_007Bdtor_007D), &dtDvbC2L1UpdatePars);
					throw;
				}
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdateDSlicePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2L1UpdatePars, 4)));
				num4++;
			}
			while (num4 < m_L1Updates.Count);
		}
		m_PaprPars.ConvertToUnmgd((Dtapi.DtDvbC2PaprPars*)((byte*)uC2Pars + 66480));
		m_VirtOutput.ConvertToUnmgd((Dtapi.DtVirtualOutPars*)((byte*)uC2Pars + 66504));
		m_TpOutput.ConvertToUnmgd((Dtapi.DtTestPointOutPars*)((byte*)uC2Pars + 66520));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2Pars* uC2Pars)
	{
		m_Bandwidth = *(int*)uC2Pars;
		m_NetworkId = ((int*)uC2Pars)[1];
		m_C2SystemId = ((int*)uC2Pars)[2];
		m_StartFrequency = ((int*)uC2Pars)[3];
		m_C2Bandwidth = ((int*)uC2Pars)[4];
		m_GuardInterval = ((int*)uC2Pars)[5];
		m_ReservedTone = ((bool*)uC2Pars)[24];
		m_EarlyWarningSystem = ((bool*)uC2Pars)[25];
		m_C2Version = ((int*)uC2Pars)[7];
		m_L1TiMode = ((int*)uC2Pars)[8];
		m_NumDSlices = ((int*)uC2Pars)[9];
		m_NumNotches = ((int*)uC2Pars)[2815];
		m_NumPlpInputs = ((int*)uC2Pars)[2848];
		m_OutpFreqOffset = ((int*)uC2Pars)[16633];
		m_OutpBandwidth = ((int*)uC2Pars)[16634];
		m_L1P2ChangeCtr = ((int*)uC2Pars)[16638];
		m_NotchTestEnable = ((bool*)uC2Pars)[66556];
		int num = 0;
		Dtapi.DtDvbC2Pars* ptr = (Dtapi.DtDvbC2Pars*)((byte*)uC2Pars + 40);
		do
		{
			m_DSlices[num].ConvertFromUnmgd((Dtapi.DtDvbC2DSlicePars*)ptr);
			num++;
			ptr = (Dtapi.DtDvbC2Pars*)((byte*)ptr + 44);
		}
		while (num < 255);
		int num2 = 0;
		Dtapi.DtDvbC2Pars* ptr2 = (Dtapi.DtDvbC2Pars*)((byte*)uC2Pars + 11264);
		do
		{
			DtDvbC2NotchPars dtDvbC2NotchPars = m_Notches[num2];
			dtDvbC2NotchPars.m_Start = *(int*)ptr2;
			dtDvbC2NotchPars.m_Width = ((int*)ptr2)[1];
			num2++;
			ptr2 = (Dtapi.DtDvbC2Pars*)((byte*)ptr2 + 8);
		}
		while (num2 < 16);
		int num3 = 0;
		Dtapi.DtDvbC2Pars* ptr3 = (Dtapi.DtDvbC2Pars*)((byte*)uC2Pars + 11396);
		do
		{
			m_PlpInputs[num3].ConvertFromUnmgd((Dtapi.DtPlpInpPars*)ptr3);
			num3++;
			ptr3 = (Dtapi.DtDvbC2Pars*)((byte*)ptr3 + 216);
		}
		while (num3 < 255);
		m_L1Updates.Clear();
		uint num4 = 0u;
		vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E* ptr4 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E*)((byte*)uC2Pars + 66540);
		if (0u < (uint)((((int*)ptr4)[1] - *(int*)ptr4) / 20))
		{
			ptr4 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E*)((byte*)uC2Pars + 66540);
			vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E* ptr5 = (vector_003CDtapi_003A_003ADtDvbC2L1UpdatePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2L1UpdatePars_003E_0020_003E*)((byte*)ptr4 + 4);
			int num5 = 0;
			do
			{
				DtDvbC2L1UpdatePars dtDvbC2L1UpdatePars = new DtDvbC2L1UpdatePars();
				dtDvbC2L1UpdatePars.ConvertFromUnmgd((Dtapi.DtDvbC2L1UpdatePars*)(num5 + ((int*)uC2Pars)[16635]));
				m_L1Updates.Add(dtDvbC2L1UpdatePars);
				num4++;
				num5 += 20;
			}
			while (num4 < (uint)((*(int*)ptr5 - *(int*)ptr4) / 20));
		}
		m_PaprPars.ConvertFromUnmgd((Dtapi.DtDvbC2PaprPars*)((byte*)uC2Pars + 66480));
		m_VirtOutput.ConvertFromUnmgd((Dtapi.DtVirtualOutPars*)((byte*)uC2Pars + 66504));
		m_TpOutput.m_Enabled = ((bool*)uC2Pars)[66520];
	}

	public DtDvbC2Pars()
	{
		m_DSlices = new DtDvbC2DSlicePars[255];
		int num = 0;
		do
		{
			m_DSlices[num] = new DtDvbC2DSlicePars();
			num++;
		}
		while (num < 255);
		m_Notches = new DtDvbC2NotchPars[16];
		int num2 = 0;
		do
		{
			m_Notches[num2] = new DtDvbC2NotchPars();
			num2++;
		}
		while (num2 < 16);
		m_PlpInputs = new DtPlpInpPars[255];
		int num3 = 0;
		do
		{
			m_PlpInputs[num3] = new DtPlpInpPars();
			num3++;
		}
		while (num3 < 255);
		m_L1Updates = new List<DtDvbC2L1UpdatePars>();
		m_PaprPars = new DtDvbC2PaprPars();
		m_VirtOutput = new DtVirtualOutPars();
		m_TpOutput = new DtTestPointOutPars();
	}

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		try
		{
			global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002EInit(&dtDvbC2Pars);
			ConvertFromUnmgd(&dtDvbC2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
	}

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtDvbC2Pars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002ECheckValidity(&dtDvbC2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return result;
	}

	public unsafe DTAPI_RESULT GetParamInfo(ref DtDvbC2ParamInfo C2Info)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtDvbC2Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2ParamInfo dtDvbC2ParamInfo);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002EGetParamInfo(&dtDvbC2Pars, &dtDvbC2ParamInfo);
			C2Info.ConvertFromUnmgd(&dtDvbC2ParamInfo);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtDvbC2Pars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars2);
			global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars2);
			try
			{
				ConvertToUnmgd(&dtDvbC2Pars);
				Rhs.ConvertToUnmgd(&dtDvbC2Pars2);
				result = global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_003D_003D(&dtDvbC2Pars, &dtDvbC2Pars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars2);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars2);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtDvbC2Pars Rhs)
	{
		return !op_Equality(Rhs);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool IsEqual(DtDvbC2Pars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars2);
			global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars2);
			try
			{
				ConvertToUnmgd(&dtDvbC2Pars);
				Rhs.ConvertToUnmgd(&dtDvbC2Pars2);
				result = global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002EIsEqual(&dtDvbC2Pars, &dtDvbC2Pars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars2);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars2);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return result;
	}
}
