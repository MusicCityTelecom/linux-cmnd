using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtBigTsSplitPars
{
	public bool m_Enabled;

	public bool m_IsCommonPlp;

	public bool m_SplitSdtIn;

	public List<int> m_Pids;

	public int m_OnwId;

	public int m_TsId;

	public int m_ServiceId;

	public int m_PmtPid;

	public int m_NewTsId;

	public int m_SdtLoopDataLength;

	public byte[] m_SdtLoopData;

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtBigTsSplitPars dtBigTsSplitPars);
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 4)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 8)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 12)) = 0;
		try
		{
			ConvertToUnmgd(&dtBigTsSplitPars);
			global::_003CModule_003E.Dtapi_002EDtBigTsSplitPars_002EInit(&dtBigTsSplitPars);
			ConvertFromUnmgd(&dtBigTsSplitPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtBigTsSplitPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtBigTsSplitPars_002E_007Bdtor_007D), &dtBigTsSplitPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 4)));
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtBigTsSplitPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtBigTsSplitPars dtBigTsSplitPars);
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 4)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 8)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 12)) = 0;
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtBigTsSplitPars dtBigTsSplitPars2);
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars2, 4)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars2, 8)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtBigTsSplitPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars2, 12)) = 0;
			try
			{
				ConvertToUnmgd(&dtBigTsSplitPars);
				Rhs.ConvertToUnmgd(&dtBigTsSplitPars2);
				result = global::_003CModule_003E.Dtapi_002EDtBigTsSplitPars_002E_003D_003D(&dtBigTsSplitPars, &dtBigTsSplitPars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtBigTsSplitPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtBigTsSplitPars_002E_007Bdtor_007D), &dtBigTsSplitPars2);
				throw;
			}
			global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars2, 4)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtBigTsSplitPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtBigTsSplitPars_002E_007Bdtor_007D), &dtBigTsSplitPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtBigTsSplitPars, 4)));
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtBigTsSplitPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal DtBigTsSplitPars()
	{
		m_Enabled = false;
		m_Pids = new List<int>();
		m_SdtLoopData = new byte[168];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtBigTsSplitPars* uSplitPars)
	{
		*(bool*)uSplitPars = m_Enabled;
		((sbyte*)uSplitPars)[1] = (m_IsCommonPlp ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uSplitPars)[2] = (m_SplitSdtIn ? ((sbyte)1) : ((sbyte)0));
		Dtapi.DtBigTsSplitPars* ptr = (Dtapi.DtBigTsSplitPars*)((byte*)uSplitPars + 4);
		vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E* ptr2 = (vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Pids.Count)
		{
			do
			{
				int num2 = m_Pids[num];
				global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002Eemplace_back_003Cint_003E((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)ptr, &num2);
				num++;
			}
			while (num < m_Pids.Count);
		}
		((int*)uSplitPars)[4] = m_OnwId;
		((int*)uSplitPars)[5] = m_TsId;
		((int*)uSplitPars)[6] = m_ServiceId;
		((int*)uSplitPars)[7] = m_PmtPid;
		((int*)uSplitPars)[8] = m_NewTsId;
		((int*)uSplitPars)[9] = m_SdtLoopDataLength;
		IntPtr destination = new IntPtr((byte*)uSplitPars + 40);
		byte[] sdtLoopData = m_SdtLoopData;
		Marshal.Copy(sdtLoopData, 0, destination, sdtLoopData.Length);
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtBigTsSplitPars* uSplitPars)
	{
		m_Enabled = *(bool*)uSplitPars;
		m_IsCommonPlp = ((bool*)uSplitPars)[1];
		m_SplitSdtIn = ((bool*)uSplitPars)[2];
		m_Pids.Clear();
		uint num = 0u;
		Dtapi.DtBigTsSplitPars* ptr = (Dtapi.DtBigTsSplitPars*)((byte*)uSplitPars + 4);
		vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E* ptr2 = (vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)ptr;
		if (0u < (uint)(((int*)ptr2)[1] - *(int*)ptr2 >> 2))
		{
			ptr2 = (vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)ptr;
			vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E* ptr3 = (vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)((byte*)ptr2 + 4);
			do
			{
				m_Pids.Add(*(int*)(int)(num * 4 + (uint)(*(int*)ptr)));
				num++;
			}
			while (num < (uint)(*(int*)ptr3 - *(int*)ptr2 >> 2));
		}
		m_OnwId = ((int*)uSplitPars)[4];
		m_TsId = ((int*)uSplitPars)[5];
		m_ServiceId = ((int*)uSplitPars)[6];
		m_PmtPid = ((int*)uSplitPars)[7];
		m_NewTsId = ((int*)uSplitPars)[8];
		m_SdtLoopDataLength = ((int*)uSplitPars)[9];
		IntPtr source = new IntPtr((byte*)uSplitPars + 40);
		byte[] sdtLoopData = m_SdtLoopData;
		Marshal.Copy(source, sdtLoopData, 0, sdtLoopData.Length);
	}
}
