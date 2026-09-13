using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtPar : IDisposable
{
	public enum StatValueType
	{
		PAR_VT_UNDEFINED = 0,
		PAR_VT_INT = 3,
		PAR_VT_DOUBLE = 2,
		PAR_VT_BOOL = 1
	}

	internal unsafe Dtapi.DtPar* m_pDtPar;

	public unsafe bool m_ValueBool
	{
		[return: MarshalAs(UnmanagedType.U1)]
		get
		{
			return ((bool*)m_pDtPar)[40];
		}
		[param: MarshalAs(UnmanagedType.U1)]
		set
		{
			((sbyte*)m_pDtPar)[40] = (value ? ((sbyte)1) : ((sbyte)0));
		}
	}

	public unsafe int m_ValueInt
	{
		get
		{
			return ((int*)m_pDtPar)[10];
		}
		set
		{
			((int*)m_pDtPar)[10] = value;
		}
	}

	public unsafe double m_ValueDouble
	{
		get
		{
			return ((double*)m_pDtPar)[5];
		}
		set
		{
			((double*)m_pDtPar)[5] = value;
		}
	}

	public unsafe DTAPI_RESULT m_Result => ((DTAPI_RESULT*)m_pDtPar)[2];

	public unsafe DTAPI_RESULT GetName(ref string rName, ref string rShortName)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out char* value);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out char* value2);
		uint num = global::_003CModule_003E.Dtapi_002EDtPar_002EGetName(m_pDtPar, &value, &value2);
		if (num == 0)
		{
			rName = new string(value);
			rShortName = new string(value2);
		}
		else
		{
			rName = null;
			rShortName = null;
		}
		return (DTAPI_RESULT)num;
	}

	public unsafe DTAPI_RESULT GetValue(ref bool Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		uint result = global::_003CModule_003E.Dtapi_002EDtPar_002EGetValue(m_pDtPar, &flag);
		Value = flag;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref double Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out double num);
		uint result = global::_003CModule_003E.Dtapi_002EDtPar_002EGetValue(m_pDtPar, &num);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref int Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtPar_002EGetValue(m_pDtPar, &num);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT SetId(int ParId)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtPar_002ESetId(m_pDtPar, ParId);
	}

	public unsafe DTAPI_RESULT SetValue([MarshalAs(UnmanagedType.U1)] bool Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtPar_002ESetValue(m_pDtPar, Value);
	}

	public unsafe DTAPI_RESULT SetValue(double Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtPar_002ESetValue(m_pDtPar, Value);
	}

	public unsafe DTAPI_RESULT SetValue(int Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtPar_002ESetValue(m_pDtPar, Value);
	}

	public unsafe DtPar(int ParId)
	{
		Dtapi.DtPar* ptr = (Dtapi.DtPar*)global::_003CModule_003E.@new(48u);
		Dtapi.DtPar* pDtPar;
		try
		{
			pDtPar = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bctor_007D(ptr, ParId));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 48u);
			throw;
		}
		m_pDtPar = pDtPar;
	}

	public unsafe DtPar()
	{
		Dtapi.DtPar* ptr = (Dtapi.DtPar*)global::_003CModule_003E.@new(48u);
		Dtapi.DtPar* pDtPar;
		try
		{
			pDtPar = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 48u);
			throw;
		}
		m_pDtPar = pDtPar;
	}

	private void _007EDtPar()
	{
		_0021DtPar();
	}

	private unsafe void _0021DtPar()
	{
		Dtapi.DtPar* pDtPar = m_pDtPar;
		if (pDtPar != null)
		{
			Dtapi.DtPar* ptr = pDtPar;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr)))((nint)ptr, 1u);
			m_pDtPar = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtPar();
			return;
		}
		try
		{
			_0021DtPar();
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

	~DtPar()
	{
		Dispose(A_0: false);
	}
}
