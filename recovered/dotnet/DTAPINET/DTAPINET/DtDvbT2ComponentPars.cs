using System;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbT2ComponentPars
{
	public int m_T2Version;

	public int m_T2Profile;

	public bool m_T2BaseLite;

	public int m_Bandwidth;

	public int m_FftMode;

	public int m_Miso;

	public int m_GuardInterval;

	public int m_Papr;

	public bool m_BwtExt;

	public int m_PilotPattern;

	public int m_L1Modulation;

	public int m_CellId;

	public int m_NetworkId;

	public int m_T2SystemId;

	public bool m_L1Repetition;

	public int m_NumT2Frames;

	public int m_NumDataSyms;

	public int m_NumSubslices;

	public int m_ComponentStartTime;

	public bool m_FefEnable;

	public int m_FefType;

	public int m_FefS1;

	public int m_FefS2;

	public int m_FefSignal;

	public int m_FefLength;

	public int m_FefInterval;

	public int m_NumRfChans;

	public int m_StartRfIdx;

	public int[] m_RfChanFreqs;

	public int m_NumPlps;

	public DtDvbT2PlpPars[] m_Plps;

	public DtPlpInpPars[] m_PlpInputs;

	public DtDvbT2AuxPars m_Aux;

	public DtDvbT2PaprPars m_PaprPars;

	public DtDvbT2TxSigPars m_TxSignature;

	public DtDvbT2RbmValidation m_RbmValidation;

	public DtTestPointOutPars m_TpOutput;

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ComponentPars dtDvbT2ComponentPars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2ComponentPars_002E_007Bctor_007D(&dtDvbT2ComponentPars);
		try
		{
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, void>)(int)(*(uint*)(*(int*)(&dtDvbT2ComponentPars) + 4)))((nint)(&dtDvbT2ComponentPars));
			ConvertFromUnmgd(&dtDvbT2ComponentPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2ComponentPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2ComponentPars_002E_007Bdtor_007D), &dtDvbT2ComponentPars);
			throw;
		}
		try
		{
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtPlpInpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtPlpInpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtPlpInpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtPlpInpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2ComponentPars, 164)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtDvbT2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2PlpPars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbT2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2PlpPars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2ComponentPars, 152)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbT2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbT2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2ComponentPars, 152)));
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2ComponentPars* uT2Pars)
	{
		((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, void>)(int)(*(uint*)(*(int*)uT2Pars + 4)))((nint)uT2Pars);
		((int*)uT2Pars)[2] = m_T2Version;
		((int*)uT2Pars)[3] = m_T2Profile;
		((sbyte*)uT2Pars)[16] = (m_T2BaseLite ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2Pars)[5] = m_Bandwidth;
		((int*)uT2Pars)[6] = m_FftMode;
		((int*)uT2Pars)[7] = m_Miso;
		((int*)uT2Pars)[8] = m_GuardInterval;
		((int*)uT2Pars)[9] = m_Papr;
		((sbyte*)uT2Pars)[40] = (m_BwtExt ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2Pars)[11] = m_PilotPattern;
		((int*)uT2Pars)[12] = m_L1Modulation;
		((int*)uT2Pars)[13] = m_CellId;
		((int*)uT2Pars)[14] = m_NetworkId;
		((int*)uT2Pars)[15] = m_T2SystemId;
		((sbyte*)uT2Pars)[64] = (m_L1Repetition ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2Pars)[17] = m_NumT2Frames;
		((int*)uT2Pars)[18] = m_NumDataSyms;
		((int*)uT2Pars)[19] = m_NumSubslices;
		((int*)uT2Pars)[20] = m_ComponentStartTime;
		((sbyte*)uT2Pars)[84] = (m_FefEnable ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2Pars)[22] = m_FefType;
		((int*)uT2Pars)[23] = m_FefS1;
		((int*)uT2Pars)[24] = m_FefS2;
		((int*)uT2Pars)[25] = m_FefSignal;
		((int*)uT2Pars)[26] = m_FefLength;
		((int*)uT2Pars)[27] = m_FefInterval;
		((int*)uT2Pars)[28] = m_NumRfChans;
		((int*)uT2Pars)[36] = m_StartRfIdx;
		int num = 0;
		Dtapi.DtDvbT2ComponentPars* ptr = (Dtapi.DtDvbT2ComponentPars*)((byte*)uT2Pars + 116);
		do
		{
			*(int*)ptr = m_RfChanFreqs[num];
			num++;
			ptr = (Dtapi.DtDvbT2ComponentPars*)((byte*)ptr + 4);
		}
		while (num < 7);
		((int*)uT2Pars)[37] = m_NumPlps;
		int num2 = 0;
		Dtapi.DtDvbT2ComponentPars* ptr2 = (Dtapi.DtDvbT2ComponentPars*)((byte*)uT2Pars + 152);
		Dtapi.DtDvbT2ComponentPars* ptr3 = (Dtapi.DtDvbT2ComponentPars*)((byte*)uT2Pars + 164);
		int num3 = 0;
		int num4 = 0;
		do
		{
			m_Plps[num2].ConvertToUnmgd((Dtapi.DtDvbT2PlpPars*)(num4 + *(int*)ptr2));
			m_PlpInputs[num2].ConvertToUnmgd((Dtapi.DtPlpInpPars*)(num3 + *(int*)ptr3));
			num2++;
			num4 += 1116;
			num3 += 216;
		}
		while (num4 < 284580);
		m_Aux.ConvertToUnmgd((Dtapi.DtDvbT2AuxPars*)((byte*)uT2Pars + 176));
		m_PaprPars.ConvertToUnmgd((Dtapi.DtDvbT2PaprPars*)((byte*)uT2Pars + 184));
		m_RbmValidation.ConvertToUnmgd((Dtapi.DtDvbT2RbmValidation*)((byte*)uT2Pars + 312));
		m_TpOutput.ConvertToUnmgd((Dtapi.DtTestPointOutPars*)((byte*)uT2Pars + 328));
		m_TxSignature.ConvertToUnmgd((Dtapi.DtDvbT2TxSigPars*)((byte*)uT2Pars + 280));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2ComponentPars* uT2Pars)
	{
		m_T2Version = ((int*)uT2Pars)[2];
		m_T2Profile = ((int*)uT2Pars)[3];
		m_T2BaseLite = ((bool*)uT2Pars)[16];
		m_Bandwidth = ((int*)uT2Pars)[5];
		m_FftMode = ((int*)uT2Pars)[6];
		m_Miso = ((int*)uT2Pars)[7];
		m_GuardInterval = ((int*)uT2Pars)[8];
		m_Papr = ((int*)uT2Pars)[9];
		m_BwtExt = ((bool*)uT2Pars)[40];
		m_PilotPattern = ((int*)uT2Pars)[11];
		m_L1Modulation = ((int*)uT2Pars)[12];
		m_CellId = ((int*)uT2Pars)[13];
		m_NetworkId = ((int*)uT2Pars)[14];
		m_T2SystemId = ((int*)uT2Pars)[15];
		m_L1Repetition = ((bool*)uT2Pars)[64];
		m_NumT2Frames = ((int*)uT2Pars)[17];
		m_NumDataSyms = ((int*)uT2Pars)[18];
		m_NumSubslices = ((int*)uT2Pars)[19];
		m_ComponentStartTime = ((int*)uT2Pars)[20];
		m_FefEnable = ((bool*)uT2Pars)[84];
		m_FefType = ((int*)uT2Pars)[22];
		m_FefS1 = ((int*)uT2Pars)[23];
		m_FefS2 = ((int*)uT2Pars)[24];
		m_FefSignal = ((int*)uT2Pars)[25];
		m_FefLength = ((int*)uT2Pars)[26];
		m_FefInterval = ((int*)uT2Pars)[27];
		m_NumRfChans = ((int*)uT2Pars)[28];
		m_StartRfIdx = ((int*)uT2Pars)[36];
		int num = 0;
		Dtapi.DtDvbT2ComponentPars* ptr = (Dtapi.DtDvbT2ComponentPars*)((byte*)uT2Pars + 116);
		do
		{
			ref int reference = ref m_RfChanFreqs[num];
			reference = *(int*)ptr;
			num++;
			ptr = (Dtapi.DtDvbT2ComponentPars*)((byte*)ptr + 4);
		}
		while (num < 7);
		m_NumPlps = ((int*)uT2Pars)[37];
		int num2 = 0;
		Dtapi.DtDvbT2ComponentPars* ptr2 = (Dtapi.DtDvbT2ComponentPars*)((byte*)uT2Pars + 152);
		Dtapi.DtDvbT2ComponentPars* ptr3 = (Dtapi.DtDvbT2ComponentPars*)((byte*)uT2Pars + 164);
		int num3 = 0;
		int num4 = 0;
		do
		{
			m_Plps[num2].ConvertFromUnmgd((Dtapi.DtDvbT2PlpPars*)(num4 + *(int*)ptr2));
			m_PlpInputs[num2].ConvertFromUnmgd((Dtapi.DtPlpInpPars*)(num3 + *(int*)ptr3));
			num2++;
			num4 += 1116;
			num3 += 216;
		}
		while (num4 < 284580);
		m_Aux.ConvertFromUnmgd((Dtapi.DtDvbT2AuxPars*)((byte*)uT2Pars + 176));
		m_PaprPars.ConvertFromUnmgd((Dtapi.DtDvbT2PaprPars*)((byte*)uT2Pars + 184));
		m_RbmValidation.ConvertFromUnmgd((Dtapi.DtDvbT2RbmValidation*)((byte*)uT2Pars + 312));
		m_TpOutput.m_Enabled = ((bool*)uT2Pars)[328];
		m_TxSignature.ConvertFromUnmgd((Dtapi.DtDvbT2TxSigPars*)((byte*)uT2Pars + 280));
	}

	internal DtDvbT2ComponentPars()
	{
		m_Plps = new DtDvbT2PlpPars[255];
		m_PlpInputs = new DtPlpInpPars[255];
		int num = 0;
		do
		{
			m_Plps[num] = new DtDvbT2PlpPars();
			m_PlpInputs[num] = new DtPlpInpPars();
			num++;
		}
		while (num < 255);
		m_RfChanFreqs = new int[7];
		m_Aux = new DtDvbT2AuxPars();
		m_PaprPars = new DtDvbT2PaprPars();
		m_RbmValidation = new DtDvbT2RbmValidation();
		m_TpOutput = new DtTestPointOutPars();
		m_TxSignature = new DtDvbT2TxSigPars();
	}
}
