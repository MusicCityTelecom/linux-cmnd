using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDtaPlusDevice : IDisposable
{
	private unsafe Dtapi.DtDtaPlusDevice* m_pDtaPlusDvc;

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool IsAttached()
	{
		return global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EIsAttached(m_pDtaPlusDvc);
	}

	public unsafe DTAPI_RESULT AttachToDevice(DtDtaPlusDeviceDesc rDvcDescr)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDtaPlusDeviceDesc dtDtaPlusDeviceDesc);
		global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002E_007Bctor_007D((basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDtaPlusDeviceDesc, 8)));
		DTAPI_RESULT result;
		try
		{
			rDvcDescr.ConvertToUnmgd(&dtDtaPlusDeviceDesc);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EAttachToDevice(m_pDtaPlusDvc, &dtDtaPlusDeviceDesc);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDtaPlusDeviceDesc*, void>)(&global::_003CModule_003E.Dtapi_002EDtDtaPlusDeviceDesc_002E_007Bdtor_007D), &dtDtaPlusDeviceDesc);
			throw;
		}
		basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E* pThis = (basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDtaPlusDeviceDesc, 8));
		try
		{
			global::_003CModule_003E.std_002Ebasic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_002E_Tidy_deallocate((basic_string_003Cchar_002Cstd_003A_003Achar_traits_003Cchar_003E_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDtaPlusDeviceDesc, 8)));
			return result;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_String_alloc_003Cstd_003A_003A_String_base_types_003Cchar_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_String_alloc_003Cstd_003A_003A_String_base_types_003Cchar_002Cstd_003A_003Aallocator_003Cchar_003E_0020_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
	}

	public unsafe DTAPI_RESULT AttachToSerial(long SerialNumber)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EAttachToSerial(m_pDtaPlusDvc, SerialNumber);
	}

	public unsafe DTAPI_RESULT Detach()
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EDetach(m_pDtaPlusDvc);
	}

	public unsafe DTAPI_RESULT GetDeviceStatus(ref int rStatus)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EGetDeviceStatus(m_pDtaPlusDvc, &num);
		rStatus = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTempControlStatus(ref int rControlStatus)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EGetTempControlStatus(m_pDtaPlusDvc, &num);
		rControlStatus = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTempTemperature(ref int rTemperature)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EGetTemperature(m_pDtaPlusDvc, &num);
		rTemperature = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTempSerialNumber(ref long rSerialNumber)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num);
		uint result = global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002EGetSerialNumber(m_pDtaPlusDvc, &num);
		rSerialNumber = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT SetRfOutLevel(int Level)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002ESetRfOutLevel(m_pDtaPlusDvc, Level);
	}

	public unsafe DTAPI_RESULT SetFreq(int Freq)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002ESetFreq(m_pDtaPlusDvc, Freq);
	}

	public unsafe DtDtaPlusDevice()
	{
		Dtapi.DtDtaPlusDevice* ptr = (Dtapi.DtDtaPlusDevice*)global::_003CModule_003E.@new(8u);
		Dtapi.DtDtaPlusDevice* pDtaPlusDvc;
		try
		{
			pDtaPlusDvc = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtDtaPlusDevice_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 8u);
			throw;
		}
		m_pDtaPlusDvc = pDtaPlusDvc;
	}

	private void _007EDtDtaPlusDevice()
	{
		_0021DtDtaPlusDevice();
	}

	private unsafe void _0021DtDtaPlusDevice()
	{
		Dtapi.DtDtaPlusDevice* pDtaPlusDvc = m_pDtaPlusDvc;
		if (pDtaPlusDvc != null)
		{
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)pDtaPlusDvc)))((nint)pDtaPlusDvc, 1u);
		}
		m_pDtaPlusDvc = null;
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtDtaPlusDevice();
			return;
		}
		try
		{
			_0021DtDtaPlusDevice();
		}
		finally
		{
			base.Finalize();
		}
	}

	public virtual sealed void Dispose()
	{
		Dispose(A_0: true);
		GC.SuppressFinalize(this);
	}

	~DtDtaPlusDevice()
	{
		Dispose(A_0: false);
	}
}
