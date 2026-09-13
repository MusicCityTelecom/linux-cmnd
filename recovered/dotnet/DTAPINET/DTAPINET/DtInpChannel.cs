using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtInpChannel : IDisposable
{
	public delegate void DtBsProgressFunc(DtBsProgress Progress);

	public delegate void DtSpsProgressFunc(DtSpsProgress Progress);

	internal DtBsProgressFunc m_BsProgressFunc;

	internal DtSpsProgressFunc m_SpsProgressFunc;

	internal unsafe Dtapi.DtInpChannel* m_pDtInpChannel;

	internal unsafe DtDemodEventHelper* m_pDemodEventHelper;

	internal IDtDemodEvent m_EventInterface;

	internal unsafe BsProgressHelper* m_pBsProgressHelper;

	internal unsafe SpsProgressHelper* m_pSpsProgressHelper;

	protected internal unsafe void* m_pDtInpChannelDerived;

	public unsafe DtHwFuncDesc HwFuncDesc()
	{
		DtHwFuncDesc dtHwFuncDesc = new DtHwFuncDesc();
		dtHwFuncDesc.ConvertFromUnmgd((Dtapi.DtHwFuncDesc*)((byte*)m_pDtInpChannel + 8));
		return dtHwFuncDesc;
	}

	public unsafe int Category()
	{
		return ((int*)m_pDtInpChannel)[2];
	}

	public unsafe int FirmwareVersion()
	{
		return ((int*)m_pDtInpChannel)[17];
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool IsAttached()
	{
		return global::_003CModule_003E.Dtapi_002EDtInpChannel_002EIsAttached(m_pDtInpChannel);
	}

	public unsafe int TypeNumber(DtCaps Caps)
	{
		Dtapi.DtCaps pCaps = *Caps.m_pCaps;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps);
		return global::_003CModule_003E.Dtapi_002EDtCaps_002E_003D_003D(global::_003CModule_003E.Dtapi_002EDtCaps_002E_0026((Dtapi.DtCaps*)((byte*)m_pDtInpChannel + 208), &dtCaps, &pCaps), &pCaps) ? 1 : 0;
	}

	public unsafe int TypeNumber()
	{
		return ((int*)m_pDtInpChannel)[9];
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port, [MarshalAs(UnmanagedType.U1)] bool Exclusive, [MarshalAs(UnmanagedType.U1)] bool ProbeOnly)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EAttachToPort(m_pDtInpChannel, rDtDvc.pDtDvc, Port, Exclusive, ProbeOnly);
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port, [MarshalAs(UnmanagedType.U1)] bool Exclusive)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EAttachToPort(m_pDtInpChannel, rDtDvc.pDtDvc, Port, Exclusive, false);
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EAttachToPort(m_pDtInpChannel, rDtDvc.pDtDvc, Port, true, false);
	}

	public unsafe DTAPI_RESULT BlindScan(int NumEntries, int* NumEntriesResult, DtTransmitter[] rScanResults, long FreqHzSteps, long StartFreqHz, long EndFreqHz)
	{
		Dtapi.DtTransmitter* ptr = (Dtapi.DtTransmitter*)global::_003CModule_003E.new_005B_005D(((uint)NumEntries > 268435455u) ? uint.MaxValue : ((uint)(NumEntries * 16)));
		uint num = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EBlindScan(m_pDtInpChannel, NumEntries, NumEntriesResult, ptr, FreqHzSteps, StartFreqHz, EndFreqHz);
		if (num == 0)
		{
			int num2 = 0;
			if (0 < *NumEntriesResult)
			{
				Dtapi.DtTransmitter* ptr2 = (Dtapi.DtTransmitter*)((byte*)ptr + 12);
				do
				{
					rScanResults[num2].m_FreqHz = *(long*)((byte*)ptr2 - 12);
					rScanResults[num2].m_ModType = *((int*)ptr2 - 1);
					rScanResults[num2].m_SymbolRate = *(int*)ptr2;
					num2++;
					ptr2 = (Dtapi.DtTransmitter*)((byte*)ptr2 + 16);
				}
				while (num2 < *NumEntriesResult);
			}
		}
		global::_003CModule_003E.delete_005B_005D(ptr);
		return (DTAPI_RESULT)num;
	}

	public unsafe DTAPI_RESULT BlindScan(DtBsProgressFunc ProgessFunc, DtDemodPars DemodPars, long FreqHzSteps, long StartFreqHz, long EndFreqHz)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPars dtDemodPars);
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bctor_007D(&dtDemodPars);
		DTAPI_RESULT result;
		try
		{
			DemodPars.ConvertToUnmgd(&dtDemodPars);
			m_BsProgressFunc = ProgessFunc;
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EBlindScan(m_pDtInpChannel, (delegate* unmanaged[Cdecl, Cdecl]<Dtapi.DtBsProgress*, void*, void>)global::_003CModule_003E.__unep_0040_003FOnBsProgressFunc_0040BsProgressHelper_0040DTAPINET_0040_0040_0024_0024FSAXAAUDtBsProgress_0040Dtapi_0040_0040PAX_0040Z, m_pBsProgressHelper, &dtDemodPars, FreqHzSteps, StartFreqHz, EndFreqHz);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDemodPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D), &dtDemodPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D(&dtDemodPars);
		return result;
	}

	public unsafe DTAPI_RESULT CancelBlindScan()
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ECancelBlindScan(m_pDtInpChannel);
	}

	public unsafe DTAPI_RESULT CancelSpectrumScan()
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ECancelSpectrumScan(m_pDtInpChannel);
	}

	public unsafe DTAPI_RESULT ClearFifo()
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EClearFifo(m_pDtInpChannel);
	}

	public unsafe DTAPI_RESULT ClearFlags(int Latched)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EClearFlags(m_pDtInpChannel, Latched);
	}

	public unsafe DTAPI_RESULT Detach(int DetachMode)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EDetach(m_pDtInpChannel, DetachMode);
	}

	public unsafe DTAPI_RESULT Equalise(int EqualiserSetting)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EEqualise(m_pDtInpChannel, EqualiserSetting);
	}

	public unsafe DTAPI_RESULT GetDescriptor(ref DtHwFuncDesc rHwFuncDesc)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtHwFuncDesc dtHwFuncDesc);
		global::_003CModule_003E.Dtapi_002EDtHwFuncDesc_002E_007Bctor_007D(&dtHwFuncDesc);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetDescriptor(m_pDtInpChannel, &dtHwFuncDesc);
		(rHwFuncDesc = new DtHwFuncDesc()).ConvertFromUnmgd(&dtHwFuncDesc);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFifoLoad(ref int FifoLoad)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetFifoLoad(m_pDtInpChannel, &num);
		FifoLoad = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFlags(ref int Flags, ref int Latched)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetFlags(m_pDtInpChannel, &num, &num2);
		Flags = num;
		Latched = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue, ref long ParXtra0, ref long ParXtra1)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetIoConfig(m_pDtInpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		ParXtra1 = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue, ref long ParXtra0)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetIoConfig(m_pDtInpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetIoConfig(m_pDtInpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetIoConfig(m_pDtInpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIpPars(ref DtIpPars rIpPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIpPars dtIpPars);
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bctor_007D(&dtIpPars);
		uint result;
		try
		{
			result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetIpPars(m_pDtInpChannel, &dtIpPars);
			(rIpPars = new DtIpPars()).ConvertFromUnmgd(&dtIpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D), &dtIpPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D(&dtIpPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIpStat(ref DtIpStat IpStat)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIpStat dtIpStat);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetIpStat(m_pDtInpChannel, &dtIpStat);
		IpStat.ConvertFromUnmgd(&dtIpStat);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetMaxFifoSize(ref int MaxFifoSize)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetMaxFifoSize(m_pDtInpChannel, &num);
		MaxFifoSize = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetPars(int Count, DtPar[] rPars)
	{
		uint num2;
		if ((uint)Count <= 89478485u)
		{
			uint num = (uint)(Count * 48);
			if (num <= 4294967291u)
			{
				num2 = num + 4;
				goto IL_0022;
			}
		}
		num2 = uint.MaxValue;
		goto IL_0022;
		IL_0022:
		Dtapi.DtPar* ptr = (Dtapi.DtPar*)global::_003CModule_003E.new_005B_005D(num2);
		Dtapi.DtPar* ptr3;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = Count;
				Dtapi.DtPar* ptr2 = (Dtapi.DtPar*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, (uint)Count, (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, Dtapi.DtPar*>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, void>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num3 = (((uint)Count > 89478485u) ? uint.MaxValue : ((uint)(Count * 48)));
			uint num4 = ((num3 > 4294967291u) ? uint.MaxValue : (num3 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num4);
			throw;
		}
		int num5 = 0;
		if (0 < Count)
		{
			Dtapi.DtPar* ptr4 = ptr3;
			do
			{
				global::_003CModule_003E.Dtapi_002EDtPar_002E_003D(ptr4, rPars[num5].m_pDtPar);
				num5++;
				ptr4 = (Dtapi.DtPar*)((byte*)ptr4 + 48);
			}
			while (num5 < Count);
		}
		uint num6 = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetPars(m_pDtInpChannel, Count, ptr3);
		if (num6 == 0)
		{
			int num7 = 0;
			if (0 < Count)
			{
				Dtapi.DtPar* ptr5 = ptr3;
				do
				{
					global::_003CModule_003E.Dtapi_002EDtPar_002E_003D(rPars[num7].m_pDtPar, ptr5);
					num7++;
					ptr5 = (Dtapi.DtPar*)((byte*)ptr5 + 48);
				}
				while (num7 < Count);
			}
		}
		if (ptr3 != null)
		{
			Dtapi.DtPar* ptr6 = (Dtapi.DtPar*)((byte*)ptr3 - 4);
			if (*(int*)ptr6 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr6);
			}
		}
		return (DTAPI_RESULT)num6;
	}

	public unsafe DTAPI_RESULT GetRxClkFreq(ref int RxClkFreq)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetRxClkFreq(m_pDtInpChannel, &num);
		RxClkFreq = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRxControl(ref int RxControl)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetRxControl(m_pDtInpChannel, &num);
		RxControl = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRxMode(ref int RxMode)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetRxMode(m_pDtInpChannel, &num);
		RxMode = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatus(ref int PacketSize, ref int NumInv, ref int ClkDet, ref int AsiLock, ref int RateOk, ref int AsiInv)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num5);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num6);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStatus(m_pDtInpChannel, &num, &num2, &num3, &num4, &num5, &num6);
		PacketSize = num;
		NumInv = num2;
		ClkDet = num3;
		AsiLock = num4;
		RateOk = num5;
		AsiInv = num6;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtT2MiStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtT2MiStreamSelPars dtT2MiStreamSelPars);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtT2MiStreamSelPars);
		StreamSel.m_T2MiOutPid = *(int*)(&dtT2MiStreamSelPars);
		StreamSel.m_T2MiTsRate = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtT2MiStreamSelPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtT2MiStreamSelPars, 4));
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtDvbT2StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2StreamSelPars dtDvbT2StreamSelPars);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtDvbT2StreamSelPars);
		StreamSel.m_PlpId = *(int*)(&dtDvbT2StreamSelPars);
		StreamSel.m_CommonPlpId = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbT2StreamSelPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2StreamSelPars, 4));
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtDvbTStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbTStreamSelPars dtDvbTStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtDvbTStreamSelPars);
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtDvbS2StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2StreamSelPars dtDvbS2StreamSelPars);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtDvbS2StreamSelPars);
		StreamSel.m_Isi = *(int*)(&dtDvbS2StreamSelPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtDvbC2StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2StreamSelPars dtDvbC2StreamSelPars);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtDvbC2StreamSelPars);
		StreamSel.ConvertFromUnmgd(&dtDvbC2StreamSelPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtDabStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabStreamSelPars dtDabStreamSelPars);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtDabStreamSelPars);
		StreamSel.ConvertFromUnmgd(&dtDabStreamSelPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtDabEtiStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabEtiStreamSelPars dtDabEtiStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtDabEtiStreamSelPars);
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtIsdbtStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtStreamSelPars dtIsdbtStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtIsdbtStreamSelPars);
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref DtAtsc3StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3StreamSelPars dtAtsc3StreamSelPars);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStreamSelection(m_pDtInpChannel, &dtAtsc3StreamSelPars);
		StreamSel.m_PlpId = *(int*)(&dtAtsc3StreamSelPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTargetId(ref int Present, ref int TargetId)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetTargetId(m_pDtInpChannel, &num, &num2);
		Present = num;
		TargetId = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTsRateBps(ref int TsRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetTsRateBps(m_pDtInpChannel, &num);
		TsRate = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetViolCount(ref int ViolCount)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetViolCount(m_pDtInpChannel, &num);
		ViolCount = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT LedControl(int LedControl)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ELedControl(m_pDtInpChannel, LedControl);
	}

	public unsafe DTAPI_RESULT PolarityControl(int Polarity)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EPolarityControl(m_pDtInpChannel, Polarity);
	}

	public unsafe DTAPI_RESULT Read(byte[] rBuffer, int NumBytesToRead, int Timeout)
	{
		fixed (byte* ptr = &rBuffer[0])
		{
			return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ERead(m_pDtInpChannel, (sbyte*)ptr, NumBytesToRead, Timeout);
		}
	}

	public unsafe DTAPI_RESULT Read(byte[] rBuffer, int NumBytesToRead)
	{
		fixed (byte* ptr = &rBuffer[0])
		{
			return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ERead(m_pDtInpChannel, (sbyte*)ptr, NumBytesToRead);
		}
	}

	public unsafe DTAPI_RESULT ReadFrame(uint[] rFrame, ref int FrameSize)
	{
		fixed (uint* ptr = &rFrame[0])
		{
			int num = FrameSize;
			uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EReadFrame(m_pDtInpChannel, ptr, &num, -1);
			FrameSize = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT ReadFrame(uint[] rFrame, ref int FrameSize, int Timeout)
	{
		fixed (uint* ptr = &rFrame[0])
		{
			int num = FrameSize;
			uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EReadFrame(m_pDtInpChannel, ptr, &num, Timeout);
			FrameSize = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT Reset(int ResetMode)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EReset(m_pDtInpChannel, ResetMode);
	}

	public unsafe DTAPI_RESULT SetAdcSampleRate(int SampleRate)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetAdcSampleRate(m_pDtInpChannel, SampleRate);
	}

	public unsafe DTAPI_RESULT SetAntPower(int AntPower)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetAntPower(m_pDtInpChannel, AntPower);
	}

	public unsafe DTAPI_RESULT SetErrorStatsMode(int ModType, int Mode)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetErrorStatsMode(m_pDtInpChannel, ModType, Mode);
	}

	public unsafe DTAPI_RESULT SetFifoSize(int FifoSize)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetFifoSize(m_pDtInpChannel, FifoSize);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue, long ParXtra0)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetIoConfig(m_pDtInpChannel, Group, Value, SubValue, ParXtra0, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetIoConfig(m_pDtInpChannel, Group, Value, SubValue, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetIoConfig(m_pDtInpChannel, Group, Value, -1, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue, long ParXtra0, long ParXtra1)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetIoConfig(m_pDtInpChannel, Group, Value, SubValue, ParXtra0, ParXtra1);
	}

	public unsafe DTAPI_RESULT SetIpPars(DtIpPars rIpPars)
	{
		if (rIpPars == null)
		{
			return DTAPI_RESULT.E_INVALID_ARG;
		}
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIpPars dtIpPars);
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bctor_007D(&dtIpPars);
		DTAPI_RESULT result;
		try
		{
			rIpPars.ConvertToUnmgd(&dtIpPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetIpPars(m_pDtInpChannel, &dtIpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D), &dtIpPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D(&dtIpPars);
		return result;
	}

	public unsafe DTAPI_RESULT SetPars(int Count, DtPar[] rPars)
	{
		uint num2;
		if ((uint)Count <= 89478485u)
		{
			uint num = (uint)(Count * 48);
			if (num <= 4294967291u)
			{
				num2 = num + 4;
				goto IL_0022;
			}
		}
		num2 = uint.MaxValue;
		goto IL_0022;
		IL_0022:
		Dtapi.DtPar* ptr = (Dtapi.DtPar*)global::_003CModule_003E.new_005B_005D(num2);
		Dtapi.DtPar* ptr3;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = Count;
				Dtapi.DtPar* ptr2 = (Dtapi.DtPar*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, (uint)Count, (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, Dtapi.DtPar*>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, void>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num3 = (((uint)Count > 89478485u) ? uint.MaxValue : ((uint)(Count * 48)));
			uint num4 = ((num3 > 4294967291u) ? uint.MaxValue : (num3 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num4);
			throw;
		}
		int num5 = 0;
		if (0 < Count)
		{
			Dtapi.DtPar* ptr4 = ptr3;
			do
			{
				global::_003CModule_003E.Dtapi_002EDtPar_002E_003D(ptr4, rPars[num5].m_pDtPar);
				num5++;
				ptr4 = (Dtapi.DtPar*)((byte*)ptr4 + 48);
			}
			while (num5 < Count);
		}
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetPars(m_pDtInpChannel, Count, ptr3);
		if (ptr3 != null)
		{
			Dtapi.DtPar* ptr5 = (Dtapi.DtPar*)((byte*)ptr3 - 4);
			if (*(int*)ptr5 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr5);
			}
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT SetRxControl(int RxControl)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetRxControl(m_pDtInpChannel, RxControl);
	}

	public unsafe DTAPI_RESULT SetRxMode(int RxMode)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetRxMode(m_pDtInpChannel, RxMode);
	}

	public DTAPI_RESULT RegisterDemodCallback(IDtDemodEvent rIEvent)
	{
		return RegisterDemodCallback(rIEvent, -1L);
	}

	public unsafe DTAPI_RESULT RegisterDemodCallback(IDtDemodEvent rIEvent, long EvFlags)
	{
		uint result;
		if (rIEvent != null)
		{
			m_EventInterface = rIEvent;
			result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002ERegisterDemodCallback(m_pDtInpChannel, (Dtapi.IDtDemodEvent*)m_pDemodEventHelper, EvFlags);
		}
		else
		{
			result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002ERegisterDemodCallback(m_pDtInpChannel, null, EvFlags);
			m_EventInterface = rIEvent;
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetConstellationPoints(int NumPoints, DtConstelPoint[] rPoints, int StreamId)
	{
		if (NumPoints == 0)
		{
			return DTAPI_RESULT.OK;
		}
		Dtapi.DtConstelPoint* ptr = (Dtapi.DtConstelPoint*)global::_003CModule_003E.new_005B_005D(((uint)NumPoints > 536870911u) ? uint.MaxValue : ((uint)(NumPoints << 3)));
		uint num = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetConstellationPoints(m_pDtInpChannel, NumPoints, ptr, StreamId);
		if (num == 0)
		{
			int num2 = 0;
			if (0 < NumPoints)
			{
				do
				{
					rPoints[num2].m_X = *(int*)(num2 * 8 + (byte*)ptr);
					rPoints[num2].m_Y = ((int*)((byte*)ptr + num2 * 8))[1];
					num2++;
				}
				while (num2 < NumPoints);
			}
		}
		global::_003CModule_003E.delete_005B_005D(ptr);
		return (DTAPI_RESULT)num;
	}

	public DTAPI_RESULT GetConstellationPoints(int NumPoints, DtConstelPoint[] rPoints)
	{
		return GetConstellationPoints(NumPoints, rPoints, -1);
	}

	public unsafe DTAPI_RESULT GetDemodControl(DtDemodPars DemodPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPars dtDemodPars);
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bctor_007D(&dtDemodPars);
		DTAPI_RESULT result;
		try
		{
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetDemodControl(m_pDtInpChannel, &dtDemodPars);
			DemodPars.ConvertFromUnmgd(&dtDemodPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDemodPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D), &dtDemodPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D(&dtDemodPars);
		return result;
	}

	public unsafe DTAPI_RESULT GetDemodControl(ref int ModType, ref int ParXtra0, ref int ParXtra1, ref int ParXtra2)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetDemodControl(m_pDtInpChannel, &num, &num2, &num3, &num4);
		ModType = num;
		ParXtra0 = num2;
		ParXtra1 = num3;
		ParXtra2 = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetSupportedStatistics(ref int Count, DtStatistic[] rStatistics)
	{
		int num = Count;
		int num2 = num;
		uint num3 = (uint)num;
		uint num5;
		if (num3 <= 89478485)
		{
			uint num4 = num3 * 48;
			if (num4 <= 4294967291u)
			{
				num5 = num4 + 4;
				goto IL_002a;
			}
		}
		num5 = uint.MaxValue;
		goto IL_002a;
		IL_002a:
		Dtapi.DtStatistic* ptr = (Dtapi.DtStatistic*)global::_003CModule_003E.new_005B_005D(num5);
		Dtapi.DtStatistic* ptr3;
		try
		{
			if (ptr != null)
			{
				*(uint*)ptr = num3;
				Dtapi.DtStatistic* ptr2 = (Dtapi.DtStatistic*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, num3, (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, Dtapi.DtStatistic*>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, void>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num6 = num3;
			uint num7 = ((num6 > 89478485) ? uint.MaxValue : (num6 * 48));
			uint num8 = ((num7 > 4294967291u) ? uint.MaxValue : (num7 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num8);
			throw;
		}
		uint num9 = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetSupportedStatistics(m_pDtInpChannel, &num2, ptr3);
		if (num9 == 0)
		{
			int num10 = 0;
			if (0 < num2)
			{
				Dtapi.DtStatistic* ptr4 = ptr3;
				do
				{
					global::_003CModule_003E.Dtapi_002EDtStatistic_002E_003D(rStatistics[num10].m_pDtStatistic, ptr4);
					num10++;
					ptr4 = (Dtapi.DtStatistic*)((byte*)ptr4 + 48);
				}
				while (num10 < num2);
			}
		}
		if (ptr3 != null)
		{
			Dtapi.DtStatistic* ptr5 = (Dtapi.DtStatistic*)((byte*)ptr3 - 4);
			if (*(int*)ptr5 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr5);
			}
		}
		Count = num2;
		return (DTAPI_RESULT)num9;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Type, ref bool Statistic)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStatistic(m_pDtInpChannel, Type, &flag);
		Statistic = flag;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Type, ref double Statistic)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out double num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStatistic(m_pDtInpChannel, Type, &num);
		Statistic = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Type, ref int Statistic)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStatistic(m_pDtInpChannel, Type, &num);
		Statistic = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Count, DtStatistic[] rStatistics)
	{
		uint num2;
		if ((uint)Count <= 89478485u)
		{
			uint num = (uint)(Count * 48);
			if (num <= 4294967291u)
			{
				num2 = num + 4;
				goto IL_0022;
			}
		}
		num2 = uint.MaxValue;
		goto IL_0022;
		IL_0022:
		Dtapi.DtStatistic* ptr = (Dtapi.DtStatistic*)global::_003CModule_003E.new_005B_005D(num2);
		Dtapi.DtStatistic* ptr3;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = Count;
				Dtapi.DtStatistic* ptr2 = (Dtapi.DtStatistic*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, (uint)Count, (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, Dtapi.DtStatistic*>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, void>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num3 = (((uint)Count > 89478485u) ? uint.MaxValue : ((uint)(Count * 48)));
			uint num4 = ((num3 > 4294967291u) ? uint.MaxValue : (num3 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num4);
			throw;
		}
		int num5 = 0;
		if (0 < Count)
		{
			Dtapi.DtStatistic* ptr4 = ptr3;
			do
			{
				global::_003CModule_003E.Dtapi_002EDtStatistic_002E_003D(ptr4, rStatistics[num5].m_pDtStatistic);
				num5++;
				ptr4 = (Dtapi.DtStatistic*)((byte*)ptr4 + 48);
			}
			while (num5 < Count);
		}
		uint num6 = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetStatistics(m_pDtInpChannel, Count, ptr3);
		if (num6 == 0)
		{
			int num7 = 0;
			if (0 < Count)
			{
				Dtapi.DtStatistic* ptr5 = ptr3;
				do
				{
					global::_003CModule_003E.Dtapi_002EDtStatistic_002E_003D(rStatistics[num7].m_pDtStatistic, ptr5);
					num7++;
					ptr5 = (Dtapi.DtStatistic*)((byte*)ptr5 + 48);
				}
				while (num7 < Count);
			}
		}
		if (ptr3 != null)
		{
			Dtapi.DtStatistic* ptr6 = (Dtapi.DtStatistic*)((byte*)ptr3 - 4);
			if (*(int*)ptr6 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr6);
			}
		}
		return (DTAPI_RESULT)num6;
	}

	public unsafe DTAPI_RESULT GetTunerFrequency(ref long FreqHz)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetTunerFrequency(m_pDtInpChannel, &num, -1);
		FreqHz = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTunerFrequency(ref long FreqHz, int TunerId)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num);
		uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002EGetTunerFrequency(m_pDtInpChannel, &num, TunerId);
		FreqHz = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT I2CLock(int TimeOut)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CLock(m_pDtInpChannel, TimeOut);
	}

	public unsafe DTAPI_RESULT I2CUnlock()
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CUnlock(m_pDtInpChannel);
	}

	public unsafe DTAPI_RESULT I2CRead(int DvcAddr, byte[] rBuffer, int NumBytesToRead)
	{
		fixed (byte* ptr = &rBuffer[0])
		{
			return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CRead(m_pDtInpChannel, DvcAddr, (sbyte*)ptr, NumBytesToRead);
		}
	}

	public unsafe DTAPI_RESULT I2CWrite(int DvcAddr, byte[] rBuffer, int NumBytesToWrite)
	{
		fixed (byte* ptr = &rBuffer[0])
		{
			return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CWrite(m_pDtInpChannel, DvcAddr, (sbyte*)ptr, NumBytesToWrite);
		}
	}

	public unsafe DTAPI_RESULT I2CWriteRead(int DvcAddrWrite, byte[] rBufferWrite, int NumBytesToWrite, int DvcAddrRead, byte[] rBufferRead, int NumBytesToRead)
	{
		//IL_0006: Expected I, but got O
		//IL_0008: Expected I, but got O
		//The blocks IL_0015, IL_0019, IL_0023, IL_0024 are reachable both inside and outside the pinned region starting at IL_0012. ILSpy has duplicated these blocks in order to place them both within and outside the `fixed` statement.
		//The blocks IL_0024 are reachable both inside and outside the pinned region starting at IL_0021. ILSpy has duplicated these blocks in order to place them both within and outside the `fixed` statement.
		//The blocks IL_0024 are reachable both inside and outside the pinned region starting at IL_0021. ILSpy has duplicated these blocks in order to place them both within and outside the `fixed` statement.
		sbyte* ptr = (sbyte*)unchecked((nint)null);
		sbyte* ptr2 = (sbyte*)unchecked((nint)null);
		if (rBufferWrite != null)
		{
			fixed (byte* ptr3 = &rBufferWrite[0])
			{
				ptr = (sbyte*)ptr3;
				if (rBufferRead != null)
				{
					fixed (byte* ptr4 = &rBufferRead[0])
					{
						ptr2 = (sbyte*)ptr4;
						return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CWriteRead(m_pDtInpChannel, DvcAddrWrite, ptr, NumBytesToWrite, DvcAddrRead, ptr2, NumBytesToRead);
					}
				}
				return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CWriteRead(m_pDtInpChannel, DvcAddrWrite, ptr, NumBytesToWrite, DvcAddrRead, ptr2, NumBytesToRead);
			}
		}
		if (rBufferRead != null)
		{
			fixed (byte* ptr4 = &rBufferRead[0])
			{
				ptr2 = (sbyte*)ptr4;
				return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CWriteRead(m_pDtInpChannel, DvcAddrWrite, ptr, NumBytesToWrite, DvcAddrRead, ptr2, NumBytesToRead);
			}
		}
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EI2CWriteRead(m_pDtInpChannel, DvcAddrWrite, ptr, NumBytesToWrite, DvcAddrRead, ptr2, NumBytesToRead);
	}

	public unsafe DTAPI_RESULT LnbEnable([MarshalAs(UnmanagedType.U1)] bool Enable)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ELnbEnable(m_pDtInpChannel, Enable);
	}

	public unsafe DTAPI_RESULT LnbEnableTone([MarshalAs(UnmanagedType.U1)] bool Enable)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ELnbEnableTone(m_pDtInpChannel, Enable);
	}

	public unsafe DTAPI_RESULT LnbSetVoltage(int Level)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ELnbSetVoltage(m_pDtInpChannel, Level);
	}

	public unsafe DTAPI_RESULT LnbSendBurst(int BurstType)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ELnbSendBurst(m_pDtInpChannel, BurstType);
	}

	public unsafe DTAPI_RESULT LnbSendDiseqcMessage(byte[] rMsgOut, int NumBytesOut, byte[] rMsgIn, ref int NumBytesIn)
	{
		fixed (byte* ptr = &rMsgOut[0])
		{
			fixed (byte* ptr2 = &rMsgIn[0])
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
				uint result = global::_003CModule_003E.Dtapi_002EDtInpChannel_002ELnbSendDiseqcMessage(m_pDtInpChannel, ptr, NumBytesOut, ptr2, &num);
				NumBytesIn = num;
				return (DTAPI_RESULT)result;
			}
		}
	}

	public unsafe DTAPI_RESULT LnbSendDiseqcMessage(byte[] rMsgOut, int NumBytesOut)
	{
		fixed (byte* ptr = &rMsgOut[0])
		{
			return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ELnbSendDiseqcMessage(m_pDtInpChannel, ptr, NumBytesOut);
		}
	}

	public unsafe DTAPI_RESULT SetDemodControl(DtDemodPars DemodPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPars dtDemodPars);
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bctor_007D(&dtDemodPars);
		DTAPI_RESULT result;
		try
		{
			DemodPars.ConvertToUnmgd(&dtDemodPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetDemodControl(m_pDtInpChannel, &dtDemodPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDemodPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D), &dtDemodPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D(&dtDemodPars);
		return result;
	}

	public unsafe DTAPI_RESULT SetDemodControl(int ModType, int ParXtra0, int ParXtra1, int ParXtra2)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetDemodControl(m_pDtInpChannel, ModType, ParXtra0, ParXtra1, ParXtra2);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtT2MiStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtT2MiStreamSelPars dtT2MiStreamSelPars);
		*(int*)(&dtT2MiStreamSelPars) = StreamSel.m_T2MiOutPid;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtT2MiStreamSelPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtT2MiStreamSelPars, 4)) = StreamSel.m_T2MiTsRate;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtT2MiStreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtDvbT2StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2StreamSelPars dtDvbT2StreamSelPars);
		*(int*)(&dtDvbT2StreamSelPars) = StreamSel.m_PlpId;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbT2StreamSelPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2StreamSelPars, 4)) = StreamSel.m_CommonPlpId;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtDvbT2StreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtDvbTStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbTStreamSelPars dtDvbTStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtDvbTStreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtDvbS2StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2StreamSelPars dtDvbS2StreamSelPars);
		*(int*)(&dtDvbS2StreamSelPars) = StreamSel.m_Isi;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtDvbS2StreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtDvbC2StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2StreamSelPars dtDvbC2StreamSelPars);
		StreamSel.ConvertToUnmgd(&dtDvbC2StreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtDvbC2StreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtDabStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabStreamSelPars dtDabStreamSelPars);
		StreamSel.ConvertToUnmgd(&dtDabStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtDabStreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtDabEtiStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabEtiStreamSelPars dtDabEtiStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtDabEtiStreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtAtsc3StreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3StreamSelPars dtAtsc3StreamSelPars);
		*(int*)(&dtAtsc3StreamSelPars) = StreamSel.m_PlpId;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtAtsc3StreamSelPars);
	}

	public unsafe DTAPI_RESULT SetStreamSelection(ref DtIsdbtStreamSelPars StreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtStreamSelPars dtIsdbtStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetStreamSelection(m_pDtInpChannel, &dtIsdbtStreamSelPars);
	}

	public unsafe DTAPI_RESULT StatisticsPollingEnable([MarshalAs(UnmanagedType.U1)] bool Enable)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002EStatisticsPollingEnable(m_pDtInpChannel, Enable);
	}

	public unsafe DTAPI_RESULT SetTunerFrequency(long FreqHz)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetTunerFrequency(m_pDtInpChannel, FreqHz, -1);
	}

	public unsafe DTAPI_RESULT SetTunerFrequency(long FreqHz, int TunerId)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetTunerFrequency(m_pDtInpChannel, FreqHz, TunerId);
	}

	public unsafe DTAPI_RESULT SetTuningMode(int Mode)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESetTuningMode(m_pDtInpChannel, Mode);
	}

	public unsafe DTAPI_RESULT Tune(long FreqHz, DtDemodPars DemodPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPars dtDemodPars);
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bctor_007D(&dtDemodPars);
		DTAPI_RESULT result;
		try
		{
			DemodPars.ConvertToUnmgd(&dtDemodPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ETune(m_pDtInpChannel, FreqHz, &dtDemodPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDemodPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D), &dtDemodPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D(&dtDemodPars);
		return result;
	}

	public unsafe DTAPI_RESULT Tune(long FreqHz, int ModType, int ParXtra0, int ParXtra1, int ParXtra2)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ETune(m_pDtInpChannel, FreqHz, ModType, ParXtra0, ParXtra1, ParXtra2);
	}

	public unsafe DTAPI_RESULT SpectrumScan(DtSpsProgressFunc ProgessFunc, int ScanType, long FreqHzSteps, long StartFreqHz, long EndFreqHz)
	{
		m_SpsProgressFunc = ProgessFunc;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtInpChannel_002ESpectrumScan(m_pDtInpChannel, (delegate* unmanaged[Cdecl, Cdecl]<Dtapi.DtSpsProgress*, void*, void>)global::_003CModule_003E.__unep_0040_003FOnSpsProgressFunc_0040SpsProgressHelper_0040DTAPINET_0040_0040_0024_0024FSAXAAUDtSpsProgress_0040Dtapi_0040_0040PAX_0040Z, m_pSpsProgressHelper, ScanType, FreqHzSteps, StartFreqHz, EndFreqHz);
	}

	protected internal unsafe DtInpChannel(void* pDtInpChannelDerived)
	{
		m_pDtInpChannel = (Dtapi.DtInpChannel*)pDtInpChannelDerived;
		m_pDtInpChannelDerived = pDtInpChannelDerived;
		DtDemodEventHelper* ptr = (DtDemodEventHelper*)global::_003CModule_003E.@new(8u);
		DtDemodEventHelper* pDemodEventHelper;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = (int)System.Runtime.CompilerServices.Unsafe.AsPointer(ref global::_003CModule_003E._003F_003F_7DtDemodEventHelper_0040DTAPINET_0040_00406B_0040);
				((int*)ptr)[1] = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pDemodEventHelper = ptr;
			}
			else
			{
				pDemodEventHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 8u);
			throw;
		}
		m_pDemodEventHelper = pDemodEventHelper;
		BsProgressHelper* ptr2 = (BsProgressHelper*)global::_003CModule_003E.@new(4u);
		BsProgressHelper* pBsProgressHelper;
		try
		{
			if (ptr2 != null)
			{
				*(int*)ptr2 = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pBsProgressHelper = ptr2;
			}
			else
			{
				pBsProgressHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr2, 4u);
			throw;
		}
		m_pBsProgressHelper = pBsProgressHelper;
		SpsProgressHelper* ptr3 = (SpsProgressHelper*)global::_003CModule_003E.@new(4u);
		SpsProgressHelper* pSpsProgressHelper;
		try
		{
			if (ptr3 != null)
			{
				*(int*)ptr3 = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pSpsProgressHelper = ptr3;
			}
			else
			{
				pSpsProgressHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr3, 4u);
			throw;
		}
		m_pSpsProgressHelper = pSpsProgressHelper;
	}

	public unsafe DtInpChannel()
	{
		Dtapi.DtInpChannel* ptr = (Dtapi.DtInpChannel*)global::_003CModule_003E.@new(336u);
		Dtapi.DtInpChannel* pDtInpChannel;
		try
		{
			pDtInpChannel = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtInpChannel_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 336u);
			throw;
		}
		m_pDtInpChannel = pDtInpChannel;
		m_pDtInpChannelDerived = null;
		DtDemodEventHelper* ptr2 = (DtDemodEventHelper*)global::_003CModule_003E.@new(8u);
		DtDemodEventHelper* pDemodEventHelper;
		try
		{
			if (ptr2 != null)
			{
				*(int*)ptr2 = (int)System.Runtime.CompilerServices.Unsafe.AsPointer(ref global::_003CModule_003E._003F_003F_7DtDemodEventHelper_0040DTAPINET_0040_00406B_0040);
				((int*)ptr2)[1] = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pDemodEventHelper = ptr2;
			}
			else
			{
				pDemodEventHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr2, 8u);
			throw;
		}
		m_pDemodEventHelper = pDemodEventHelper;
		BsProgressHelper* ptr3 = (BsProgressHelper*)global::_003CModule_003E.@new(4u);
		BsProgressHelper* pBsProgressHelper;
		try
		{
			if (ptr3 != null)
			{
				*(int*)ptr3 = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pBsProgressHelper = ptr3;
			}
			else
			{
				pBsProgressHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr3, 4u);
			throw;
		}
		m_pBsProgressHelper = pBsProgressHelper;
		SpsProgressHelper* ptr4 = (SpsProgressHelper*)global::_003CModule_003E.@new(4u);
		SpsProgressHelper* pSpsProgressHelper;
		try
		{
			if (ptr4 != null)
			{
				*(int*)ptr4 = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pSpsProgressHelper = ptr4;
			}
			else
			{
				pSpsProgressHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr4, 4u);
			throw;
		}
		m_pSpsProgressHelper = pSpsProgressHelper;
		m_BsProgressFunc = null;
		m_SpsProgressFunc = null;
	}

	internal unsafe DtInpChannel([MarshalAs(UnmanagedType.U1)] bool Init)
	{
		if (Init)
		{
			Dtapi.DtInpChannel* ptr = (Dtapi.DtInpChannel*)global::_003CModule_003E.@new(336u);
			Dtapi.DtInpChannel* pDtInpChannel;
			try
			{
				pDtInpChannel = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtInpChannel_002E_007Bctor_007D(ptr));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.delete(ptr, 336u);
				throw;
			}
			m_pDtInpChannel = pDtInpChannel;
		}
		else
		{
			m_pDtInpChannel = null;
		}
		m_pDtInpChannelDerived = null;
		DtDemodEventHelper* ptr2 = (DtDemodEventHelper*)global::_003CModule_003E.@new(8u);
		DtDemodEventHelper* pDemodEventHelper;
		try
		{
			if (ptr2 != null)
			{
				*(int*)ptr2 = (int)System.Runtime.CompilerServices.Unsafe.AsPointer(ref global::_003CModule_003E._003F_003F_7DtDemodEventHelper_0040DTAPINET_0040_00406B_0040);
				((int*)ptr2)[1] = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pDemodEventHelper = ptr2;
			}
			else
			{
				pDemodEventHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr2, 8u);
			throw;
		}
		m_pDemodEventHelper = pDemodEventHelper;
	}

	private void _007EDtInpChannel()
	{
		_0021DtInpChannel();
	}

	private unsafe void _0021DtInpChannel()
	{
		Dtapi.DtInpChannel* pDtInpChannel = m_pDtInpChannel;
		if (pDtInpChannel != null && m_pDtInpChannelDerived == null)
		{
			Dtapi.DtInpChannel* ptr = pDtInpChannel;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr)))((nint)ptr, 1u);
			m_pDtInpChannel = null;
		}
		DtDemodEventHelper* pDemodEventHelper = m_pDemodEventHelper;
		if (pDemodEventHelper != null)
		{
			DtDemodEventHelper* ptr2 = pDemodEventHelper;
			global::_003CModule_003E.gcroot_003CDTAPINET_003A_003ADtInpChannel_0020_005E_003E_002E_007Bdtor_007D((gcroot_003CDTAPINET_003A_003ADtInpChannel_0020_005E_003E*)((byte*)ptr2 + 4));
			global::_003CModule_003E.delete(ptr2, 8u);
			m_pDemodEventHelper = null;
		}
		BsProgressHelper* pBsProgressHelper = m_pBsProgressHelper;
		if (pBsProgressHelper != null)
		{
			BsProgressHelper* ptr3 = pBsProgressHelper;
			global::_003CModule_003E.gcroot_003CDTAPINET_003A_003ADtInpChannel_0020_005E_003E_002E_007Bdtor_007D((gcroot_003CDTAPINET_003A_003ADtInpChannel_0020_005E_003E*)ptr3);
			global::_003CModule_003E.delete(ptr3, 4u);
			m_pBsProgressHelper = null;
		}
		SpsProgressHelper* pSpsProgressHelper = m_pSpsProgressHelper;
		if (pSpsProgressHelper != null)
		{
			SpsProgressHelper* ptr4 = pSpsProgressHelper;
			global::_003CModule_003E.gcroot_003CDTAPINET_003A_003ADtInpChannel_0020_005E_003E_002E_007Bdtor_007D((gcroot_003CDTAPINET_003A_003ADtInpChannel_0020_005E_003E*)ptr4);
			global::_003CModule_003E.delete(ptr4, 4u);
			m_pSpsProgressHelper = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtInpChannel();
			return;
		}
		try
		{
			_0021DtInpChannel();
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

	~DtInpChannel()
	{
		Dispose(A_0: false);
	}
}
