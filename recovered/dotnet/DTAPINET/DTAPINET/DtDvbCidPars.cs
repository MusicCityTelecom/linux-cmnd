using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbCidPars
{
	public bool m_Enable;

	public uint m_GuidHigh;

	public uint m_GuidLow;

	public Dictionary<int, int> m_Content;

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbCidPars dtDvbCidPars);
		global::_003CModule_003E.Dtapi_002EDtDvbCidPars_002E_007Bctor_007D(&dtDvbCidPars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtDvbCidPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDvbCidPars_002ECheckValidity(&dtDvbCidPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbCidPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbCidPars_002E_007Bdtor_007D), &dtDvbCidPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbCidPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)));
		return result;
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbCidPars* uCidPars)
	{
		*(bool*)uCidPars = m_Enable;
		((int*)uCidPars)[1] = (int)m_GuidHigh;
		((int*)uCidPars)[2] = (int)m_GuidLow;
		Dtapi.DtDvbCidPars* ptr = (Dtapi.DtDvbCidPars*)((byte*)uCidPars + 12);
		global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002Eclear((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)ptr);
		Dictionary<int, int>.Enumerator enumerator = m_Content.GetEnumerator();
		if (enumerator.MoveNext())
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out pair_003Cstd_003A_003A_Tree_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_0020_003E_002Cbool_003E obj);
			do
			{
				KeyValuePair<int, int> current = enumerator.Current;
				int key = current.Key;
				global::_003CModule_003E.std_002Emap_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_002E_Try_emplace_003Cint_003E((map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)ptr, &obj, &key);
				*(int*)(*(int*)(&obj) + 16 + 4) = current.Value;
			}
			while (enumerator.MoveNext());
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbCidPars* uCidPars)
	{
		m_Enable = *(bool*)uCidPars;
		m_GuidHigh = ((uint*)uCidPars)[1];
		m_GuidLow = ((uint*)uCidPars)[2];
		m_Content.Clear();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out _Tree_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_0020_003E obj);
		*(int*)(&obj) = *(int*)(int)((uint*)uCidPars)[3];
		while (*(int*)(&obj) != ((int*)uCidPars)[3])
		{
			m_Content[*(int*)(*(int*)(&obj) + 16)] = *(int*)(*(int*)(&obj) + 20);
			global::_003CModule_003E.std_002E_Tree_unchecked_const_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_002Cstd_003A_003A_Iterator_base0_003E_002E_002B_002B((_Tree_unchecked_const_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E_002Cstd_003A_003A_Iterator_base0_003E*)(&obj));
		}
	}

	public unsafe DtDvbCidPars()
	{
		m_Content = new Dictionary<int, int>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbCidPars dtDvbCidPars);
		global::_003CModule_003E.Dtapi_002EDtDvbCidPars_002E_007Bctor_007D(&dtDvbCidPars);
		try
		{
			ConvertFromUnmgd(&dtDvbCidPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbCidPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbCidPars_002E_007Bdtor_007D), &dtDvbCidPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbCidPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)));
	}
}
