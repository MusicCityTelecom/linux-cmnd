using Dtapi;

namespace DTAPINET;

public class DtAtsc3DemodL1BasicData
{
	public int m_Version;

	public int m_MimoScatPilotEnc;

	public bool m_LlsFlag;

	public int m_TimeInfoFlag;

	public bool m_ReturnChannelFlag;

	public int m_Papr;

	public int m_FrameLengthMode;

	public int m_FrameLength;

	public int m_ExcessSamples;

	public int m_TimeOffset;

	public int m_AdditionalSamples;

	public int m_NumSubframes;

	public int m_PreambleNumSymbols;

	public int m_PreambleReducedCarriers;

	public int m_L1DetailContentTag;

	public int m_L1DetailSize;

	public int m_L1DetailFecMode;

	public int m_L1DetailAddParity;

	public int m_L1DetailNumCells;

	public bool m_FirstSubMimo;

	public int m_FirstSubMiso;

	public int m_FirstSubFftSize;

	public int m_FirstSubReducedCarriers;

	public int m_FirstSubGuardInterval;

	public int m_FirstSubNumOfdmSymbols;

	public int m_FirstSubPilotPattern;

	public int m_FirstSubPilotBoost;

	public bool m_FirstSubSbsFirst;

	public bool m_FirstSubSbsLast;

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3DemodL1BasicData* uL1Basic)
	{
		*(int*)uL1Basic = m_Version;
		((int*)uL1Basic)[1] = m_MimoScatPilotEnc;
		((sbyte*)uL1Basic)[8] = (m_LlsFlag ? ((sbyte)1) : ((sbyte)0));
		((int*)uL1Basic)[3] = m_TimeInfoFlag;
		((sbyte*)uL1Basic)[16] = (m_ReturnChannelFlag ? ((sbyte)1) : ((sbyte)0));
		((int*)uL1Basic)[5] = m_Papr;
		((int*)uL1Basic)[6] = m_FrameLengthMode;
		((int*)uL1Basic)[7] = m_FrameLength;
		((int*)uL1Basic)[8] = m_ExcessSamples;
		((int*)uL1Basic)[9] = m_TimeOffset;
		((int*)uL1Basic)[10] = m_AdditionalSamples;
		((int*)uL1Basic)[11] = m_NumSubframes;
		((int*)uL1Basic)[12] = m_PreambleNumSymbols;
		((int*)uL1Basic)[13] = m_PreambleReducedCarriers;
		((int*)uL1Basic)[14] = m_L1DetailContentTag;
		((int*)uL1Basic)[15] = m_L1DetailSize;
		((int*)uL1Basic)[16] = m_L1DetailFecMode;
		((int*)uL1Basic)[17] = m_L1DetailAddParity;
		((int*)uL1Basic)[18] = m_L1DetailNumCells;
		((sbyte*)uL1Basic)[76] = (m_FirstSubMimo ? ((sbyte)1) : ((sbyte)0));
		((int*)uL1Basic)[20] = m_FirstSubMiso;
		((int*)uL1Basic)[21] = m_FirstSubFftSize;
		((int*)uL1Basic)[22] = m_FirstSubReducedCarriers;
		((int*)uL1Basic)[23] = m_FirstSubGuardInterval;
		((int*)uL1Basic)[24] = m_FirstSubNumOfdmSymbols;
		((int*)uL1Basic)[25] = m_FirstSubPilotPattern;
		((int*)uL1Basic)[26] = m_FirstSubPilotBoost;
		((sbyte*)uL1Basic)[108] = (m_FirstSubSbsFirst ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uL1Basic)[109] = (m_FirstSubSbsLast ? ((sbyte)1) : ((sbyte)0));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3DemodL1BasicData* uL1Basic)
	{
		m_Version = *(int*)uL1Basic;
		m_MimoScatPilotEnc = ((int*)uL1Basic)[1];
		m_LlsFlag = ((bool*)uL1Basic)[8];
		m_TimeInfoFlag = ((int*)uL1Basic)[3];
		m_ReturnChannelFlag = ((bool*)uL1Basic)[16];
		m_Papr = ((int*)uL1Basic)[5];
		m_FrameLengthMode = ((int*)uL1Basic)[6];
		m_FrameLength = ((int*)uL1Basic)[7];
		m_ExcessSamples = ((int*)uL1Basic)[8];
		m_TimeOffset = ((int*)uL1Basic)[9];
		m_AdditionalSamples = ((int*)uL1Basic)[10];
		m_NumSubframes = ((int*)uL1Basic)[11];
		m_PreambleNumSymbols = ((int*)uL1Basic)[12];
		m_PreambleReducedCarriers = ((int*)uL1Basic)[13];
		m_L1DetailContentTag = ((int*)uL1Basic)[14];
		m_L1DetailSize = ((int*)uL1Basic)[15];
		m_L1DetailFecMode = ((int*)uL1Basic)[16];
		m_L1DetailAddParity = ((int*)uL1Basic)[17];
		m_L1DetailNumCells = ((int*)uL1Basic)[18];
		m_FirstSubMimo = ((bool*)uL1Basic)[76];
		m_FirstSubMiso = ((int*)uL1Basic)[20];
		m_FirstSubFftSize = ((int*)uL1Basic)[21];
		m_FirstSubReducedCarriers = ((int*)uL1Basic)[22];
		m_FirstSubGuardInterval = ((int*)uL1Basic)[23];
		m_FirstSubNumOfdmSymbols = ((int*)uL1Basic)[24];
		m_FirstSubPilotPattern = ((int*)uL1Basic)[25];
		m_FirstSubPilotBoost = ((int*)uL1Basic)[26];
		m_FirstSubSbsFirst = ((bool*)uL1Basic)[108];
		m_FirstSubSbsLast = ((bool*)uL1Basic)[109];
	}
}
