using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbT2DemodL1PostPlp
{
	public int m_Id;

	public int m_Type;

	public int m_PayloadType;

	public int m_FfFlag;

	public int m_FirstRfIdx;

	public int m_FirstFrameIdx;

	public int m_GroupId;

	public int m_CodeRate;

	public int m_Modulation;

	public int m_Rotation;

	public int m_FecType;

	public int m_NumBlocks;

	public int m_FrameInterval;

	public int m_TimeIlLength;

	public int m_TimeIlType;

	public int m_InBandAFlag;

	public int m_InBandBFlag;

	public int m_Reserved1;

	public int m_PlpMode;

	public int m_Static;

	public int m_StaticPadding;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2DemodL1PostPlp* uT2L1Pars)
	{
		*(int*)uT2L1Pars = m_Id;
		((int*)uT2L1Pars)[1] = m_Type;
		((int*)uT2L1Pars)[2] = m_PayloadType;
		((int*)uT2L1Pars)[3] = m_FfFlag;
		((int*)uT2L1Pars)[4] = m_FirstRfIdx;
		((int*)uT2L1Pars)[5] = m_FirstFrameIdx;
		((int*)uT2L1Pars)[6] = m_GroupId;
		((int*)uT2L1Pars)[7] = m_CodeRate;
		((int*)uT2L1Pars)[8] = m_Modulation;
		((int*)uT2L1Pars)[9] = m_Rotation;
		((int*)uT2L1Pars)[10] = m_FecType;
		((int*)uT2L1Pars)[11] = m_NumBlocks;
		((int*)uT2L1Pars)[12] = m_FrameInterval;
		((int*)uT2L1Pars)[13] = m_TimeIlLength;
		((int*)uT2L1Pars)[14] = m_TimeIlType;
		((int*)uT2L1Pars)[15] = m_InBandAFlag;
		((int*)uT2L1Pars)[16] = m_InBandBFlag;
		((int*)uT2L1Pars)[17] = m_Reserved1;
		((int*)uT2L1Pars)[18] = m_PlpMode;
		((int*)uT2L1Pars)[19] = m_Static;
		((int*)uT2L1Pars)[20] = m_StaticPadding;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2DemodL1PostPlp* uT2L1Pars)
	{
		m_Id = *(int*)uT2L1Pars;
		m_Type = ((int*)uT2L1Pars)[1];
		m_PayloadType = ((int*)uT2L1Pars)[2];
		m_FfFlag = ((int*)uT2L1Pars)[3];
		m_FirstRfIdx = ((int*)uT2L1Pars)[4];
		m_FirstFrameIdx = ((int*)uT2L1Pars)[5];
		m_GroupId = ((int*)uT2L1Pars)[6];
		m_CodeRate = ((int*)uT2L1Pars)[7];
		m_Modulation = ((int*)uT2L1Pars)[8];
		m_Rotation = ((int*)uT2L1Pars)[9];
		m_FecType = ((int*)uT2L1Pars)[10];
		m_NumBlocks = ((int*)uT2L1Pars)[11];
		m_FrameInterval = ((int*)uT2L1Pars)[12];
		m_TimeIlLength = ((int*)uT2L1Pars)[13];
		m_TimeIlType = ((int*)uT2L1Pars)[14];
		m_InBandAFlag = ((int*)uT2L1Pars)[15];
		m_InBandBFlag = ((int*)uT2L1Pars)[16];
		m_Reserved1 = ((int*)uT2L1Pars)[17];
		m_PlpMode = ((int*)uT2L1Pars)[18];
		m_Static = ((int*)uT2L1Pars)[19];
		m_StaticPadding = ((int*)uT2L1Pars)[20];
	}

	public unsafe DtDvbT2DemodL1PostPlp()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2DemodL1PostPlp dtDvbT2DemodL1PostPlp);
		global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1PostPlp_002E_007Bctor_007D(&dtDvbT2DemodL1PostPlp);
		ConvertFromUnmgd(&dtDvbT2DemodL1PostPlp);
	}
}
