using Dtapi;

namespace DTAPINET;

public class DtAtsc3DemodL1PlpData
{
	public int m_Id;

	public bool m_LlsFlag;

	public int m_Layer;

	public int m_Start;

	public int m_Size;

	public int m_ScramblerType;

	public int m_FecCodeLength;

	public int m_FecOuterCode;

	public int m_Modulation;

	public int m_CodeRate;

	public int m_TiMode;

	public int m_FecFrameStart;

	public int m_CtiFecFrameStart;

	public int m_NumChannelBonded;

	public int m_ChannelBondingFormat;

	public int[] m_BondedRfId;

	public int m_PlpType;

	public int m_NumSubslices;

	public int m_SubsliceInterval;

	public bool m_TiExtInterleaving;

	public int m_CtiDepth;

	public int m_CtiStartRow;

	public bool m_HtiInterSubframe;

	public int m_HtiNumTiBlocks;

	public int m_HtiNumFecBlocksMax;

	public int[] m_HtiNumFecBlocks;

	public bool m_HtiCellInterleaver;

	public int m_LdmInjectLevel;

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3DemodL1PlpData* uPlpData)
	{
		*(int*)uPlpData = m_Id;
		((sbyte*)uPlpData)[4] = (m_LlsFlag ? ((sbyte)1) : ((sbyte)0));
		((int*)uPlpData)[2] = m_Layer;
		((int*)uPlpData)[3] = m_Start;
		((int*)uPlpData)[4] = m_Size;
		((int*)uPlpData)[5] = m_ScramblerType;
		((int*)uPlpData)[6] = m_FecCodeLength;
		((int*)uPlpData)[7] = m_FecOuterCode;
		((int*)uPlpData)[8] = m_Modulation;
		((int*)uPlpData)[9] = m_CodeRate;
		((int*)uPlpData)[10] = m_TiMode;
		((int*)uPlpData)[11] = m_FecFrameStart;
		((int*)uPlpData)[12] = m_CtiFecFrameStart;
		((int*)uPlpData)[13] = m_NumChannelBonded;
		((int*)uPlpData)[14] = m_ChannelBondingFormat;
		int num = 0;
		Dtapi.DtAtsc3DemodL1PlpData* ptr = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)uPlpData + 60);
		do
		{
			*(int*)ptr = m_BondedRfId[num];
			num++;
			ptr = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)ptr + 4);
		}
		while (num < 8);
		((int*)uPlpData)[23] = m_PlpType;
		((int*)uPlpData)[24] = m_NumSubslices;
		((int*)uPlpData)[25] = m_SubsliceInterval;
		((sbyte*)uPlpData)[104] = (m_TiExtInterleaving ? ((sbyte)1) : ((sbyte)0));
		((int*)uPlpData)[27] = m_CtiDepth;
		((int*)uPlpData)[28] = m_CtiStartRow;
		((sbyte*)uPlpData)[116] = (m_HtiInterSubframe ? ((sbyte)1) : ((sbyte)0));
		((int*)uPlpData)[30] = m_HtiNumTiBlocks;
		((int*)uPlpData)[31] = m_HtiNumFecBlocksMax;
		int num2 = 0;
		Dtapi.DtAtsc3DemodL1PlpData* ptr2 = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)uPlpData + 128);
		do
		{
			*(int*)ptr2 = m_HtiNumFecBlocks[num2];
			num2++;
			ptr2 = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)ptr2 + 4);
		}
		while (num2 < 16);
		((sbyte*)uPlpData)[192] = (m_HtiCellInterleaver ? ((sbyte)1) : ((sbyte)0));
		((int*)uPlpData)[49] = m_LdmInjectLevel;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3DemodL1PlpData* uPlpData)
	{
		m_Id = *(int*)uPlpData;
		m_LlsFlag = ((bool*)uPlpData)[4];
		m_Layer = ((int*)uPlpData)[2];
		m_Start = ((int*)uPlpData)[3];
		m_Size = ((int*)uPlpData)[4];
		m_ScramblerType = ((int*)uPlpData)[5];
		m_FecCodeLength = ((int*)uPlpData)[6];
		m_FecOuterCode = ((int*)uPlpData)[7];
		m_Modulation = ((int*)uPlpData)[8];
		m_CodeRate = ((int*)uPlpData)[9];
		m_TiMode = ((int*)uPlpData)[10];
		m_FecFrameStart = ((int*)uPlpData)[11];
		m_CtiFecFrameStart = ((int*)uPlpData)[12];
		m_NumChannelBonded = ((int*)uPlpData)[13];
		m_ChannelBondingFormat = ((int*)uPlpData)[14];
		m_BondedRfId = new int[8];
		int num = 0;
		Dtapi.DtAtsc3DemodL1PlpData* ptr = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)uPlpData + 60);
		do
		{
			ref int reference = ref m_BondedRfId[num];
			reference = *(int*)ptr;
			num++;
			ptr = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)ptr + 4);
		}
		while (num < 8);
		m_PlpType = ((int*)uPlpData)[23];
		m_NumSubslices = ((int*)uPlpData)[24];
		m_SubsliceInterval = ((int*)uPlpData)[25];
		m_TiExtInterleaving = ((bool*)uPlpData)[104];
		m_CtiDepth = ((int*)uPlpData)[27];
		m_CtiStartRow = ((int*)uPlpData)[28];
		m_HtiInterSubframe = ((bool*)uPlpData)[116];
		m_HtiNumTiBlocks = ((int*)uPlpData)[30];
		m_HtiNumFecBlocksMax = ((int*)uPlpData)[31];
		m_HtiNumFecBlocks = new int[16];
		int num2 = 0;
		Dtapi.DtAtsc3DemodL1PlpData* ptr2 = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)uPlpData + 128);
		do
		{
			ref int reference2 = ref m_HtiNumFecBlocks[num2];
			reference2 = *(int*)ptr2;
			num2++;
			ptr2 = (Dtapi.DtAtsc3DemodL1PlpData*)((byte*)ptr2 + 4);
		}
		while (num2 < 16);
		m_HtiCellInterleaver = ((bool*)uPlpData)[192];
		m_LdmInjectLevel = ((int*)uPlpData)[49];
	}
}
