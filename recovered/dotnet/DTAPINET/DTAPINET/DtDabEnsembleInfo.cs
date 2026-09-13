using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDabEnsembleInfo
{
	public int m_CountryId;

	public int m_EnsembleReference;

	public int m_ExtCountryCode;

	public int m_InterTableId;

	public string m_Label;

	public int m_LocalTimeOffset;

	public int m_LtoUnique;

	public int m_TransmissionMode;

	public List<DtDabService> m_Services;

	public Dictionary<int, DtDabSubChannel> m_SubChannels;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabEnsembleInfo* uDabEns)
	{
		*(int*)uDabEns = m_CountryId;
		((int*)uDabEns)[1] = m_EnsembleReference;
		((int*)uDabEns)[2] = m_ExtCountryCode;
		((int*)uDabEns)[3] = m_InterTableId;
		char[] array = m_Label.ToCharArray();
		fixed (char* ptr = &array[0])
		{
			uint count = (uint)array.Length;
			char* ptr2 = ptr;
			System.Runtime.CompilerServices.Unsafe.SkipInit(out basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E obj);
			global::_003CModule_003E.std_002E_String_val_003Cstd_003A_003A_Simple_types_003Cwchar_t_003E_0020_003E_002E_007Bctor_007D((_String_val_003Cstd_003A_003A_Simple_types_003Cwchar_t_003E_0020_003E*)(&obj));
			try
			{
				System.Runtime.CompilerServices.Unsafe.As<basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 16)) = 0;
				System.Runtime.CompilerServices.Unsafe.As<basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 20)) = 7;
				*(short*)(&obj) = 0;
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002Eassign(&obj, ptr2, count);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E_002E_007Bdtor_007D), &obj);
				throw;
			}
			try
			{
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_003D((basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDabEns + 16), &obj);
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
			((int*)uDabEns)[10] = m_LocalTimeOffset;
			((int*)uDabEns)[11] = m_LtoUnique;
			((int*)uDabEns)[12] = m_TransmissionMode;
			Dtapi.DtDabEnsembleInfo* ptr3 = (Dtapi.DtDabEnsembleInfo*)((byte*)uDabEns + 52);
			vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E* ptr4 = (vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E*)ptr3;
			Dtapi.DtDabService* last = (Dtapi.DtDabService*)(int)((uint*)ptr4)[1];
			global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020Dtapi_003A_003ADtDabService_003E_0020_003E((Dtapi.DtDabService*)(int)(*(uint*)ptr4), last, (allocator_003CDtapi_003A_003ADtDabService_003E*)ptr4);
			((int*)ptr4)[1] = *(int*)ptr4;
			int num = 0;
			if (0 < m_Services.Count)
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabService dtDabService);
				do
				{
					global::_003CModule_003E.Dtapi_002EDtDabService_002E_007Bctor_007D(&dtDabService);
					try
					{
						m_Services[num].ConvertToUnmgd(&dtDabService);
						global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDabService_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E*)ptr3, &dtDabService);
					}
					catch
					{
						//try-fault
						global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDabService*, void>)(&global::_003CModule_003E.Dtapi_002EDtDabService_002E_007Bdtor_007D), &dtDabService);
						throw;
					}
					try
					{
						global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabService, 44)));
					}
					catch
					{
						//try-fault
						global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabService, 16)));
						throw;
					}
					basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E* pThis = (basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabService, 16));
					try
					{
						global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_Tidy_deallocate((basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabService, 16)));
					}
					catch
					{
						//try-fault
						global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E_002E_007Bdtor_007D), pThis);
						throw;
					}
					num++;
				}
				while (num < m_Services.Count);
			}
			Dtapi.DtDabEnsembleInfo* ptr5 = (Dtapi.DtDabEnsembleInfo*)((byte*)uDabEns + 64);
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002CDtapi_003A_003ADtDabSubChannel_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_002C0_003E_0020_003E_002Eclear((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002CDtapi_003A_003ADtDabSubChannel_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_002C0_003E_0020_003E*)ptr5);
			Dictionary<int, DtDabSubChannel>.Enumerator enumerator = m_SubChannels.GetEnumerator();
			if (enumerator.MoveNext())
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabSubChannel dtDabSubChannel);
				System.Runtime.CompilerServices.Unsafe.SkipInit(out pair_003Cstd_003A_003A_Tree_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_0020_003E_0020_003E_002Cbool_003E obj2);
				do
				{
					enumerator.Current.Value.ConvertToUnmgd(&dtDabSubChannel);
					global::_003CModule_003E.std_002Emap_003Cint_002CDtapi_003A_003ADtDabSubChannel_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_0020_003E_002E_Try_emplace_003Cint_0020const_0020_0026_003E((map_003Cint_002CDtapi_003A_003ADtDabSubChannel_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_0020_003E*)ptr5, &obj2, (int*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabSubChannel, 24)));
					// IL cpblk instruction
					System.Runtime.CompilerServices.Unsafe.CopyBlock(*(int*)(&obj2) + 20, ref dtDabSubChannel, 40);
				}
				while (enumerator.MoveNext());
			}
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabEnsembleInfo* uDabEns)
	{
		m_CountryId = *(int*)uDabEns;
		m_EnsembleReference = ((int*)uDabEns)[1];
		m_ExtCountryCode = ((int*)uDabEns)[2];
		m_InterTableId = ((int*)uDabEns)[3];
		basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E* ptr = (basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDabEns + 16);
		char* value = (char*)ptr;
		if ((byte)((8u <= (uint)((int*)ptr)[5]) ? 1u : 0u) != 0)
		{
			value = (char*)(int)(*(uint*)ptr);
		}
		m_Label = new string(value);
		m_LocalTimeOffset = ((int*)uDabEns)[10];
		m_LtoUnique = ((int*)uDabEns)[11];
		m_TransmissionMode = ((int*)uDabEns)[12];
		m_Services.Clear();
		int num = 0;
		vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E*)((byte*)uDabEns + 52);
		if (0 < (((int*)ptr2)[1] - *(int*)ptr2) / 56)
		{
			ptr2 = (vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E*)((byte*)uDabEns + 52);
			vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtDabService_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabService_003E_0020_003E*)((byte*)ptr2 + 4);
			int num2 = 0;
			do
			{
				DtDabService dtDabService = new DtDabService();
				dtDabService.ConvertFromUnmgd((Dtapi.DtDabService*)(num2 + ((int*)uDabEns)[13]));
				m_Services.Add(dtDabService);
				num++;
				num2 += 56;
			}
			while (num < (*(int*)ptr3 - *(int*)ptr2) / 56);
		}
		m_SubChannels.Clear();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out _Tree_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_0020_003E_0020_003E obj);
		*(int*)(&obj) = *(int*)(int)((uint*)uDabEns)[16];
		while (*(int*)(&obj) != ((int*)uDabEns)[16])
		{
			DtDabSubChannel dtDabSubChannel = new DtDabSubChannel();
			dtDabSubChannel.ConvertFromUnmgd((Dtapi.DtDabSubChannel*)(*(int*)(&obj) + 20));
			m_SubChannels[*(int*)(*(int*)(&obj) + 16)] = dtDabSubChannel;
			global::_003CModule_003E.std_002E_Tree_unchecked_const_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_0020_003E_002Cstd_003A_003A_Iterator_base0_003E_002E_002B_002B((_Tree_unchecked_const_iterator_003Cstd_003A_003A_Tree_val_003Cstd_003A_003A_Tree_simple_types_003Cstd_003A_003Apair_003Cint_0020const_0020_002CDtapi_003A_003ADtDabSubChannel_003E_0020_003E_0020_003E_002Cstd_003A_003A_Iterator_base0_003E*)(&obj));
		}
	}

	public DtDabEnsembleInfo()
	{
		m_Label = "";
		m_Services = new List<DtDabService>();
		m_SubChannels = new Dictionary<int, DtDabSubChannel>();
	}
}
