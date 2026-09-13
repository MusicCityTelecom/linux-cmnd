using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbT2DemodL1Data
{
	public struct DtDvbT2DemodL1P1
	{
		public bool m_Valid;

		public int m_FftMode;

		public int m_Miso;

		public int m_Fef;

		public int m_T2Profile;
	}

	public struct DtDvbT2DemodL1Pre
	{
		public bool m_Valid;

		public int m_Type;

		public int m_BwtExt;

		public int m_S1;

		public int m_S2;

		public int m_L1Repetition;

		public int m_GuardInterval;

		public int m_Papr;

		public int m_L1Modulation;

		public int m_L1CodeRate;

		public int m_L1FecType;

		public int m_L1PostSize;

		public int m_l1PostInfoSize;

		public int m_PilotPattern;

		public int m_TxIdAvailability;

		public int m_CellId;

		public int m_NetworkId;

		public int m_T2SystemId;

		public int m_NumT2Frames;

		public int m_NumDataSyms;

		public int m_RegenFlag;

		public int m_L1PostExt;

		public int m_NumRfChans;

		public int m_CurrentRfIdx;

		public int m_T2Version;

		public int m_L1PostScrambling;

		public int m_T2BaseLite;
	}

	public struct DtDvbT2DemodL1Post
	{
		public bool m_Valid;

		public int m_NumSubslices;

		public int m_NumPlps;

		public int m_NumAux;

		public List<DtDvbT2DemodRfPars> m_RfChanFreqs;

		public int m_FefType;

		public int m_FefLength;

		public int m_FefInterval;

		public List<DtDvbT2DemodL1PostPlp> m_Plps;

		public List<DtDvbT2DemodAuxPars> m_AuxPars;
	}

	public DtDvbT2DemodL1P1 m_P1;

	public DtDvbT2DemodL1Pre m_L1Pre;

	public DtDvbT2DemodL1Post m_L1Post;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2DemodL1Data* uT2L1Pars)
	{
		*(bool*)uT2L1Pars = m_P1.m_Valid;
		((int*)uT2L1Pars)[1] = m_P1.m_FftMode;
		((int*)uT2L1Pars)[2] = m_P1.m_Miso;
		((int*)uT2L1Pars)[3] = m_P1.m_Fef;
		((int*)uT2L1Pars)[4] = m_P1.m_T2Profile;
		((sbyte*)uT2L1Pars)[20] = (m_L1Pre.m_Valid ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2L1Pars)[6] = m_L1Pre.m_Type;
		((int*)uT2L1Pars)[7] = m_L1Pre.m_BwtExt;
		((int*)uT2L1Pars)[8] = m_L1Pre.m_S1;
		((int*)uT2L1Pars)[9] = m_L1Pre.m_S2;
		((int*)uT2L1Pars)[10] = m_L1Pre.m_L1Repetition;
		((int*)uT2L1Pars)[11] = m_L1Pre.m_GuardInterval;
		((int*)uT2L1Pars)[12] = m_L1Pre.m_Papr;
		((int*)uT2L1Pars)[13] = m_L1Pre.m_L1Modulation;
		((int*)uT2L1Pars)[14] = m_L1Pre.m_L1CodeRate;
		((int*)uT2L1Pars)[15] = m_L1Pre.m_L1FecType;
		((int*)uT2L1Pars)[16] = m_L1Pre.m_L1PostSize;
		((int*)uT2L1Pars)[17] = m_L1Pre.m_l1PostInfoSize;
		((int*)uT2L1Pars)[18] = m_L1Pre.m_PilotPattern;
		((int*)uT2L1Pars)[19] = m_L1Pre.m_TxIdAvailability;
		((int*)uT2L1Pars)[20] = m_L1Pre.m_CellId;
		((int*)uT2L1Pars)[21] = m_L1Pre.m_NetworkId;
		((int*)uT2L1Pars)[22] = m_L1Pre.m_T2SystemId;
		((int*)uT2L1Pars)[23] = m_L1Pre.m_NumT2Frames;
		((int*)uT2L1Pars)[24] = m_L1Pre.m_NumDataSyms;
		((int*)uT2L1Pars)[25] = m_L1Pre.m_RegenFlag;
		((int*)uT2L1Pars)[26] = m_L1Pre.m_L1PostExt;
		((int*)uT2L1Pars)[27] = m_L1Pre.m_NumRfChans;
		((int*)uT2L1Pars)[28] = m_L1Pre.m_CurrentRfIdx;
		((int*)uT2L1Pars)[29] = m_L1Pre.m_T2Version;
		((int*)uT2L1Pars)[30] = m_L1Pre.m_L1PostScrambling;
		((int*)uT2L1Pars)[31] = m_L1Pre.m_T2BaseLite;
		((sbyte*)uT2L1Pars)[128] = (m_L1Post.m_Valid ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2L1Pars)[33] = m_L1Post.m_NumSubslices;
		((int*)uT2L1Pars)[34] = m_L1Post.m_NumPlps;
		((int*)uT2L1Pars)[35] = m_L1Post.m_NumAux;
		vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E*)((byte*)uT2L1Pars + 144);
		((int*)ptr)[1] = *(int*)ptr;
		int num = 0;
		if (0 < m_L1Post.m_RfChanFreqs.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2DemodRfPars dtDvbT2DemodRfPars2);
			do
			{
				DtDvbT2DemodRfPars dtDvbT2DemodRfPars = m_L1Post.m_RfChanFreqs[num];
				*(int*)(&dtDvbT2DemodRfPars2) = dtDvbT2DemodRfPars.m_RfIdx;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbT2DemodRfPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2DemodRfPars2, 4)) = dtDvbT2DemodRfPars.m_Frequency;
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbT2DemodRfPars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E*)((byte*)uT2L1Pars + 144), &dtDvbT2DemodRfPars2);
				num++;
			}
			while (num < m_L1Post.m_RfChanFreqs.Count);
		}
		((int*)uT2L1Pars)[39] = m_L1Post.m_FefType;
		((int*)uT2L1Pars)[40] = m_L1Post.m_FefLength;
		((int*)uT2L1Pars)[41] = m_L1Post.m_FefInterval;
		Dtapi.DtDvbT2DemodL1Data* ptr2 = (Dtapi.DtDvbT2DemodL1Data*)((byte*)uT2L1Pars + 168);
		vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E*)ptr2;
		((int*)ptr3)[1] = *(int*)ptr3;
		int num2 = 0;
		if (0 < m_L1Post.m_Plps.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2DemodL1PostPlp dtDvbT2DemodL1PostPlp);
			do
			{
				global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1PostPlp_002E_007Bctor_007D(&dtDvbT2DemodL1PostPlp);
				m_L1Post.m_Plps[num2].ConvertToUnmgd(&dtDvbT2DemodL1PostPlp);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbT2DemodL1PostPlp_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E*)ptr2, &dtDvbT2DemodL1PostPlp);
				num2++;
			}
			while (num2 < m_L1Post.m_Plps.Count);
		}
		Dtapi.DtDvbT2DemodL1Data* ptr4 = (Dtapi.DtDvbT2DemodL1Data*)((byte*)uT2L1Pars + 180);
		vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E* ptr5 = (vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E*)ptr4;
		((int*)ptr5)[1] = *(int*)ptr5;
		int num3 = 0;
		if (0 < m_L1Post.m_AuxPars.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2DemodAuxPars dtDvbT2DemodAuxPars2);
			do
			{
				DtDvbT2DemodAuxPars dtDvbT2DemodAuxPars = m_L1Post.m_AuxPars[num3];
				*(int*)(&dtDvbT2DemodAuxPars2) = dtDvbT2DemodAuxPars.m_AuxStreamType;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbT2DemodAuxPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2DemodAuxPars2, 4)) = dtDvbT2DemodAuxPars.m_AuxPrivateConf;
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbT2DemodAuxPars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E*)ptr4, &dtDvbT2DemodAuxPars2);
				num3++;
			}
			while (num3 < m_L1Post.m_AuxPars.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2DemodL1Data* uT2L1Pars)
	{
		m_P1.m_Valid = *(bool*)uT2L1Pars;
		m_P1.m_FftMode = ((int*)uT2L1Pars)[1];
		m_P1.m_Miso = ((int*)uT2L1Pars)[2];
		m_P1.m_Fef = ((int*)uT2L1Pars)[3];
		m_P1.m_T2Profile = ((int*)uT2L1Pars)[4];
		m_L1Pre.m_Valid = ((bool*)uT2L1Pars)[20];
		m_L1Pre.m_Type = ((int*)uT2L1Pars)[6];
		m_L1Pre.m_BwtExt = ((int*)uT2L1Pars)[7];
		m_L1Pre.m_S1 = ((int*)uT2L1Pars)[8];
		m_L1Pre.m_S2 = ((int*)uT2L1Pars)[9];
		m_L1Pre.m_L1Repetition = ((int*)uT2L1Pars)[10];
		m_L1Pre.m_GuardInterval = ((int*)uT2L1Pars)[11];
		m_L1Pre.m_Papr = ((int*)uT2L1Pars)[12];
		m_L1Pre.m_L1Modulation = ((int*)uT2L1Pars)[13];
		m_L1Pre.m_L1CodeRate = ((int*)uT2L1Pars)[14];
		m_L1Pre.m_L1FecType = ((int*)uT2L1Pars)[15];
		m_L1Pre.m_L1PostSize = ((int*)uT2L1Pars)[16];
		m_L1Pre.m_l1PostInfoSize = ((int*)uT2L1Pars)[17];
		m_L1Pre.m_PilotPattern = ((int*)uT2L1Pars)[18];
		m_L1Pre.m_TxIdAvailability = ((int*)uT2L1Pars)[19];
		m_L1Pre.m_CellId = ((int*)uT2L1Pars)[20];
		m_L1Pre.m_NetworkId = ((int*)uT2L1Pars)[21];
		m_L1Pre.m_T2SystemId = ((int*)uT2L1Pars)[22];
		m_L1Pre.m_NumT2Frames = ((int*)uT2L1Pars)[23];
		m_L1Pre.m_NumDataSyms = ((int*)uT2L1Pars)[24];
		m_L1Pre.m_RegenFlag = ((int*)uT2L1Pars)[25];
		m_L1Pre.m_L1PostExt = ((int*)uT2L1Pars)[26];
		m_L1Pre.m_NumRfChans = ((int*)uT2L1Pars)[27];
		m_L1Pre.m_CurrentRfIdx = ((int*)uT2L1Pars)[28];
		m_L1Pre.m_T2Version = ((int*)uT2L1Pars)[29];
		m_L1Pre.m_L1PostScrambling = ((int*)uT2L1Pars)[30];
		m_L1Pre.m_T2BaseLite = ((int*)uT2L1Pars)[31];
		m_L1Post.m_Valid = ((bool*)uT2L1Pars)[128];
		m_L1Post.m_NumSubslices = ((int*)uT2L1Pars)[33];
		m_L1Post.m_NumPlps = ((int*)uT2L1Pars)[34];
		m_L1Post.m_NumAux = ((int*)uT2L1Pars)[35];
		m_L1Post.m_RfChanFreqs.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E*)((byte*)uT2L1Pars + 144);
		if (0u < (uint)(((int*)ptr)[1] - *(int*)ptr >> 3))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E*)((byte*)uT2L1Pars + 144);
			vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbT2DemodRfPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodRfPars_003E_0020_003E*)((byte*)ptr + 4);
			do
			{
				DtDvbT2DemodRfPars dtDvbT2DemodRfPars = new DtDvbT2DemodRfPars();
				Dtapi.DtDvbT2DemodRfPars* ptr3 = (Dtapi.DtDvbT2DemodRfPars*)(int)(num * 8 + (uint)((int*)uT2L1Pars)[36]);
				dtDvbT2DemodRfPars.m_RfIdx = *(int*)ptr3;
				dtDvbT2DemodRfPars.m_Frequency = ((int*)ptr3)[1];
				m_L1Post.m_RfChanFreqs.Add(dtDvbT2DemodRfPars);
				num++;
			}
			while (num < (uint)(*(int*)ptr2 - *(int*)ptr >> 3));
		}
		m_L1Post.m_FefType = ((int*)uT2L1Pars)[39];
		m_L1Post.m_FefLength = ((int*)uT2L1Pars)[40];
		m_L1Post.m_FefInterval = ((int*)uT2L1Pars)[41];
		m_L1Post.m_Plps.Clear();
		uint num2 = 0u;
		vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E* ptr4 = (vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E*)((byte*)uT2L1Pars + 168);
		if (0u < (uint)((((int*)ptr4)[1] - *(int*)ptr4) / 84))
		{
			ptr4 = (vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E*)((byte*)uT2L1Pars + 168);
			vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E* ptr5 = (vector_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodL1PostPlp_003E_0020_003E*)((byte*)ptr4 + 4);
			int num3 = 0;
			do
			{
				DtDvbT2DemodL1PostPlp dtDvbT2DemodL1PostPlp = new DtDvbT2DemodL1PostPlp();
				dtDvbT2DemodL1PostPlp.ConvertFromUnmgd((Dtapi.DtDvbT2DemodL1PostPlp*)(((int*)uT2L1Pars)[42] + num3));
				m_L1Post.m_Plps.Add(dtDvbT2DemodL1PostPlp);
				num2++;
				num3 += 84;
			}
			while (num2 < (uint)((*(int*)ptr5 - *(int*)ptr4) / 84));
		}
		m_L1Post.m_AuxPars.Clear();
		uint num4 = 0u;
		vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E* ptr6 = (vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E*)((byte*)uT2L1Pars + 180);
		if (0u < (uint)(((int*)ptr6)[1] - *(int*)ptr6 >> 3))
		{
			ptr6 = (vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E*)((byte*)uT2L1Pars + 180);
			vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E* ptr7 = (vector_003CDtapi_003A_003ADtDvbT2DemodAuxPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2DemodAuxPars_003E_0020_003E*)((byte*)ptr6 + 4);
			do
			{
				DtDvbT2DemodAuxPars dtDvbT2DemodAuxPars = new DtDvbT2DemodAuxPars();
				Dtapi.DtDvbT2DemodAuxPars* ptr8 = (Dtapi.DtDvbT2DemodAuxPars*)(int)(num4 * 8 + (uint)((int*)uT2L1Pars)[45]);
				dtDvbT2DemodAuxPars.m_AuxStreamType = *(int*)ptr8;
				dtDvbT2DemodAuxPars.m_AuxPrivateConf = ((int*)ptr8)[1];
				m_L1Post.m_AuxPars.Add(dtDvbT2DemodAuxPars);
				num4++;
			}
			while (num4 < (uint)(*(int*)ptr7 - *(int*)ptr6 >> 3));
		}
	}

	public unsafe DtDvbT2DemodL1Data()
	{
		m_L1Post.m_RfChanFreqs = new List<DtDvbT2DemodRfPars>();
		m_L1Post.m_Plps = new List<DtDvbT2DemodL1PostPlp>();
		m_L1Post.m_AuxPars = new List<DtDvbT2DemodAuxPars>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2DemodL1Data dtDvbT2DemodL1Data);
		global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1Data_002E_007Bctor_007D(&dtDvbT2DemodL1Data);
		try
		{
			ConvertFromUnmgd(&dtDvbT2DemodL1Data);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2DemodL1Data*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1Data_002E_007Bdtor_007D), &dtDvbT2DemodL1Data);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1Data_002E_007Bdtor_007D(&dtDvbT2DemodL1Data);
	}
}
