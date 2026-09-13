using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbC2DemodL1Part2Plp
{
	public int m_Id;

	public int m_Bundled;

	public int m_Type;

	public int m_PayloadType;

	public int m_GroupId;

	public int m_Start;

	public int m_FecType;

	public int m_Modulation;

	public int m_CodeRate;

	public int m_PsiSiReproc;

	public int m_TsId;

	public int m_OnwId;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2DemodL1Part2Plp* uC2L1Pars)
	{
		*(int*)uC2L1Pars = m_Id;
		((int*)uC2L1Pars)[1] = m_Bundled;
		((int*)uC2L1Pars)[2] = m_Type;
		((int*)uC2L1Pars)[3] = m_PayloadType;
		((int*)uC2L1Pars)[4] = m_GroupId;
		((int*)uC2L1Pars)[5] = m_Start;
		((int*)uC2L1Pars)[6] = m_FecType;
		((int*)uC2L1Pars)[7] = m_Modulation;
		((int*)uC2L1Pars)[8] = m_CodeRate;
		((int*)uC2L1Pars)[9] = m_PsiSiReproc;
		((int*)uC2L1Pars)[10] = m_TsId;
		((int*)uC2L1Pars)[11] = m_OnwId;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2DemodL1Part2Plp* uC2L1Pars)
	{
		m_Id = *(int*)uC2L1Pars;
		m_Bundled = ((int*)uC2L1Pars)[1];
		m_Type = ((int*)uC2L1Pars)[2];
		m_PayloadType = ((int*)uC2L1Pars)[3];
		m_GroupId = ((int*)uC2L1Pars)[4];
		m_Start = ((int*)uC2L1Pars)[5];
		m_FecType = ((int*)uC2L1Pars)[6];
		m_Modulation = ((int*)uC2L1Pars)[7];
		m_CodeRate = ((int*)uC2L1Pars)[8];
		m_PsiSiReproc = ((int*)uC2L1Pars)[9];
		m_TsId = ((int*)uC2L1Pars)[10];
		m_OnwId = ((int*)uC2L1Pars)[11];
	}

	public unsafe DtDvbC2DemodL1Part2Plp()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1Part2Plp dtDvbC2DemodL1Part2Plp);
		global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2Plp_002E_007Bctor_007D(&dtDvbC2DemodL1Part2Plp);
		ConvertFromUnmgd(&dtDvbC2DemodL1Part2Plp);
	}
}
