using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDabServiceComp
{
	public int m_AudioServiceCompType;

	public int m_DataServiceCompType;

	public int m_FidChannelId;

	public bool m_HasCondAccess;

	public bool m_IsPrimary;

	public string m_Label;

	public int m_Language;

	public int m_SubChannelId;

	public int m_ServiceCompId;

	public int m_TransportMechanismId;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabServiceComp* uDabSvcComp)
	{
		*(int*)uDabSvcComp = m_AudioServiceCompType;
		((int*)uDabSvcComp)[1] = m_DataServiceCompType;
		((int*)uDabSvcComp)[2] = m_FidChannelId;
		((sbyte*)uDabSvcComp)[12] = (m_HasCondAccess ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uDabSvcComp)[13] = (m_IsPrimary ? ((sbyte)1) : ((sbyte)0));
		char[] array = m_Label.ToCharArray();
		fixed (char* ptr = &array[0])
		{
			uint count = (uint)array.Length;
			char* ptr2 = ptr;
			System.Runtime.CompilerServices.Unsafe.SkipInit(out basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E obj);
			try
			{
				System.Runtime.CompilerServices.Unsafe.As<basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 16)) = 0;
				System.Runtime.CompilerServices.Unsafe.As<basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 20)) = 0;
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_val_003Cstd_003A_003A_Simple_types_003Cwchar_t_003E_0020_003E._Bxty*, void>)(&global::_003CModule_003E.std_002E_String_val_003Cstd_003A_003A_Simple_types_003Cwchar_t_003E_0020_003E_002E_Bxty_002E_007Bdtor_007D), &obj);
				throw;
			}
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
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_003D((basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDabSvcComp + 16), &obj);
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
			((int*)uDabSvcComp)[10] = m_Language;
			((int*)uDabSvcComp)[11] = m_SubChannelId;
			((int*)uDabSvcComp)[12] = m_ServiceCompId;
			((int*)uDabSvcComp)[13] = m_TransportMechanismId;
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabServiceComp* uDabSvcComp)
	{
		m_AudioServiceCompType = *(int*)uDabSvcComp;
		m_DataServiceCompType = ((int*)uDabSvcComp)[1];
		m_FidChannelId = ((int*)uDabSvcComp)[2];
		m_HasCondAccess = ((bool*)uDabSvcComp)[12];
		m_IsPrimary = ((bool*)uDabSvcComp)[13];
		basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E* ptr = (basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDabSvcComp + 16);
		char* value = (char*)ptr;
		if ((byte)((8u <= (uint)((int*)ptr)[5]) ? 1u : 0u) != 0)
		{
			value = (char*)(int)(*(uint*)ptr);
		}
		m_Label = new string(value);
		m_Language = ((int*)uDabSvcComp)[10];
		m_SubChannelId = ((int*)uDabSvcComp)[11];
		m_ServiceCompId = ((int*)uDabSvcComp)[12];
		m_TransportMechanismId = ((int*)uDabSvcComp)[13];
	}

	public DtDabServiceComp()
	{
		m_Label = "";
	}
}
