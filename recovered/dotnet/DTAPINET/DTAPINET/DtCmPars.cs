using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtCmPars
{
	public bool m_EnableAwgn;

	public double m_Snr;

	public bool m_EnablePaths;

	public List<DtCmPath> m_Paths;

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtCmPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPars dtCmPars);
		global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bctor_007D(&dtCmPars);
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPars dtCmPars2);
			global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bctor_007D(&dtCmPars2);
			try
			{
				ConvertToUnmgd(&dtCmPars);
				Rhs.ConvertToUnmgd(&dtCmPars2);
				result = global::_003CModule_003E.Dtapi_002EDtCmPars_002E_003D_003D(&dtCmPars, &dtCmPars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtCmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bdtor_007D), &dtCmPars2);
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPars2, 20)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtCmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bdtor_007D), &dtCmPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPars, 20)));
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtCmPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtCmPars* uCmPars)
	{
		*(bool*)uCmPars = m_EnableAwgn;
		((double*)uCmPars)[1] = m_Snr;
		((sbyte*)uCmPars)[16] = (m_EnablePaths ? ((sbyte)1) : ((sbyte)0));
		Dtapi.DtCmPars* ptr = (Dtapi.DtCmPars*)((byte*)uCmPars + 20);
		vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Paths.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPath dtCmPath);
			do
			{
				*(int*)(&dtCmPath) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 8)) = 0.0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 16)) = 0.0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 24)) = 0.0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 32)) = 0.0;
				m_Paths[num].ConvertToUnmgd(&dtCmPath);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtCmPath_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)ptr, &dtCmPath);
				num++;
			}
			while (num < m_Paths.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtCmPars* uCmPars)
	{
		m_EnableAwgn = *(bool*)uCmPars;
		m_Snr = ((double*)uCmPars)[1];
		m_EnablePaths = ((bool*)uCmPars)[16];
		m_Paths.Clear();
		int num = 0;
		vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)((byte*)uCmPars + 20);
		if (0 < (((int*)ptr)[1] - *(int*)ptr) / 40)
		{
			ptr = (vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)((byte*)uCmPars + 20);
			vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtCmPath dtCmPath = new DtCmPath();
				dtCmPath.ConvertFromUnmgd((Dtapi.DtCmPath*)(((int*)uCmPars)[5] + num2));
				m_Paths.Add(dtCmPath);
				num++;
				num2 += 40;
			}
			while (num < (*(int*)ptr2 - *(int*)ptr) / 40);
		}
	}

	public unsafe DtCmPars()
	{
		m_Paths = new List<DtCmPath>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPars dtCmPars);
		global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bctor_007D(&dtCmPars);
		try
		{
			ConvertFromUnmgd(&dtCmPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtCmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bdtor_007D), &dtCmPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPars, 20)));
	}
}
