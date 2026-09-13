using System;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtTestPointOutPars : IDisposable
{
	public delegate void TpWriteDataFunc(int TpIndex, int StreamIndex, object rBuffer, int Length, int Format, double Mult, [MarshalAs(UnmanagedType.U1)] bool IsNewFrame);

	public bool m_Enabled;

	public TpWriteDataFunc m_WriteDataFunc;

	private unsafe TpOutHelper* m_TpOutHelper;

	private void _007EDtTestPointOutPars()
	{
		_0021DtTestPointOutPars();
	}

	private unsafe void _0021DtTestPointOutPars()
	{
		TpOutHelper* tpOutHelper = m_TpOutHelper;
		if (tpOutHelper != null)
		{
			TpOutHelper* ptr = tpOutHelper;
			global::_003CModule_003E.gcroot_003CDTAPINET_003A_003ADtTestPointOutPars_0020_005E_003E_002E_007Bdtor_007D((gcroot_003CDTAPINET_003A_003ADtTestPointOutPars_0020_005E_003E*)ptr);
			global::_003CModule_003E.delete(ptr, 4u);
			m_TpOutHelper = null;
		}
	}

	internal unsafe DtTestPointOutPars()
	{
		m_Enabled = false;
		m_WriteDataFunc = null;
		TpOutHelper* ptr = (TpOutHelper*)global::_003CModule_003E.@new(4u);
		TpOutHelper* tpOutHelper;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				tpOutHelper = ptr;
			}
			else
			{
				tpOutHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 4u);
			throw;
		}
		m_TpOutHelper = tpOutHelper;
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtTestPointOutPars* uTpOutPars)
	{
		*(bool*)uTpOutPars = m_Enabled;
		((int*)uTpOutPars)[1] = (int)m_TpOutHelper;
		((int*)uTpOutPars)[2] = (int)global::_003CModule_003E.__unep_0040_003FOnTpWriteData_0040TpOutHelper_0040DTAPINET_0040_0040_0024_0024FSAXPAXHHPBXHHMH_0040Z;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtTestPointOutPars* uTpOutPars)
	{
		m_Enabled = *(bool*)uTpOutPars;
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtTestPointOutPars();
			return;
		}
		try
		{
			_0021DtTestPointOutPars();
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

	~DtTestPointOutPars()
	{
		Dispose(A_0: false);
	}
}
