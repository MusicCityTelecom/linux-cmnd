using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDabService
{
	public int m_CondAccessId;

	public int m_CountryId;

	public int m_ExtCountryCode;

	public bool m_IsLocal;

	public string m_Label;

	public int m_ServiceReference;

	public List<DtDabServiceComp> m_Components;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabService* uDabSvc)
	{
		*(int*)uDabSvc = m_CondAccessId;
		((int*)uDabSvc)[1] = m_CountryId;
		((int*)uDabSvc)[2] = m_ExtCountryCode;
		((sbyte*)uDabSvc)[12] = (m_IsLocal ? ((sbyte)1) : ((sbyte)0));
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
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_003D((basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDabSvc + 16), &obj);
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
			((int*)uDabSvc)[10] = m_ServiceReference;
			Dtapi.DtDabService* ptr3 = (Dtapi.DtDabService*)((byte*)uDabSvc + 44);
			vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E* ptr4 = (vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E*)ptr3;
			Dtapi.DtDabServiceComp* last = (Dtapi.DtDabServiceComp*)(int)((uint*)ptr4)[1];
			global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020Dtapi_003A_003ADtDabServiceComp_003E_0020_003E((Dtapi.DtDabServiceComp*)(int)(*(uint*)ptr4), last, (allocator_003CDtapi_003A_003ADtDabServiceComp_003E*)ptr4);
			((int*)ptr4)[1] = *(int*)ptr4;
			int num = 0;
			if (0 >= m_Components.Count)
			{
				return;
			}
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabServiceComp dtDabServiceComp);
			do
			{
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_007Bctor_007D((basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabServiceComp, 16)));
				try
				{
					m_Components[num].ConvertToUnmgd(&dtDabServiceComp);
					global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDabServiceComp_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E*)ptr3, &dtDabServiceComp);
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDabServiceComp*, void>)(&global::_003CModule_003E.Dtapi_002EDtDabServiceComp_002E_007Bdtor_007D), &dtDabServiceComp);
					throw;
				}
				basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E* pThis = (basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabServiceComp, 16));
				try
				{
					global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_Tidy_deallocate((basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabServiceComp, 16)));
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cwchar_t_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_0020_003E_002E_007Bdtor_007D), pThis);
					throw;
				}
				num++;
			}
			while (num < m_Components.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabService* uDabSvc)
	{
		m_CondAccessId = *(int*)uDabSvc;
		m_CountryId = ((int*)uDabSvc)[1];
		m_ExtCountryCode = ((int*)uDabSvc)[2];
		m_IsLocal = ((bool*)uDabSvc)[12];
		basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E* ptr = (basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDabSvc + 16);
		char* value = (char*)ptr;
		if ((byte)((8u <= (uint)((int*)ptr)[5]) ? 1u : 0u) != 0)
		{
			value = (char*)(int)(*(uint*)ptr);
		}
		m_Label = new string(value);
		m_ServiceReference = ((int*)uDabSvc)[10];
		m_Components.Clear();
		int num = 0;
		vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E*)((byte*)uDabSvc + 44);
		if (0 < (((int*)ptr2)[1] - *(int*)ptr2) / 56)
		{
			ptr2 = (vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E*)((byte*)uDabSvc + 44);
			vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtDabServiceComp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabServiceComp_003E_0020_003E*)((byte*)ptr2 + 4);
			int num2 = 0;
			do
			{
				DtDabServiceComp dtDabServiceComp = new DtDabServiceComp();
				dtDabServiceComp.ConvertFromUnmgd((Dtapi.DtDabServiceComp*)(((int*)uDabSvc)[11] + num2));
				m_Components.Add(dtDabServiceComp);
				num++;
				num2 += 56;
			}
			while (num < (*(int*)ptr3 - *(int*)ptr2) / 56);
		}
	}

	public DtDabService()
	{
		m_Label = "";
		m_Components = new List<DtDabServiceComp>();
	}
}
