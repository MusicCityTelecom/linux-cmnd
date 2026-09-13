using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbC2DemodL1PlpSigDataPlp
{
	public int m_Id;

	public int m_FecType;

	public int m_Modulation;

	public int m_CodeRate;

	public int m_HdrCntr;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2DemodL1PlpSigDataPlp* uC2Pars)
	{
		*(int*)uC2Pars = m_Id;
		((int*)uC2Pars)[1] = m_FecType;
		((int*)uC2Pars)[2] = m_Modulation;
		((int*)uC2Pars)[3] = m_CodeRate;
		((int*)uC2Pars)[4] = m_HdrCntr;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2DemodL1PlpSigDataPlp* uC2Pars)
	{
		m_Id = *(int*)uC2Pars;
		m_FecType = ((int*)uC2Pars)[1];
		m_Modulation = ((int*)uC2Pars)[2];
		m_CodeRate = ((int*)uC2Pars)[3];
		m_HdrCntr = ((int*)uC2Pars)[4];
	}

	public unsafe DtDvbC2DemodL1PlpSigDataPlp()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1PlpSigDataPlp dtDvbC2DemodL1PlpSigDataPlp);
		global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1PlpSigDataPlp_002E_007Bctor_007D(&dtDvbC2DemodL1PlpSigDataPlp);
		ConvertFromUnmgd(&dtDvbC2DemodL1PlpSigDataPlp);
	}
}
