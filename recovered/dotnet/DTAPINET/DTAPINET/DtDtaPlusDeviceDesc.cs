using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDtaPlusDeviceDesc
{
	public long m_Serial;

	public string m_DevicePath;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDtaPlusDeviceDesc* uDvcDesc)
	{
		m_Serial = *(long*)uDvcDesc;
		basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E* ptr = (basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E*)((byte*)uDvcDesc + 8);
		sbyte* value = (sbyte*)ptr;
		if ((byte)((16u <= (uint)((int*)ptr)[5]) ? 1u : 0u) != 0)
		{
			value = (sbyte*)(int)(*(uint*)ptr);
		}
		m_DevicePath = new string(value);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDtaPlusDeviceDesc* uDvcDesc)
	{
		*(long*)uDvcDesc = m_Serial;
		ref byte reference = ref *(byte*)m_DevicePath;
		if (System.Runtime.CompilerServices.Unsafe.AsPointer(ref reference) != null)
		{
			reference = ref *(byte*)((ref *(_003F*)RuntimeHelpers.OffsetToStringData) + (ref System.Runtime.CompilerServices.Unsafe.As<byte, _003F>(ref reference)));
		}
		fixed (char* ptr = &System.Runtime.CompilerServices.Unsafe.As<byte, char>(ref reference))
		{
			int num = m_DevicePath.Length * 2 + 2;
			sbyte* ptr2 = (sbyte*)global::_003CModule_003E.new_005B_005D((uint)num);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out uint num2);
			global::_003CModule_003E.wcstombs_s(&num2, ptr2, (uint)num, ptr, (uint)num);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E obj);
			basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E* right = global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002E_007Bctor_007D(&obj, ptr2);
			try
			{
				global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002E_003D((basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E*)((byte*)uDvcDesc + 8), right);
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
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cchar_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cchar_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_0020_003E_002E_007Bdtor_007D), &obj);
				throw;
			}
			global::_003CModule_003E.delete(ptr2, 1u);
		}
	}

	public DtDtaPlusDeviceDesc()
	{
		m_Serial = -1L;
		m_DevicePath = "";
	}
}
