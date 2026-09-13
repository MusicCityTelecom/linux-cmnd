using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtAtsc3Pars
{
	public int m_Bandwidth;

	public int m_MinorVersion;

	public int m_EasWakeup;

	public int m_PreambleFftSize;

	public int m_PreambleGuardInterval;

	public int m_PreamblePilotDx;

	public int m_PreambleReducedCarriers;

	public int m_L1BasicFecMode;

	public int m_L1DetailFecMode;

	public int m_L1DetailAddParity;

	public int m_L1DetailVersion;

	public int m_TxIdInjectLevelCode;

	public int m_TxIdAddress;

	public int m_TimeInfoFlag;

	public int m_TimeSeconds;

	public int m_TimeNanoseconds;

	public bool m_LlsFlag;

	public int m_Papr;

	public int m_FrameLengthMode;

	public int m_FrameLength;

	public int m_NumRf;

	public int[] m_BondedBsId;

	public int m_BsId;

	public List<DtAtsc3SubframePars> m_Subframes;

	public int m_NumPlpInputs;

	public DtPlpInpPars[] m_PlpInputs;

	public DtVirtualOutPars m_VirtOutput;

	public DtTestPointOutPars m_TpOutput;

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		try
		{
			global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002EInit(&dtAtsc3Pars);
			ConvertFromUnmgd(&dtAtsc3Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
	}

	public unsafe DTAPI_RESULT CheckValidity(ref string ParName, ref int SubframeIdx, ref int PlpIdx1, ref int PlpIdx2)
	{
		ParName = "";
		System.Runtime.CompilerServices.Unsafe.SkipInit(out basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E obj);
		global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002E_007Bctor_007D(&obj);
		uint result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
			global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
			try
			{
				ConvertToUnmgd(&dtAtsc3Pars);
				System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
				System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
				System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
				result = global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002ECheckValidity(&dtAtsc3Pars, &obj, &num, &num2, &num3);
				ParName = new string(global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002Ec_str(&obj));
				SubframeIdx = num;
				PlpIdx1 = num2;
				PlpIdx2 = num3;
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
				throw;
			}
			try
			{
				global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002E_007Bdtor_007D), &obj);
			throw;
		}
		try
		{
			global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002E_Tidy_deallocate(&obj);
			return (DTAPI_RESULT)result;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cchar_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cchar_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_0020_003E_002E_007Bdtor_007D), &obj);
			throw;
		}
	}

	public unsafe DTAPI_RESULT CheckValidity(ref int SubframeIdx, ref int PlpIdx1, ref int PlpIdx2)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		uint result;
		try
		{
			ConvertToUnmgd(&dtAtsc3Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
			result = global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002ECheckValidity(&dtAtsc3Pars, &num, &num2, &num3);
			SubframeIdx = num;
			PlpIdx1 = num2;
			PlpIdx2 = num3;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT CheckValidity(ref int SubframeIdx, ref int PlpIdx)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		uint result;
		try
		{
			ConvertToUnmgd(&dtAtsc3Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
			result = global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002ECheckValidity(&dtAtsc3Pars, &num, &num2);
			SubframeIdx = num;
			PlpIdx = num2;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtAtsc3Pars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002ECheckValidity(&dtAtsc3Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		return result;
	}

	public unsafe DTAPI_RESULT GetParamInfo(ref DtAtsc3ParamInfo Atsc3Info)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		uint result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3ParamInfo dtAtsc3ParamInfo);
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtAtsc3ParamInfo, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3ParamInfo, 32)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtAtsc3ParamInfo, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3ParamInfo, 36)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtAtsc3ParamInfo, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3ParamInfo, 40)) = 0;
			try
			{
				ConvertToUnmgd(&dtAtsc3Pars);
				result = global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002EGetParamInfo(&dtAtsc3Pars, &dtAtsc3ParamInfo);
				(Atsc3Info = new DtAtsc3ParamInfo()).ConvertFromUnmgd(&dtAtsc3ParamInfo);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3ParamInfo*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3ParamInfo_002E_007Bdtor_007D), &dtAtsc3ParamInfo);
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframeInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframeInfo_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframeInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframeInfo_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3ParamInfo, 32)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		return (DTAPI_RESULT)result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtAtsc3Pars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars2);
			global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars2);
			try
			{
				ConvertToUnmgd(&dtAtsc3Pars);
				Rhs.ConvertToUnmgd(&dtAtsc3Pars2);
				result = global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_003D_003D(&dtAtsc3Pars, &dtAtsc3Pars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars2);
				throw;
			}
			try
			{
				global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars2, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars2, 116)));
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars2, 116)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtAtsc3Pars Rhs)
	{
		return !op_Equality(Rhs);
	}

	[SpecialName]
	public unsafe DtAtsc3Pars op_Assign(DtAtsc3Pars Rhs)
	{
		if (this != Rhs)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
			global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
			try
			{
				Rhs.ConvertToUnmgd(&dtAtsc3Pars);
				ConvertFromUnmgd(&dtAtsc3Pars);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
				throw;
			}
			try
			{
				global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		}
		return this;
	}

	public unsafe DTAPI_RESULT ToXml(ref string XmlString)
	{
		XmlString = "";
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		DTAPI_RESULT dTAPI_RESULT;
		try
		{
			ConvertToUnmgd(&dtAtsc3Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E obj);
			global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_007Bctor_007D(&obj);
			try
			{
				dTAPI_RESULT = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002EToXml(&dtAtsc3Pars, &obj);
				if (dTAPI_RESULT == DTAPI_RESULT.OK)
				{
					XmlString = new string(global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002Ec_str(&obj));
				}
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_007Bdtor_007D), &obj);
				throw;
			}
			try
			{
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_Tidy_deallocate(&obj);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E_002E_007Bdtor_007D), &obj);
				throw;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		return dTAPI_RESULT;
	}

	public unsafe DTAPI_RESULT FromXml(string XmlString)
	{
		fixed (char* ptr = &XmlString.ToCharArray()[0])
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E obj);
			global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_007Bctor_007D(&obj, ptr);
			DTAPI_RESULT dTAPI_RESULT;
			try
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
				global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
				try
				{
					dTAPI_RESULT = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002EFromXml(&dtAtsc3Pars, &obj);
					if (dTAPI_RESULT == DTAPI_RESULT.OK)
					{
						ConvertFromUnmgd(&dtAtsc3Pars);
					}
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
					throw;
				}
				try
				{
					global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
					throw;
				}
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_007Bdtor_007D), &obj);
				throw;
			}
			try
			{
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_Tidy_deallocate(&obj);
				return dTAPI_RESULT;
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E_002E_007Bdtor_007D), &obj);
				throw;
			}
		}
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3Pars* uA3Pars)
	{
		*(int*)uA3Pars = m_Bandwidth;
		((int*)uA3Pars)[1] = m_MinorVersion;
		((int*)uA3Pars)[2] = m_EasWakeup;
		((int*)uA3Pars)[3] = m_PreambleFftSize;
		((int*)uA3Pars)[4] = m_PreambleGuardInterval;
		((int*)uA3Pars)[5] = m_PreamblePilotDx;
		((int*)uA3Pars)[6] = m_PreambleReducedCarriers;
		((int*)uA3Pars)[7] = m_L1BasicFecMode;
		((int*)uA3Pars)[8] = m_L1DetailFecMode;
		((int*)uA3Pars)[9] = m_L1DetailAddParity;
		((int*)uA3Pars)[10] = m_L1DetailVersion;
		((int*)uA3Pars)[11] = m_TxIdInjectLevelCode;
		((int*)uA3Pars)[12] = m_TxIdAddress;
		((int*)uA3Pars)[13] = m_TimeInfoFlag;
		((int*)uA3Pars)[14] = m_TimeSeconds;
		((int*)uA3Pars)[15] = m_TimeNanoseconds;
		((sbyte*)uA3Pars)[64] = (m_LlsFlag ? ((sbyte)1) : ((sbyte)0));
		((int*)uA3Pars)[17] = m_Papr;
		((int*)uA3Pars)[18] = m_FrameLengthMode;
		((int*)uA3Pars)[19] = m_FrameLength;
		((int*)uA3Pars)[28] = m_BsId;
		((int*)uA3Pars)[20] = m_NumRf;
		int num = 0;
		Dtapi.DtAtsc3Pars* ptr = (Dtapi.DtAtsc3Pars*)((byte*)uA3Pars + 84);
		do
		{
			*(int*)ptr = m_BondedBsId[num];
			num++;
			ptr = (Dtapi.DtAtsc3Pars*)((byte*)ptr + 4);
		}
		while (num < 7);
		Dtapi.DtAtsc3Pars* ptr2 = (Dtapi.DtAtsc3Pars*)((byte*)uA3Pars + 116);
		vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)ptr2;
		Dtapi.DtAtsc3SubframePars* last = (Dtapi.DtAtsc3SubframePars*)(int)((uint*)ptr3)[1];
		global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cclass_0020Dtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E((Dtapi.DtAtsc3SubframePars*)(int)(*(uint*)ptr3), last, (allocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E*)ptr3);
		((int*)ptr3)[1] = *(int*)ptr3;
		int num2 = 0;
		if (0 < m_Subframes.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3SubframePars dtAtsc3SubframePars);
			do
			{
				global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bctor_007D(&dtAtsc3SubframePars);
				try
				{
					m_Subframes[num2].ConvertToUnmgd(&dtAtsc3SubframePars);
					global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002Eemplace_back_003Cclass_0020Dtapi_003A_003ADtAtsc3SubframePars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)ptr2, &dtAtsc3SubframePars);
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3SubframePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3SubframePars_002E_007Bdtor_007D), &dtAtsc3SubframePars);
					throw;
				}
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3SubframePars, 44)));
				num2++;
			}
			while (num2 < m_Subframes.Count);
		}
		((int*)uA3Pars)[32] = m_NumPlpInputs;
		int num3 = 0;
		Dtapi.DtAtsc3Pars* ptr4 = (Dtapi.DtAtsc3Pars*)((byte*)uA3Pars + 132);
		do
		{
			m_PlpInputs[num3].ConvertToUnmgd((Dtapi.DtPlpInpPars*)ptr4);
			num3++;
			ptr4 = (Dtapi.DtAtsc3Pars*)((byte*)ptr4 + 216);
		}
		while (num3 < 64);
		m_VirtOutput.ConvertToUnmgd((Dtapi.DtVirtualOutPars*)((byte*)uA3Pars + 13960));
		m_TpOutput.ConvertToUnmgd((Dtapi.DtTestPointOutPars*)((byte*)uA3Pars + 13976));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3Pars* uA3Pars)
	{
		m_Bandwidth = *(int*)uA3Pars;
		m_MinorVersion = ((int*)uA3Pars)[1];
		m_EasWakeup = ((int*)uA3Pars)[2];
		m_PreambleFftSize = ((int*)uA3Pars)[3];
		m_PreambleGuardInterval = ((int*)uA3Pars)[4];
		m_PreamblePilotDx = ((int*)uA3Pars)[5];
		m_PreambleReducedCarriers = ((int*)uA3Pars)[6];
		m_L1BasicFecMode = ((int*)uA3Pars)[7];
		m_L1DetailFecMode = ((int*)uA3Pars)[8];
		m_L1DetailAddParity = ((int*)uA3Pars)[9];
		m_L1DetailVersion = ((int*)uA3Pars)[10];
		m_TxIdInjectLevelCode = ((int*)uA3Pars)[11];
		m_TxIdAddress = ((int*)uA3Pars)[12];
		m_TimeInfoFlag = ((int*)uA3Pars)[13];
		m_TimeSeconds = ((int*)uA3Pars)[14];
		m_TimeNanoseconds = ((int*)uA3Pars)[15];
		m_LlsFlag = ((bool*)uA3Pars)[64];
		m_Papr = ((int*)uA3Pars)[17];
		m_FrameLengthMode = ((int*)uA3Pars)[18];
		m_FrameLength = ((int*)uA3Pars)[19];
		m_BsId = ((int*)uA3Pars)[28];
		m_NumRf = ((int*)uA3Pars)[20];
		int num = 0;
		Dtapi.DtAtsc3Pars* ptr = (Dtapi.DtAtsc3Pars*)((byte*)uA3Pars + 84);
		do
		{
			ref int reference = ref m_BondedBsId[num];
			reference = *(int*)ptr;
			num++;
			ptr = (Dtapi.DtAtsc3Pars*)((byte*)ptr + 4);
		}
		while (num < 7);
		m_Subframes.Clear();
		uint num2 = 0u;
		vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)((byte*)uA3Pars + 116);
		if (0u < (uint)((((int*)ptr2)[1] - *(int*)ptr2) / 56))
		{
			ptr2 = (vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)((byte*)uA3Pars + 116);
			vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)((byte*)ptr2 + 4);
			int num3 = 0;
			do
			{
				DtAtsc3SubframePars dtAtsc3SubframePars = new DtAtsc3SubframePars();
				dtAtsc3SubframePars.ConvertFromUnmgd((Dtapi.DtAtsc3SubframePars*)(num3 + ((int*)uA3Pars)[29]));
				m_Subframes.Add(dtAtsc3SubframePars);
				num2++;
				num3 += 56;
			}
			while (num2 < (uint)((*(int*)ptr3 - *(int*)ptr2) / 56));
		}
		m_NumPlpInputs = ((int*)uA3Pars)[32];
		int num4 = 0;
		Dtapi.DtAtsc3Pars* ptr4 = (Dtapi.DtAtsc3Pars*)((byte*)uA3Pars + 132);
		do
		{
			m_PlpInputs[num4].ConvertFromUnmgd((Dtapi.DtPlpInpPars*)ptr4);
			num4++;
			ptr4 = (Dtapi.DtAtsc3Pars*)((byte*)ptr4 + 216);
		}
		while (num4 < 64);
		m_VirtOutput.ConvertFromUnmgd((Dtapi.DtVirtualOutPars*)((byte*)uA3Pars + 13960));
		m_TpOutput.m_Enabled = ((bool*)uA3Pars)[13976];
	}

	public unsafe DtAtsc3Pars()
	{
		m_BondedBsId = new int[7];
		m_Subframes = new List<DtAtsc3SubframePars>();
		m_PlpInputs = new DtPlpInpPars[64];
		int num = 0;
		do
		{
			m_PlpInputs[num] = new DtPlpInpPars();
			num++;
		}
		while (num < 64);
		m_VirtOutput = new DtVirtualOutPars();
		m_TpOutput = new DtTestPointOutPars();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		try
		{
			ConvertFromUnmgd(&dtAtsc3Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
	}
}
