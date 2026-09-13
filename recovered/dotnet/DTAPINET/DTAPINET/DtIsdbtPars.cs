using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtIsdbtPars
{
	public bool m_DoMux;

	public bool m_FilledOut;

	public int m_ParXtra0;

	public int m_BType;

	public int m_Mode;

	public int m_Guard;

	public int m_PartialRx;

	public int m_Emergency;

	public int m_IipPid;

	public DtIsdbtLayerPars[] m_LayerPars;

	public Dictionary<int, int> m_Pid2Layer;

	public int m_LayerOther;

	public int m_Virtual13Segm;

	public bool m_Valid;

	public int m_TotalBitrate;

	public unsafe DTAPI_RESULT CheckValidity(ref int ResultCode)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtIsdbtPars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002ECheckValidity(&dtIsdbtPars, &num);
			ConvertFromUnmgd(&dtIsdbtPars);
			ResultCode = num;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		return result;
	}

	public unsafe DTAPI_RESULT ComputeRates()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtIsdbtPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002EComputeRates(&dtIsdbtPars);
			ConvertFromUnmgd(&dtIsdbtPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		return result;
	}

	public unsafe DTAPI_RESULT RetrieveParsFromTs(byte[] gBuffer, int NumBytes)
	{
		fixed (byte* ptr = &gBuffer[0])
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
			global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
			DTAPI_RESULT result;
			try
			{
				ConvertToUnmgd(&dtIsdbtPars);
				result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002ERetrieveParsFromTs(&dtIsdbtPars, (sbyte*)ptr, NumBytes);
				ConvertFromUnmgd(&dtIsdbtPars);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
				throw;
			}
			map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
			try
			{
				global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
				throw;
			}
			global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
			return result;
		}
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public static bool BTypeCompat(int BType, int NumSegm)
	{
		return global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002EBTypeCompat(BType, NumSegm);
	}

	public unsafe void MakeConsistent()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
		try
		{
			ConvertToUnmgd(&dtIsdbtPars);
			global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002EMakeConsistent(&dtIsdbtPars);
			ConvertFromUnmgd(&dtIsdbtPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
	}

	public unsafe void MakeNumSegmConsistent()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
		try
		{
			ConvertToUnmgd(&dtIsdbtPars);
			global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002EMakeNumSegmConsistent(&dtIsdbtPars);
			ConvertFromUnmgd(&dtIsdbtPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
	}

	public int NumSegm()
	{
		DtIsdbtLayerPars[] layerPars = m_LayerPars;
		return layerPars[2].m_NumSegments + layerPars[1].m_NumSegments + layerPars[0].m_NumSegments;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtIsdbtPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
		bool result;
		try
		{
			ConvertToUnmgd(&dtIsdbtPars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars2);
			global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars2);
			try
			{
				Rhs.ConvertToUnmgd(&dtIsdbtPars2);
				result = global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_003D_003D(&dtIsdbtPars, &dtIsdbtPars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars2);
				throw;
			}
			map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars2, 100));
			try
			{
				global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars2, 100)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
				throw;
			}
			global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars2, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars2, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis2 = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis2);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtIsdbtPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIsdbtPars* uIsdbtPars)
	{
		((int*)uIsdbtPars)[2] = m_BType;
		*(bool*)uIsdbtPars = m_DoMux;
		((sbyte*)uIsdbtPars)[1] = (m_FilledOut ? ((sbyte)1) : ((sbyte)0));
		((int*)uIsdbtPars)[6] = m_Emergency;
		((int*)uIsdbtPars)[4] = m_Guard;
		((int*)uIsdbtPars)[7] = m_IipPid;
		int num = 0;
		Dtapi.DtIsdbtPars* ptr = (Dtapi.DtIsdbtPars*)((byte*)uIsdbtPars + 48);
		do
		{
			((int*)ptr)[2] = m_LayerPars[num].m_BitRate;
			*(int*)ptr = m_LayerPars[num].m_CodeRate;
			*((int*)ptr - 1) = m_LayerPars[num].m_Modulation;
			*((int*)ptr - 2) = m_LayerPars[num].m_NumSegments;
			((int*)ptr)[1] = m_LayerPars[num].m_TimeInterleave;
			num++;
			ptr = (Dtapi.DtIsdbtPars*)((byte*)ptr + 20);
		}
		while (num < 3);
		((int*)uIsdbtPars)[3] = m_Mode;
		((int*)uIsdbtPars)[5] = m_PartialRx;
		((int*)uIsdbtPars)[8] = m_LayerOther;
		((int*)uIsdbtPars)[1] = m_ParXtra0;
		((int*)uIsdbtPars)[9] = m_Virtual13Segm;
		((int*)uIsdbtPars)[28] = m_TotalBitrate;
		((sbyte*)uIsdbtPars)[108] = (m_Valid ? ((sbyte)1) : ((sbyte)0));
		Dtapi.DtIsdbtPars* ptr2 = (Dtapi.DtIsdbtPars*)((byte*)uIsdbtPars + 100);
		global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002Eclear((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)ptr2);
		Dictionary<int, int> pid2Layer = m_Pid2Layer;
		if (pid2Layer == null)
		{
			return;
		}
		Dictionary<int, int>.Enumerator enumerator = pid2Layer.GetEnumerator();
		if (enumerator.MoveNext())
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out pair_003Cstd_003A_003A_Tree_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_0020_003E_002Cbool_003E obj);
			do
			{
				KeyValuePair<int, int> current = enumerator.Current;
				int key = current.Key;
				global::_003CModule_003E.std_002Emap_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_002E_Try_emplace_003Cint_003E((map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)ptr2, &obj, &key);
				*(int*)(*(int*)(&obj) + 16 + 4) = current.Value;
			}
			while (enumerator.MoveNext());
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIsdbtPars* uIsdbtPars)
	{
		m_BType = ((int*)uIsdbtPars)[2];
		m_DoMux = *(bool*)uIsdbtPars;
		m_Emergency = ((int*)uIsdbtPars)[6];
		m_Guard = ((int*)uIsdbtPars)[4];
		m_IipPid = ((int*)uIsdbtPars)[7];
		int num = 0;
		Dtapi.DtIsdbtPars* ptr = (Dtapi.DtIsdbtPars*)((byte*)uIsdbtPars + 48);
		do
		{
			m_LayerPars[num].m_BitRate = ((int*)ptr)[2];
			m_LayerPars[num].m_CodeRate = *(int*)ptr;
			m_LayerPars[num].m_Modulation = *((int*)ptr - 1);
			m_LayerPars[num].m_NumSegments = *((int*)ptr - 2);
			m_LayerPars[num].m_TimeInterleave = ((int*)ptr)[1];
			num++;
			ptr = (Dtapi.DtIsdbtPars*)((byte*)ptr + 20);
		}
		while (num < 3);
		m_Mode = ((int*)uIsdbtPars)[3];
		m_PartialRx = ((int*)uIsdbtPars)[5];
		m_LayerOther = ((int*)uIsdbtPars)[8];
		m_ParXtra0 = ((int*)uIsdbtPars)[1];
		m_Virtual13Segm = ((int*)uIsdbtPars)[9];
		m_TotalBitrate = ((int*)uIsdbtPars)[28];
		m_Valid = ((bool*)uIsdbtPars)[108];
		m_Pid2Layer.Clear();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out _Tree_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_0020_003E obj);
		*(int*)(&obj) = 0;
		if (((int*)((byte*)uIsdbtPars + 100))[1] != 0)
		{
			*(int*)(&obj) = *(int*)(int)((uint*)uIsdbtPars)[25];
			while (*(int*)(&obj) != ((int*)uIsdbtPars)[25])
			{
				m_Pid2Layer[*(int*)(*(int*)(&obj) + 16)] = *(int*)(*(int*)(&obj) + 20);
				global::_003CModule_003E.std_002E_Tree_unchecked_const_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_002Cstd_003A_003A_Iterator_base0_003E_002E_002B_002B((_Tree_unchecked_const_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_002Cstd_003A_003A_Iterator_base0_003E*)(&obj));
			}
		}
	}

	public unsafe DtIsdbtPars()
	{
		m_Pid2Layer = new Dictionary<int, int>();
		m_LayerPars = new DtIsdbtLayerPars[3];
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
		try
		{
			ConvertFromUnmgd(&dtIsdbtPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
	}
}
