using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtIsdbS3Pars
{
	public int m_SymRate;

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbS3Pars dtIsdbS3Pars);
		*(int*)(&dtIsdbS3Pars) = m_SymRate;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtIsdbS3Pars_002ECheckValidity(&dtIsdbS3Pars);
	}

	public void Init()
	{
		m_SymRate = 33756100;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtIsdbS3Pars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbS3Pars dtIsdbS3Pars);
		*(int*)(&dtIsdbS3Pars) = m_SymRate;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbS3Pars dtIsdbS3Pars2);
		*(int*)(&dtIsdbS3Pars2) = Rhs.m_SymRate;
		return global::_003CModule_003E.Dtapi_002EDtIsdbS3Pars_002E_003D_003D(&dtIsdbS3Pars, &dtIsdbS3Pars2);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtIsdbS3Pars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIsdbS3Pars* uIsdbS3Pars)
	{
		*(int*)uIsdbS3Pars = m_SymRate;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIsdbS3Pars* uIsdbS3Pars)
	{
		m_SymRate = *(int*)uIsdbS3Pars;
	}

	public DtIsdbS3Pars()
	{
		m_SymRate = 33756100;
	}
}
