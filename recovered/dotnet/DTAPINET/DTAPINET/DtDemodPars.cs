using System;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtDemodPars
{
	private int m_ModType;

	private IDemodPars m_DemodPars;

	public DtDemodPars()
	{
		m_ModType = -1;
		m_DemodPars = null;
	}

	public int GetModType()
	{
		return m_ModType;
	}

	public DTAPI_RESULT SetModType(int ModType)
	{
		if (ModType == m_ModType)
		{
			return DTAPI_RESULT.OK;
		}
		switch (ModType)
		{
		case 10:
			m_DemodPars = new DtDemodParsAtsc();
			goto case -1;
		case 69:
			m_DemodPars = new DtDemodParsAtsc3();
			goto case -1;
		case 53:
			m_DemodPars = new DtDemodParsDab();
			goto case -1;
		case 52:
			m_DemodPars = new DtDemodParsDvbC2();
			goto case -1;
		case 0:
			m_DemodPars = new DtDemodParsDvbS();
			goto case -1;
		case 32:
		case 33:
		case 34:
		case 35:
			m_DemodPars = new DtDemodParsDvbS2();
			goto case -1;
		case 9:
			m_DemodPars = new DtDemodParsDvbT();
			goto case -1;
		case 11:
			m_DemodPars = new DtDemodParsDvbT2();
			goto case -1;
		case 15:
			m_DemodPars = new DtDemodParsIq();
			goto case -1;
		case 16:
			m_DemodPars = new DtDemodParsIq2131();
			goto case -1;
		case 12:
			m_DemodPars = new DtDemodParsIsdbt();
			goto case -1;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 54:
			m_DemodPars = new DtDemodParsQam();
			goto case -1;
		case -1:
			m_ModType = ModType;
			return DTAPI_RESULT.OK;
		default:
			return DTAPI_RESULT.E_INVALID_MODTYPE;
		}
	}

	public DtDemodParsAtsc Atsc()
	{
		return m_DemodPars as DtDemodParsAtsc;
	}

	public DtDemodParsAtsc3 Atsc3()
	{
		return m_DemodPars as DtDemodParsAtsc3;
	}

	public DtDemodParsDab Dab()
	{
		return m_DemodPars as DtDemodParsDab;
	}

	public DtDemodParsDvbC2 DvbC2()
	{
		return m_DemodPars as DtDemodParsDvbC2;
	}

	public DtDemodParsDvbS DvbS()
	{
		return m_DemodPars as DtDemodParsDvbS;
	}

	public DtDemodParsDvbS2 DvbS2()
	{
		return m_DemodPars as DtDemodParsDvbS2;
	}

	public DtDemodParsDvbT DvbT()
	{
		return m_DemodPars as DtDemodParsDvbT;
	}

	public DtDemodParsDvbT2 DvbT2()
	{
		return m_DemodPars as DtDemodParsDvbT2;
	}

	public DtDemodParsIq Iq()
	{
		return m_DemodPars as DtDemodParsIq;
	}

	public DtDemodParsIq2131 Iq2131()
	{
		return m_DemodPars as DtDemodParsIq2131;
	}

	public DtDemodParsIsdbt Isdbt()
	{
		return m_DemodPars as DtDemodParsIsdbt;
	}

	public DtDemodParsQam Qam()
	{
		return m_DemodPars as DtDemodParsQam;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsAtsc()
	{
		return m_ModType == 10;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsAtsc3()
	{
		return m_ModType == 69;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsDab()
	{
		return m_ModType == 53;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsDvbC2()
	{
		return m_ModType == 52;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsDvbS()
	{
		return m_ModType == 0;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsDvbS2()
	{
		int modType = m_ModType;
		int num = ((modType == 32 || modType == 33 || modType == 34 || modType == 35) ? 1 : 0);
		return (byte)num != 0;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsDvbT()
	{
		return m_ModType == 9;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsDvbT2()
	{
		return m_ModType == 11;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsIq()
	{
		return m_ModType == 15;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsIq2131()
	{
		return m_ModType == 16;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsIsdbt()
	{
		return m_ModType == 12;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool IsQam()
	{
		int modType = m_ModType;
		int num = ((modType == 3 || modType == 4 || modType == 5 || modType == 6 || modType == 7 || modType == 8 || modType == 54) ? 1 : 0);
		return (byte)num != 0;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDemodPars* uDemodPars)
	{
		SetModType(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EGetModType(uDemodPars));
		switch (global::_003CModule_003E.Dtapi_002EDtDemodPars_002EGetModType(uDemodPars))
		{
		case 10:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EAtsc(uDemodPars));
			break;
		case 69:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EAtsc3(uDemodPars));
			break;
		case 53:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDab(uDemodPars));
			break;
		case 52:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbC2(uDemodPars));
			break;
		case 0:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbS(uDemodPars));
			break;
		case 32:
		case 33:
		case 34:
		case 35:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbS2(uDemodPars));
			break;
		case 9:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbT(uDemodPars));
			break;
		case 11:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbT2(uDemodPars));
			break;
		case 15:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EIq(uDemodPars));
			break;
		case 16:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EIq2131(uDemodPars));
			break;
		case 12:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EIsdbt(uDemodPars));
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 54:
			m_DemodPars.ConvertFromUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EQam(uDemodPars));
			break;
		default:
			throw new Exception("Invalid modulation type");
		case -1:
			break;
		}
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDemodPars* uDemodPars)
	{
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002ESetModType(uDemodPars, m_ModType);
		switch (m_ModType)
		{
		case 10:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EAtsc(uDemodPars));
			break;
		case 69:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EAtsc3(uDemodPars));
			break;
		case 53:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDab(uDemodPars));
			break;
		case 52:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbC2(uDemodPars));
			break;
		case 0:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbS(uDemodPars));
			break;
		case 32:
		case 33:
		case 34:
		case 35:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbS2(uDemodPars));
			break;
		case 9:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbT(uDemodPars));
			break;
		case 11:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EDvbT2(uDemodPars));
			break;
		case 15:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EIq(uDemodPars));
			break;
		case 16:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EIq2131(uDemodPars));
			break;
		case 12:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EIsdbt(uDemodPars));
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 54:
			m_DemodPars.ConvertToUnmgd(global::_003CModule_003E.Dtapi_002EDtDemodPars_002EQam(uDemodPars));
			break;
		default:
			throw new Exception("Invalid modulation type");
		case -1:
			break;
		}
	}
}
