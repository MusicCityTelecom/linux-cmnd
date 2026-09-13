using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDriverVersionInfo
{
	public int m_Category;

	public DtDriverId m_Id;

	public string m_Name;

	public int m_Major;

	public int m_Minor;

	public int m_BugFix;

	public int m_Build;

	public DtDriverVersionInfo()
	{
		m_Name = "";
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDriverVersionInfo* uDrvInfo)
	{
		m_Category = *(int*)uDrvInfo;
		m_Id = ((DtDriverId*)uDrvInfo)[1];
		basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E* ptr = (basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDrvInfo + 8);
		char* value = (char*)ptr;
		if ((byte)((8u <= (uint)((int*)ptr)[5]) ? 1u : 0u) != 0)
		{
			value = (char*)(int)(*(uint*)ptr);
		}
		m_Name = new string(value);
		m_Major = ((int*)uDrvInfo)[8];
		m_Minor = ((int*)uDrvInfo)[9];
		m_BugFix = ((int*)uDrvInfo)[10];
		m_Build = ((int*)uDrvInfo)[11];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDriverVersionInfo* uDrvInfo)
	{
		*(int*)uDrvInfo = m_Category;
		((int*)uDrvInfo)[1] = (int)m_Id;
		fixed (char* ptr = &m_Name.ToCharArray()[0])
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E obj);
			basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E* right = global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_007Bctor_007D(&obj, ptr);
			try
			{
				global::_003CModule_003E.std_002Ebasic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E_002E_003D((basic_string_003Cwchar_t_002Cstd_003A_003Achar_traits_003Cwchar_t_003E_002Cstd_003A_003Aallocator_003Cwchar_t_003E_0020_003E*)((byte*)uDrvInfo + 8), right);
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
			((int*)uDrvInfo)[8] = m_Major;
			((int*)uDrvInfo)[9] = m_Minor;
			((int*)uDrvInfo)[10] = m_BugFix;
			((int*)uDrvInfo)[11] = m_Build;
		}
	}
}
