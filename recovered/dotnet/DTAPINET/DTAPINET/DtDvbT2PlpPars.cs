using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbT2PlpPars
{
	public bool m_Hem;

	public bool m_Npd;

	public int m_Issy;

	public int m_IssyBufs;

	public int m_IssyTDesign;

	public int m_CompensatingDelay;

	public int m_TsRate;

	public int m_GseLabelType;

	public int m_Id;

	public int m_GroupId;

	public int m_Type;

	public int m_PayloadType;

	public int m_CodeRate;

	public int m_Modulation;

	public bool m_Rotation;

	public int m_FecType;

	public int m_FrameInterval;

	public int m_FirstFrameIdx;

	public int m_TimeIlLength;

	public int m_TimeIlType;

	public bool m_InBandAFlag;

	public bool m_InBandBFlag;

	public int m_NumBlocks;

	public bool m_PlpMute;

	public int m_NumOtherPlpInBand;

	public int[] m_OtherPlpInBand;

	public bool m_FfFlag;

	public int m_FirstRfIdx;

	public unsafe void Init(int PlpId)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2PlpPars dtDvbT2PlpPars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2PlpPars_002EInit(&dtDvbT2PlpPars, PlpId);
		ConvertFromUnmgd(&dtDvbT2PlpPars);
	}

	internal DtDvbT2PlpPars()
	{
		m_OtherPlpInBand = new int[254];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2PlpPars* uT2PlpPars)
	{
		*(bool*)uT2PlpPars = m_Hem;
		((sbyte*)uT2PlpPars)[1] = (m_Npd ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2PlpPars)[1] = m_Issy;
		((int*)uT2PlpPars)[2] = m_IssyBufs;
		((int*)uT2PlpPars)[3] = m_IssyTDesign;
		((int*)uT2PlpPars)[4] = m_CompensatingDelay;
		((int*)uT2PlpPars)[5] = m_TsRate;
		((int*)uT2PlpPars)[6] = m_GseLabelType;
		((int*)uT2PlpPars)[7] = m_Id;
		((int*)uT2PlpPars)[8] = m_GroupId;
		((int*)uT2PlpPars)[9] = m_Type;
		((int*)uT2PlpPars)[10] = m_PayloadType;
		((int*)uT2PlpPars)[11] = m_CodeRate;
		((int*)uT2PlpPars)[12] = m_Modulation;
		((sbyte*)uT2PlpPars)[52] = (m_Rotation ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2PlpPars)[14] = m_FecType;
		((int*)uT2PlpPars)[15] = m_FrameInterval;
		((int*)uT2PlpPars)[16] = m_FirstFrameIdx;
		((int*)uT2PlpPars)[17] = m_TimeIlLength;
		((int*)uT2PlpPars)[18] = m_TimeIlType;
		((sbyte*)uT2PlpPars)[76] = (m_InBandAFlag ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uT2PlpPars)[77] = (m_InBandBFlag ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2PlpPars)[20] = m_NumBlocks;
		((int*)uT2PlpPars)[22] = m_NumOtherPlpInBand;
		int num = 0;
		Dtapi.DtDvbT2PlpPars* ptr = (Dtapi.DtDvbT2PlpPars*)((byte*)uT2PlpPars + 92);
		do
		{
			*(int*)ptr = m_OtherPlpInBand[num];
			num++;
			ptr = (Dtapi.DtDvbT2PlpPars*)((byte*)ptr + 4);
		}
		while (num < 254);
		((sbyte*)uT2PlpPars)[1108] = (m_FfFlag ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2PlpPars)[278] = m_FirstRfIdx;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2PlpPars* uT2PlpPars)
	{
		m_Hem = *(bool*)uT2PlpPars;
		m_Npd = ((bool*)uT2PlpPars)[1];
		m_Issy = ((int*)uT2PlpPars)[1];
		m_IssyBufs = ((int*)uT2PlpPars)[2];
		m_IssyTDesign = ((int*)uT2PlpPars)[3];
		m_CompensatingDelay = ((int*)uT2PlpPars)[4];
		m_TsRate = ((int*)uT2PlpPars)[5];
		m_GseLabelType = ((int*)uT2PlpPars)[6];
		m_Id = ((int*)uT2PlpPars)[7];
		m_GroupId = ((int*)uT2PlpPars)[8];
		m_Type = ((int*)uT2PlpPars)[9];
		m_PayloadType = ((int*)uT2PlpPars)[10];
		m_CodeRate = ((int*)uT2PlpPars)[11];
		m_Modulation = ((int*)uT2PlpPars)[12];
		m_Rotation = ((bool*)uT2PlpPars)[52];
		m_FecType = ((int*)uT2PlpPars)[14];
		m_FrameInterval = ((int*)uT2PlpPars)[15];
		m_FirstFrameIdx = ((int*)uT2PlpPars)[16];
		m_TimeIlLength = ((int*)uT2PlpPars)[17];
		m_TimeIlType = ((int*)uT2PlpPars)[18];
		m_InBandAFlag = ((bool*)uT2PlpPars)[76];
		m_InBandBFlag = ((bool*)uT2PlpPars)[77];
		m_NumBlocks = ((int*)uT2PlpPars)[20];
		m_NumOtherPlpInBand = ((int*)uT2PlpPars)[22];
		int num = 0;
		Dtapi.DtDvbT2PlpPars* ptr = (Dtapi.DtDvbT2PlpPars*)((byte*)uT2PlpPars + 92);
		do
		{
			ref int reference = ref m_OtherPlpInBand[num];
			reference = *(int*)ptr;
			num++;
			ptr = (Dtapi.DtDvbT2PlpPars*)((byte*)ptr + 4);
		}
		while (num < 254);
		m_FfFlag = ((bool*)uT2PlpPars)[1108];
		m_FirstRfIdx = ((int*)uT2PlpPars)[278];
	}
}
