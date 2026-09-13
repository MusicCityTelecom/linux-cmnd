using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtCmmbPars
{
	private int m_Bandwidth;

	private int m_TsRate;

	private int m_TsPid;

	private int m_AreaId;

	private int m_TxId;

	private unsafe DTAPI_RESULT RetrieveTsRateFromTs(byte[] gBuffer, int NumBytes)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		ConvertToUnmgd(&dtCmmbPars);
		fixed (byte* ptr = &gBuffer[0])
		{
			uint result = global::_003CModule_003E.Dtapi_002EDtCmmbPars_002ERetrieveTsRateFromTs(&dtCmmbPars, (sbyte*)ptr, NumBytes);
			ConvertFromUnmgd(&dtCmmbPars);
			return (DTAPI_RESULT)result;
		}
	}

	[return: MarshalAs(UnmanagedType.U1)]
	private unsafe bool operator ==(DtCmmbPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars2);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars2);
		ConvertToUnmgd(&dtCmmbPars);
		Rhs.ConvertToUnmgd(&dtCmmbPars2);
		return global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_003D_003D(&dtCmmbPars, &dtCmmbPars2);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	private bool operator !=(DtCmmbPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtCmmbPars* uCmmbPars)
	{
		*(int*)uCmmbPars = m_Bandwidth;
		((int*)uCmmbPars)[1] = m_TsRate;
		((int*)uCmmbPars)[2] = m_TsPid;
		((int*)uCmmbPars)[3] = m_AreaId;
		((int*)uCmmbPars)[4] = m_TxId;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtCmmbPars* uCmmbPars)
	{
		m_Bandwidth = *(int*)uCmmbPars;
		m_TsRate = ((int*)uCmmbPars)[1];
		m_TsPid = ((int*)uCmmbPars)[2];
		m_AreaId = ((int*)uCmmbPars)[3];
		m_TxId = ((int*)uCmmbPars)[4];
	}

	public unsafe DtCmmbPars()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		ConvertFromUnmgd(&dtCmmbPars);
	}
}
