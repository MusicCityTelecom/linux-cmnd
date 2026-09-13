using System;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbT2RbmValidation : IDisposable
{
	public delegate void RbmEventHandler(DtDvbT2RbmEvent RbmArgs);

	public bool m_Enabled;

	public bool m_PlotEnabled;

	public int m_PlotPeriod;

	public RbmEventHandler m_RbmEvent;

	private unsafe RbmEventHelper* m_RbmEventHelper;

	private void _007EDtDvbT2RbmValidation()
	{
		_0021DtDvbT2RbmValidation();
	}

	private unsafe void _0021DtDvbT2RbmValidation()
	{
		RbmEventHelper* rbmEventHelper = m_RbmEventHelper;
		if (rbmEventHelper != null)
		{
			RbmEventHelper* ptr = rbmEventHelper;
			global::_003CModule_003E.gcroot_003CDTAPINET_003A_003ADtDvbT2RbmValidation_0020_005E_003E_002E_007Bdtor_007D((gcroot_003CDTAPINET_003A_003ADtDvbT2RbmValidation_0020_005E_003E*)ptr);
			global::_003CModule_003E.delete(ptr, 4u);
			m_RbmEventHelper = null;
		}
	}

	internal unsafe DtDvbT2RbmValidation()
	{
		m_Enabled = false;
		m_RbmEvent = null;
		RbmEventHelper* ptr = (RbmEventHelper*)global::_003CModule_003E.@new(4u);
		RbmEventHelper* rbmEventHelper;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				rbmEventHelper = ptr;
			}
			else
			{
				rbmEventHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 4u);
			throw;
		}
		m_RbmEventHelper = rbmEventHelper;
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2RbmValidation* uRbmValPars)
	{
		*(bool*)uRbmValPars = m_Enabled;
		((sbyte*)uRbmValPars)[1] = (m_PlotEnabled ? ((sbyte)1) : ((sbyte)0));
		((int*)uRbmValPars)[1] = m_PlotPeriod;
		((int*)uRbmValPars)[2] = (int)m_RbmEventHelper;
		((int*)uRbmValPars)[3] = (int)global::_003CModule_003E.__unep_0040_003FOnRbmEvent_0040RbmEventHelper_0040DTAPINET_0040_0040_0024_0024FSAXPAXPBUDtDvbT2RbmEvent_0040Dtapi_0040_0040_0040Z;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2RbmValidation* uRbmValPars)
	{
		m_Enabled = *(bool*)uRbmValPars;
		m_PlotEnabled = ((bool*)uRbmValPars)[1];
		m_PlotPeriod = ((int*)uRbmValPars)[1];
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtDvbT2RbmValidation();
			return;
		}
		try
		{
			_0021DtDvbT2RbmValidation();
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

	~DtDvbT2RbmValidation()
	{
		Dispose(A_0: false);
	}
}
