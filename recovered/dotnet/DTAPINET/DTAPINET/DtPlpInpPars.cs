using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtPlpInpPars
{
	public enum InDataType
	{
		ALP = 3,
		TS188 = 0,
		TS204 = 1,
		GSE = 2,
		TLVIS3 = 4
	}

	public int m_FifoIdx;

	public InDataType m_DataType;

	public DtBigTsSplitPars m_BigTsSplit;

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtPlpInpPars dtPlpInpPars);
		Dtapi.DtBigTsSplitPars* ptr = (Dtapi.DtBigTsSplitPars*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 8));
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 12)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 16)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 20)) = 0;
		try
		{
			ConvertToUnmgd(&dtPlpInpPars);
			global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002EInit(&dtPlpInpPars, 0, (Dtapi.DtPlpInpPars.InDataType)0);
			ConvertFromUnmgd(&dtPlpInpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D), &dtPlpInpPars);
			throw;
		}
		Dtapi.DtBigTsSplitPars* ptr2 = (Dtapi.DtBigTsSplitPars*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 8));
		global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 12)));
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtPlpInpPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtPlpInpPars dtPlpInpPars);
		Dtapi.DtBigTsSplitPars* ptr = (Dtapi.DtBigTsSplitPars*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 8));
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 12)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 16)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 20)) = 0;
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtPlpInpPars dtPlpInpPars2);
			Dtapi.DtBigTsSplitPars* ptr2 = (Dtapi.DtBigTsSplitPars*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars2, 8));
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars2, 12)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars2, 16)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtPlpInpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars2, 20)) = 0;
			try
			{
				ConvertToUnmgd(&dtPlpInpPars);
				Rhs.ConvertToUnmgd(&dtPlpInpPars2);
				result = global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_003D_003D(&dtPlpInpPars, &dtPlpInpPars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D), &dtPlpInpPars2);
				throw;
			}
			Dtapi.DtBigTsSplitPars* ptr3 = (Dtapi.DtBigTsSplitPars*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars2, 8));
			global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars2, 12)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D), &dtPlpInpPars);
			throw;
		}
		Dtapi.DtBigTsSplitPars* ptr4 = (Dtapi.DtBigTsSplitPars*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 8));
		global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtPlpInpPars, 12)));
		return result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtPlpInpPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal DtPlpInpPars()
	{
		m_BigTsSplit = new DtBigTsSplitPars();
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtPlpInpPars* uPlpInpPars)
	{
		*(int*)uPlpInpPars = m_FifoIdx;
		((int*)uPlpInpPars)[1] = (int)m_DataType;
		m_BigTsSplit.ConvertToUnmgd((Dtapi.DtBigTsSplitPars*)((byte*)uPlpInpPars + 8));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtPlpInpPars* rPars)
	{
		m_FifoIdx = *(int*)rPars;
		m_DataType = ((InDataType*)rPars)[1];
		m_BigTsSplit.ConvertFromUnmgd((Dtapi.DtBigTsSplitPars*)((byte*)rPars + 8));
	}
}
