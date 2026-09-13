using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtAtsc3SubframePars
{
	public int m_Miso;

	public int m_MisoNumTx;

	public int m_MisoTxIndex;

	public int m_FftSize;

	public int m_ReducedCarriers;

	public int m_GuardInterval;

	public int m_PilotPattern;

	public int m_PilotBoost;

	public bool m_SbsFirst;

	public bool m_SbsLast;

	public int m_NumOfdmSymbols;

	public bool m_FreqInterleaver;

	public List<DtAtsc3PlpPars> m_Plps;

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3SubframePars dtAtsc3SubframePars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bctor_007D(&dtAtsc3SubframePars);
		try
		{
			global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002EInit(&dtAtsc3SubframePars);
			ConvertFromUnmgd(&dtAtsc3SubframePars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3SubframePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bdtor_007D), &dtAtsc3SubframePars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3SubframePars, 44)));
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtAtsc3SubframePars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3SubframePars dtAtsc3SubframePars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bctor_007D(&dtAtsc3SubframePars);
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3SubframePars dtAtsc3SubframePars2);
			global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bctor_007D(&dtAtsc3SubframePars2);
			try
			{
				ConvertToUnmgd(&dtAtsc3SubframePars);
				Rhs.ConvertToUnmgd(&dtAtsc3SubframePars2);
				result = global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_003D_003D(&dtAtsc3SubframePars, &dtAtsc3SubframePars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3SubframePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bdtor_007D), &dtAtsc3SubframePars2);
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3SubframePars2, 44)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3SubframePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bdtor_007D), &dtAtsc3SubframePars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3SubframePars, 44)));
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtAtsc3SubframePars Rhs)
	{
		return !op_Equality(Rhs);
	}

	[SpecialName]
	public unsafe DtAtsc3SubframePars op_Assign(DtAtsc3SubframePars Rhs)
	{
		if (this != Rhs)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3SubframePars dtAtsc3SubframePars);
			global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bctor_007D(&dtAtsc3SubframePars);
			try
			{
				Rhs.ConvertToUnmgd(&dtAtsc3SubframePars);
				ConvertFromUnmgd(&dtAtsc3SubframePars);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3SubframePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bdtor_007D), &dtAtsc3SubframePars);
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3SubframePars, 44)));
		}
		return this;
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3SubframePars* uA3Pars)
	{
		*(int*)uA3Pars = m_Miso;
		((int*)uA3Pars)[1] = m_MisoNumTx;
		((int*)uA3Pars)[2] = m_MisoTxIndex;
		((int*)uA3Pars)[3] = m_FftSize;
		((int*)uA3Pars)[4] = m_ReducedCarriers;
		((int*)uA3Pars)[5] = m_GuardInterval;
		((int*)uA3Pars)[6] = m_PilotPattern;
		((int*)uA3Pars)[7] = m_PilotBoost;
		((sbyte*)uA3Pars)[32] = (m_SbsFirst ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uA3Pars)[33] = (m_SbsLast ? ((sbyte)1) : ((sbyte)0));
		((int*)uA3Pars)[9] = m_NumOfdmSymbols;
		((sbyte*)uA3Pars)[40] = (m_FreqInterleaver ? ((sbyte)1) : ((sbyte)0));
		Dtapi.DtAtsc3SubframePars* ptr = (Dtapi.DtAtsc3SubframePars*)((byte*)uA3Pars + 44);
		vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Plps.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3PlpPars dtAtsc3PlpPars);
			do
			{
				global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_007Bctor_007D(&dtAtsc3PlpPars);
				m_Plps[num].ConvertToUnmgd(&dtAtsc3PlpPars);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E_002Eemplace_back_003Cclass_0020Dtapi_003A_003ADtAtsc3PlpPars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)ptr, &dtAtsc3PlpPars);
				num++;
			}
			while (num < m_Plps.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3SubframePars* uA3Pars)
	{
		m_Miso = *(int*)uA3Pars;
		m_MisoNumTx = ((int*)uA3Pars)[1];
		m_MisoTxIndex = ((int*)uA3Pars)[2];
		m_FftSize = ((int*)uA3Pars)[3];
		m_ReducedCarriers = ((int*)uA3Pars)[4];
		m_GuardInterval = ((int*)uA3Pars)[5];
		m_PilotPattern = ((int*)uA3Pars)[6];
		m_PilotBoost = ((int*)uA3Pars)[7];
		m_SbsFirst = ((bool*)uA3Pars)[32];
		m_SbsLast = ((bool*)uA3Pars)[33];
		m_NumOfdmSymbols = ((int*)uA3Pars)[9];
		m_FreqInterleaver = ((bool*)uA3Pars)[40];
		m_Plps.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)((byte*)uA3Pars + 44);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 124))
		{
			ptr = (vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)((byte*)uA3Pars + 44);
			vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtAtsc3PlpPars dtAtsc3PlpPars = new DtAtsc3PlpPars();
				dtAtsc3PlpPars.ConvertFromUnmgd((Dtapi.DtAtsc3PlpPars*)(((int*)uA3Pars)[11] + num2));
				m_Plps.Add(dtAtsc3PlpPars);
				num++;
				num2 += 124;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 124));
		}
	}

	public unsafe DtAtsc3SubframePars()
	{
		m_Plps = new List<DtAtsc3PlpPars>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3SubframePars dtAtsc3SubframePars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bctor_007D(&dtAtsc3SubframePars);
		try
		{
			ConvertFromUnmgd(&dtAtsc3SubframePars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3SubframePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bdtor_007D), &dtAtsc3SubframePars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3SubframePars, 44)));
	}
}
