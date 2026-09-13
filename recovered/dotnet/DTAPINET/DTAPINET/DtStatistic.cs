using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtStatistic : IDisposable
{
	public enum StatValueType
	{
		STAT_VT_UNDEFINED = 0,
		STAT_VT_INT = 3,
		STAT_VT_DOUBLE = 2,
		STAT_VT_BOOL = 1,
		STAT_VT_DAB_ENSEM = 13,
		STAT_VT_DVBC2_L1P2 = 4,
		STAT_VT_DVBC2_PLPSIG = 5,
		STAT_VT_DVBT2_L1 = 6,
		STAT_VT_ISDBT_PARS = 7,
		STAT_VT_LDPC_STATS = 8,
		STAT_VT_MA_DATA = 9,
		STAT_VT_MA_STATS = 10,
		STAT_VT_PLP_BLOCKS = 11,
		STAT_VT_RS_STATS = 14,
		STAT_VT_VIT_STATS = 12,
		STAT_VT_DVBT_TPS = 15,
		STAT_VT_DAB_TXID = 16,
		STAT_VT_ATSC3_L1 = 17,
		STAT_VT_DVBT2_TXID = 18,
		STAT_VT_ATSC3_TXID = 19,
		STAT_VT_DVBS2_ISI = 20,
		STAT_VT_DVBS2_ISI_SIGDATA = 21
	}

	public enum IdXtraType
	{
		STAT_IDXTRA_NOT_USED = -1,
		STAT_IDXTRA_ISDBT_LAYER,
		STAT_IDXTRA_ISI,
		STAT_IDXTRA_TIME_WINDOW,
		STAT_IDXTRA_PLPID,
		STAT_IDXTRA_SUBCH
	}

	public enum TimeWindowType
	{
		STAT_TIME_NOT_USED = -1,
		STAT_TIME_SHORT,
		STAT_TIME_MEDIUM,
		STAT_TIME_LONG
	}

	public class IdXtra
	{
		internal DtStatistic Stat;

		public unsafe int this[int idx]
		{
			get
			{
				if ((uint)idx <= 3u)
				{
					return ((int*)(idx * 4 + (byte*)Stat.m_pDtStatistic))[4];
				}
				throw new ArgumentOutOfRangeException();
			}
			set
			{
				if ((uint)idx <= 3u)
				{
					((int*)(idx * 4 + (byte*)Stat.m_pDtStatistic))[4] = value;
					return;
				}
				throw new ArgumentOutOfRangeException();
			}
		}

		internal IdXtra(DtStatistic stat)
		{
			Stat = stat;
		}
	}

	internal unsafe Dtapi.DtStatistic* m_pDtStatistic;

	public IdXtra m_IdXtra;

	public unsafe bool m_ValueBool
	{
		[return: MarshalAs(UnmanagedType.U1)]
		get
		{
			return ((bool*)m_pDtStatistic)[40];
		}
		[param: MarshalAs(UnmanagedType.U1)]
		set
		{
			((sbyte*)m_pDtStatistic)[40] = (value ? ((sbyte)1) : ((sbyte)0));
		}
	}

	public unsafe int m_ValueInt
	{
		get
		{
			return ((int*)m_pDtStatistic)[10];
		}
		set
		{
			((int*)m_pDtStatistic)[10] = value;
		}
	}

	public unsafe double m_ValueDouble
	{
		get
		{
			return ((double*)m_pDtStatistic)[5];
		}
		set
		{
			((double*)m_pDtStatistic)[5] = value;
		}
	}

	public unsafe StatValueType m_ValueType => ((StatValueType*)m_pDtStatistic)[8];

	public unsafe DTAPI_RESULT m_Result => ((DTAPI_RESULT*)m_pDtStatistic)[2];

	public unsafe DTAPI_RESULT GetName(ref string rName, ref string rShortName)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out char* value);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out char* value2);
		uint num = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetName(m_pDtStatistic, &value, &value2);
		if (num == 0)
		{
			rName = new string(value);
			rShortName = new string(value2);
		}
		else
		{
			rName = null;
			rShortName = null;
		}
		return (DTAPI_RESULT)num;
	}

	public unsafe DTAPI_RESULT GetValue(ref DtDvbTTpsInfo rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbTTpsInfo* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref DtVitDecStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtVitDecStats* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.m_BitCount = *(long*)ptr;
			rValue.m_BitErrorCount = ((long*)ptr)[1];
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref DtRsDecStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtRsDecStats* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtDabTransmitterIdInfo rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabTransmitterIdInfo* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtDabEnsembleInfo rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabEnsembleInfo* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtIsdbtParamsData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtParamsData* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref DtDemodPlpBlocks rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPlpBlocks* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref DtDemodMaLayerStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodMaLayerStats* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref DtDemodMaLayerData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodMaLayerData* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref DtDemodLdpcStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodLdpcStats* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtDvbT2DemodL1Data rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2DemodL1Data* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtDvbS2Isi rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2Isi* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtDvbS2IsiSigData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2IsiSigData* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtDvbC2DemodL1PlpSigData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1PlpSigData* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtDvbC2DemodL1Part2Data rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1Part2Data* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtAtsc3TxIdInfo rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3TxIdInfo* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(DtAtsc3DemodL1Data rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3DemodL1Data* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &ptr);
		if (ptr != null)
		{
			rValue.ConvertFromUnmgd(ptr);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref bool Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &flag);
		Value = flag;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref double Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out double num);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &num);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetValue(ref int Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtStatistic_002EGetValue(m_pDtStatistic, &num);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT SetId(int StatisticId)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetId(m_pDtStatistic, StatisticId);
	}

	public unsafe DTAPI_RESULT SetValue(ref DtDvbTTpsInfo rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbTTpsInfo dtDvbTTpsInfo);
		rValue.ConvertToUnmgd(&dtDvbTTpsInfo);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDvbTTpsInfo);
	}

	public unsafe DTAPI_RESULT SetValue(ref DtVitDecStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtVitDecStats dtVitDecStats);
		*(long*)(&dtVitDecStats) = rValue.m_BitCount;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtVitDecStats, long>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtVitDecStats, 8)) = rValue.m_BitErrorCount;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtVitDecStats);
	}

	public unsafe DTAPI_RESULT SetValue(ref DtRsDecStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtRsDecStats dtRsDecStats);
		rValue.ConvertToUnmgd(&dtRsDecStats);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtRsDecStats);
	}

	public unsafe DTAPI_RESULT SetValue(DtDabTransmitterIdInfo rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabTransmitterIdInfo dtDabTransmitterIdInfo);
		*(int*)(&dtDabTransmitterIdInfo) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDabTransmitterIdInfo, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabTransmitterIdInfo, 4)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDabTransmitterIdInfo, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDabTransmitterIdInfo, 8)) = 0;
		DTAPI_RESULT result;
		try
		{
			rValue.ConvertToUnmgd(&dtDabTransmitterIdInfo);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDabTransmitterIdInfo);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDabTransmitterIdInfo*, void>)(&global::_003CModule_003E.Dtapi_002EDtDabTransmitterIdInfo_002E_007Bdtor_007D), &dtDabTransmitterIdInfo);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDabTransmitterId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabTransmitterId_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDabTransmitterId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabTransmitterId_003E_0020_003E*)(&dtDabTransmitterIdInfo));
		return result;
	}

	public unsafe DTAPI_RESULT SetValue(DtDabEnsembleInfo rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabEnsembleInfo dtDabEnsembleInfo);
		global::_003CModule_003E.Dtapi_002EDtDabEnsembleInfo_002E_007Bctor_007D(&dtDabEnsembleInfo);
		DTAPI_RESULT result;
		try
		{
			rValue.ConvertToUnmgd(&dtDabEnsembleInfo);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDabEnsembleInfo);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDabEnsembleInfo*, void>)(&global::_003CModule_003E.Dtapi_002EDtDabEnsembleInfo_002E_007Bdtor_007D), &dtDabEnsembleInfo);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDabEnsembleInfo_002E_007Bdtor_007D(&dtDabEnsembleInfo);
		return result;
	}

	public unsafe DTAPI_RESULT SetValue(DtIsdbtParamsData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtParamsData dtIsdbtParamsData);
		global::_003CModule_003E.Dtapi_002EDtIsdbtParamsData_002E_007Bctor_007D(&dtIsdbtParamsData);
		rValue.ConvertToUnmgd(&dtIsdbtParamsData);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtIsdbtParamsData);
	}

	public unsafe DTAPI_RESULT SetValue(ref DtDemodPlpBlocks rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPlpBlocks dtDemodPlpBlocks);
		rValue.ConvertToUnmgd(&dtDemodPlpBlocks);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDemodPlpBlocks);
	}

	public unsafe DTAPI_RESULT SetValue(ref DtDemodMaLayerStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodMaLayerStats dtDemodMaLayerStats);
		rValue.ConvertToUnmgd(&dtDemodMaLayerStats);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDemodMaLayerStats);
	}

	public unsafe DTAPI_RESULT SetValue(ref DtDemodMaLayerData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodMaLayerData dtDemodMaLayerData);
		rValue.ConvertToUnmgd(&dtDemodMaLayerData);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDemodMaLayerData);
	}

	public unsafe DTAPI_RESULT SetValue(ref DtDemodLdpcStats rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodLdpcStats dtDemodLdpcStats);
		rValue.ConvertToUnmgd(&dtDemodLdpcStats);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDemodLdpcStats);
	}

	public unsafe DTAPI_RESULT SetValue(DtDvbT2DemodL1Data rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2DemodL1Data dtDvbT2DemodL1Data);
		global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1Data_002E_007Bctor_007D(&dtDvbT2DemodL1Data);
		DTAPI_RESULT result;
		try
		{
			rValue.ConvertToUnmgd(&dtDvbT2DemodL1Data);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDvbT2DemodL1Data);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2DemodL1Data*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1Data_002E_007Bdtor_007D), &dtDvbT2DemodL1Data);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2DemodL1Data_002E_007Bdtor_007D(&dtDvbT2DemodL1Data);
		return result;
	}

	public unsafe DTAPI_RESULT SetValue(DtDvbS2Isi rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2Isi dtDvbS2Isi);
		global::_003CModule_003E.Dtapi_002EDtDvbS2Isi_002E_007Bctor_007D(&dtDvbS2Isi);
		DTAPI_RESULT result;
		try
		{
			rValue.ConvertToUnmgd(&dtDvbS2Isi);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDvbS2Isi);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbS2Isi*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbS2Isi_002E_007Bdtor_007D), &dtDvbS2Isi);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)(&dtDvbS2Isi));
		return result;
	}

	public unsafe DTAPI_RESULT SetValue(DtDvbS2IsiSigData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2IsiSigData dtDvbS2IsiSigData);
		global::_003CModule_003E.Dtapi_002EDtDvbS2IsiSigData_002E_007Bctor_007D(&dtDvbS2IsiSigData);
		DTAPI_RESULT result;
		try
		{
			rValue.ConvertToUnmgd(&dtDvbS2IsiSigData);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDvbS2IsiSigData);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbS2IsiSigData*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbS2IsiSigData_002E_007Bdtor_007D), &dtDvbS2IsiSigData);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbS2IsiSigData, 8)));
		return result;
	}

	public unsafe DTAPI_RESULT SetValue(DtDvbC2DemodL1PlpSigData rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1PlpSigData dtDvbC2DemodL1PlpSigData);
		global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1PlpSigData_002E_007Bctor_007D(&dtDvbC2DemodL1PlpSigData);
		DTAPI_RESULT result;
		try
		{
			rValue.ConvertToUnmgd(&dtDvbC2DemodL1PlpSigData);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDvbC2DemodL1PlpSigData);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2DemodL1PlpSigData*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1PlpSigData_002E_007Bdtor_007D), &dtDvbC2DemodL1PlpSigData);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1PlpSigDataPlp_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1PlpSigData, 4)));
		return result;
	}

	public unsafe DTAPI_RESULT SetValue(DtDvbC2DemodL1Part2Data rValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1Part2Data dtDvbC2DemodL1Part2Data);
		global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2Data_002E_007Bctor_007D(&dtDvbC2DemodL1Part2Data);
		DTAPI_RESULT result;
		try
		{
			rValue.ConvertToUnmgd(&dtDvbC2DemodL1Part2Data);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, &dtDvbC2DemodL1Part2Data);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2DemodL1Part2Data*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2Data_002E_007Bdtor_007D), &dtDvbC2DemodL1Part2Data);
			throw;
		}
		try
		{
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2NotchPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2NotchPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2Data, 60)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2Data, 44)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2DSlice_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2Data, 44)));
		return result;
	}

	public unsafe DTAPI_RESULT SetValue([MarshalAs(UnmanagedType.U1)] bool Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, Value);
	}

	public unsafe DTAPI_RESULT SetValue(double Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, Value);
	}

	public unsafe DTAPI_RESULT SetValue(int Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtStatistic_002ESetValue(m_pDtStatistic, Value);
	}

	public unsafe DtStatistic(int StatisticId)
	{
		Dtapi.DtStatistic* ptr = (Dtapi.DtStatistic*)global::_003CModule_003E.@new(48u);
		Dtapi.DtStatistic* pDtStatistic;
		try
		{
			pDtStatistic = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bctor_007D(ptr, StatisticId));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 48u);
			throw;
		}
		m_pDtStatistic = pDtStatistic;
		m_IdXtra = new IdXtra(this);
	}

	public unsafe DtStatistic()
	{
		Dtapi.DtStatistic* ptr = (Dtapi.DtStatistic*)global::_003CModule_003E.@new(48u);
		Dtapi.DtStatistic* pDtStatistic;
		try
		{
			pDtStatistic = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 48u);
			throw;
		}
		m_pDtStatistic = pDtStatistic;
		m_IdXtra = new IdXtra(this);
	}

	private void _007EDtStatistic()
	{
		_0021DtStatistic();
	}

	private unsafe void _0021DtStatistic()
	{
		Dtapi.DtStatistic* pDtStatistic = m_pDtStatistic;
		if (pDtStatistic != null)
		{
			Dtapi.DtStatistic* ptr = pDtStatistic;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr)))((nint)ptr, 1u);
			m_pDtStatistic = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtStatistic();
			return;
		}
		try
		{
			_0021DtStatistic();
		}
		finally
		{
			base.Finalize();
		}
	}

	public virtual sealed void Dispose()
	{
		Dispose(A_0: true);
		GC.SuppressFinalize(this);
	}

	~DtStatistic()
	{
		Dispose(A_0: false);
	}
}
