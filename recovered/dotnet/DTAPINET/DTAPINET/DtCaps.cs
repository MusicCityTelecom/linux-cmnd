using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtCaps : IDisposable
{
	internal unsafe Dtapi.DtCaps* m_pCaps;

	public unsafe DtCaps(int caps)
	{
		Dtapi.DtCaps* ptr = (Dtapi.DtCaps*)global::_003CModule_003E.@new(32u);
		Dtapi.DtCaps* pCaps;
		try
		{
			pCaps = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(ptr, caps));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 32u);
			throw;
		}
		m_pCaps = pCaps;
	}

	public unsafe DtCaps(Dtapi.DtCaps* Caps)
	{
		Dtapi.DtCaps* ptr = (Dtapi.DtCaps*)global::_003CModule_003E.@new(32u);
		Dtapi.DtCaps* ptr2;
		try
		{
			ptr2 = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 32u);
			throw;
		}
		m_pCaps = ptr2;
		// IL cpblk instruction
		System.Runtime.CompilerServices.Unsafe.CopyBlock(ptr2, Caps, 32);
	}

	public unsafe DtCaps(void* pCaps)
	{
		Dtapi.DtCaps* ptr = (Dtapi.DtCaps*)global::_003CModule_003E.@new(32u);
		Dtapi.DtCaps* ptr2;
		try
		{
			ptr2 = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 32u);
			throw;
		}
		m_pCaps = ptr2;
		// IL cpblk instruction
		System.Runtime.CompilerServices.Unsafe.CopyBlock(ptr2, pCaps, 32);
	}

	public unsafe DtCaps()
	{
		Dtapi.DtCaps* ptr = (Dtapi.DtCaps*)global::_003CModule_003E.@new(32u);
		Dtapi.DtCaps* pCaps;
		try
		{
			pCaps = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 32u);
			throw;
		}
		m_pCaps = pCaps;
	}

	private void _007EDtCaps()
	{
		_0021DtCaps();
	}

	private unsafe void _0021DtCaps()
	{
		Dtapi.DtCaps* pCaps = m_pCaps;
		if (pCaps != null)
		{
			global::_003CModule_003E.delete(pCaps, 32u);
		}
		m_pCaps = null;
	}

	public unsafe static DtCaps operator &(DtCaps Caps1, DtCaps Caps2)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps);
		return new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_0026(Caps1.m_pCaps, &dtCaps, Caps2.m_pCaps));
	}

	public unsafe static DtCaps operator |(DtCaps Caps1, DtCaps Caps2)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps);
		return new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007C(Caps1.m_pCaps, &dtCaps, Caps2.m_pCaps));
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe static bool operator ==(DtCaps Caps1, int Zero)
	{
		return global::_003CModule_003E.Dtapi_002EDtCaps_002E_003D_003D(Caps1.m_pCaps, Zero);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe static bool operator ==(DtCaps Caps1, DtCaps Caps2)
	{
		return global::_003CModule_003E.Dtapi_002EDtCaps_002E_003D_003D(Caps1.m_pCaps, Caps2.m_pCaps);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe static bool operator !=(DtCaps Caps1, int Zero)
	{
		return global::_003CModule_003E.Dtapi_002EDtCaps_002E_0021_003D(Caps1.m_pCaps, Zero);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe static bool operator !=(DtCaps Caps1, DtCaps Caps2)
	{
		return global::_003CModule_003E.Dtapi_002EDtCaps_002E_0021_003D(Caps1.m_pCaps, Caps2.m_pCaps);
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtCaps();
			return;
		}
		try
		{
			_0021DtCaps();
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

	~DtCaps()
	{
		Dispose(A_0: false);
	}
}
